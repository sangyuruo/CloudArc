package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.domain.MeterCategoryRule;
import com.emcloud.arc.domain.MeterRule;
import com.emcloud.arc.domain.RuleDTO;
import com.emcloud.arc.service.MeterCategoryRuleService;
import com.emcloud.arc.service.MeterRuleService;
import com.emcloud.arc.web.rest.errors.BadRequestAlertException;
import com.emcloud.arc.web.rest.util.HeaderUtil;
import com.emcloud.arc.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MeterRule.
 */
@RestController
@RequestMapping("/api")
public class MeterRuleResource {

    private final Logger log = LoggerFactory.getLogger(MeterRuleResource.class);

    private static final String ENTITY_NAME = "meterRule";

    private final MeterRuleService meterRuleService;
    private final MeterCategoryRuleService meterCategoryRuleService;

    public MeterRuleResource(MeterRuleService meterRuleService, MeterCategoryRuleService meterCategoryRuleService) {
        this.meterRuleService = meterRuleService;
        this.meterCategoryRuleService = meterCategoryRuleService;
    }

    /**
     * POST  /meter-rules : Create a new meterRule.
     *
     * @param meterRule the meterRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meterRule, or with status 400 (Bad Request) if the meterRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meter-rules")
    @Timed
    public ResponseEntity<MeterRule> createMeterRule(@Valid @RequestBody MeterRule meterRule) throws URISyntaxException {
        log.debug("REST request to save MeterRule : {}", meterRule);
        if (meterRule.getId() != null) {
            throw new BadRequestAlertException("A new meterRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeterRule result = meterRuleService.save(meterRule);
        return ResponseEntity.created(new URI("/api/meter-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meter-rules : Updates an existing meterRule.
     *
     * @param meterRule the meterRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meterRule,
     * or with status 400 (Bad Request) if the meterRule is not valid,
     * or with status 500 (Internal Server Error) if the meterRule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meter-rules")
    @Timed
    public ResponseEntity<MeterRule> updateMeterRule(@Valid @RequestBody MeterRule meterRule) throws URISyntaxException {
        log.debug("REST request to update MeterRule : {}", meterRule);
        if (meterRule.getId() == null) {
            return createMeterRule(meterRule);
        }
        MeterRule result = meterRuleService.update(meterRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meterRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meter-rules : get all the meterRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterRules in body
     */
    @GetMapping("/meter-rules")
    @Timed
    public ResponseEntity<List<MeterRule>> getAllMeterRules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeterRules");
        Page<MeterRule> page = meterRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meter-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/meter-rules/test")
    @Timed
    public List<RuleDTO> getTest() {
        log.debug("REST request to get a page of MeterCategoryRules");
        List<MeterCategoryRule> list = meterCategoryRuleService.findAll();
        List<MeterRule> list2 = meterRuleService.findAll();
        List<RuleDTO> ruleDTOList = new ArrayList<>();
        List<RuleDTO> ruleDTOList2 = new ArrayList<>();
        for (MeterCategoryRule rule : list){
            RuleDTO r=new RuleDTO();
             r.setRuleName(rule.getRuleName());
             r.setRuleCode(rule.getRuleCode());
            ruleDTOList.add(r);
        }
        for (MeterRule rule1:list2) {
            RuleDTO r=new RuleDTO();
            r.setRuleName(rule1.getRuleName());
            r.setRuleCode(rule1.getRuleCode());
            ruleDTOList2.add(r);
        }
        ruleDTOList2.addAll(ruleDTOList);
         return ruleDTOList2;
    }


    /**
     * GET  /meter-rules/:id : get the "id" meterRule.
     *
     * @param id the id of the meterRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meterRule, or with status 404 (Not Found)
     */
    @GetMapping("/meter-rules/{id}")
    @Timed
    public ResponseEntity<MeterRule> getMeterRule(@PathVariable Long id) {
        log.debug("REST request to get MeterRule : {}", id);
        MeterRule meterRule = meterRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meterRule));
    }

    /**
     * DELETE  /meter-rules/:id : delete the "id" meterRule.
     *
     * @param id the id of the meterRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meter-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeterRule(@PathVariable Long id) {
        log.debug("REST request to delete MeterRule : {}", id);
        meterRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
