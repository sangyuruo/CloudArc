package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.domain.MeterCategoryRule;
import com.emcloud.arc.service.MeterCategoryRuleService;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MeterCategoryRule.
 */
@RestController
@RequestMapping("/api")
public class MeterCategoryRuleResource {

    private final Logger log = LoggerFactory.getLogger(MeterCategoryRuleResource.class);

    private static final String ENTITY_NAME = "meterCategoryRule";

    private final MeterCategoryRuleService meterCategoryRuleService;

    public MeterCategoryRuleResource(MeterCategoryRuleService meterCategoryRuleService) {
        this.meterCategoryRuleService = meterCategoryRuleService;
    }

    /**
     * POST  /meter-category-rules : Create a new meterCategoryRule.
     *
     * @param meterCategoryRule the meterCategoryRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meterCategoryRule, or with status 400 (Bad Request) if the meterCategoryRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meter-category-rules")
    @Timed
    public ResponseEntity<MeterCategoryRule> createMeterCategoryRule(@Valid @RequestBody MeterCategoryRule meterCategoryRule) throws URISyntaxException {
        log.debug("REST request to save MeterCategoryRule : {}", meterCategoryRule);
        if (meterCategoryRule.getId() != null) {
            throw new BadRequestAlertException("A new meterCategoryRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeterCategoryRule result = meterCategoryRuleService.save(meterCategoryRule);
        return ResponseEntity.created(new URI("/api/meter-category-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meter-category-rules : Updates an existing meterCategoryRule.
     *
     * @param meterCategoryRule the meterCategoryRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meterCategoryRule,
     * or with status 400 (Bad Request) if the meterCategoryRule is not valid,
     * or with status 500 (Internal Server Error) if the meterCategoryRule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meter-category-rules")
    @Timed
    public ResponseEntity<MeterCategoryRule> updateMeterCategoryRule(@Valid @RequestBody MeterCategoryRule meterCategoryRule) throws URISyntaxException {
        log.debug("REST request to update MeterCategoryRule : {}", meterCategoryRule);
        if (meterCategoryRule.getId() == null) {
            return createMeterCategoryRule(meterCategoryRule);
        }
        MeterCategoryRule result = meterCategoryRuleService.update(meterCategoryRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meterCategoryRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meter-category-rules : get all the meterCategoryRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterCategoryRules in body
     */
    @GetMapping("/meter-category-rules")
    @Timed
    public ResponseEntity<List<MeterCategoryRule>> getAllMeterCategoryRules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeterCategoryRules");
        Page<MeterCategoryRule> page = meterCategoryRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meter-category-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /meter-category-rules/:id : get the "id" meterCategoryRule.
     *
     * @param id the id of the meterCategoryRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meterCategoryRule, or with status 404 (Not Found)
     */
    @GetMapping("/meter-category-rules/{id}")
    @Timed
    public ResponseEntity<MeterCategoryRule> getMeterCategoryRule(@PathVariable Long id) {
        log.debug("REST request to get MeterCategoryRule : {}", id);
        MeterCategoryRule meterCategoryRule = meterCategoryRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meterCategoryRule));
    }

    /**
     * DELETE  /meter-category-rules/:id : delete the "id" meterCategoryRule.
     *
     * @param id the id of the meterCategoryRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meter-category-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeterCategoryRule(@PathVariable Long id) {
        log.debug("REST request to delete MeterCategoryRule : {}", id);
        meterCategoryRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
