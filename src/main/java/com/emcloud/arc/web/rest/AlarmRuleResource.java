package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.domain.AlarmRule;
import com.emcloud.arc.domain.MeterRule;
import com.emcloud.arc.domain.SmartMeterData;
import com.emcloud.arc.service.AlarmRuleService;
import com.emcloud.arc.web.rest.errors.BadRequestAlertException;
import com.emcloud.arc.web.rest.util.HeaderUtil;
import com.emcloud.arc.web.rest.util.PaginationUtil;
import com.sun.net.httpserver.Authenticator;
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
 * REST controller for managing AlarmRule.
 */
@RestController
@RequestMapping("/api")
public class AlarmRuleResource {

    private final Logger log = LoggerFactory.getLogger(AlarmRuleResource.class);

    private static final String ENTITY_NAME = "alarmRule";

    private final AlarmRuleService alarmRuleService;

    public AlarmRuleResource(AlarmRuleService alarmRuleService) {
        this.alarmRuleService = alarmRuleService;
    }

    /**
     * POST  /alarm-rules : Create a new alarmRule.
     *
     * @param alarmRule the alarmRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alarmRule, or with status 400 (Bad Request) if the alarmRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alarm-rules")
    @Timed
    public ResponseEntity<AlarmRule> createAlarmRule(@Valid @RequestBody AlarmRule alarmRule) throws URISyntaxException {
        log.debug("REST request to save AlarmRule : {}", alarmRule);
        if (alarmRule.getId() != null) {
            throw new BadRequestAlertException("A new alarmRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmRule result = alarmRuleService.save(alarmRule);
        return ResponseEntity.created(new URI("/api/alarm-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alarm-rules : Updates an existing alarmRule.
     *
     * @param alarmRule the alarmRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alarmRule,
     * or with status 400 (Bad Request) if the alarmRule is not valid,
     * or with status 500 (Internal Server Error) if the alarmRule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alarm-rules")
    @Timed
    public ResponseEntity<AlarmRule> updateAlarmRule(@Valid @RequestBody AlarmRule alarmRule) throws URISyntaxException {
        log.debug("REST request to update AlarmRule : {}", alarmRule);
        if (alarmRule.getId() == null) {
            return createAlarmRule(alarmRule);
        }
        AlarmRule result = alarmRuleService.update(alarmRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alarmRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alarm-rules : get all the alarmRules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of alarmRules in body
     */
    @GetMapping("/alarm-rules")
    @Timed
    public ResponseEntity<List<AlarmRule>> getAllAlarmRules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AlarmRules");
        Page<AlarmRule> page = alarmRuleService.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/alarm-rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /alarm-rules/:id : get the "id" alarmRule.
     *
     * @param id the id of the alarmRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alarmRule, or with status 404 (Not Found)
     */
    @GetMapping("/alarm-rules/{id}")
    @Timed
    public ResponseEntity<AlarmRule> getAlarmRule(@PathVariable Long id) {
        log.debug("REST request to get AlarmRule : {}", id);
        AlarmRule alarmRule = alarmRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(alarmRule));
    }

    /**
     * DELETE  /alarm-rules/:id : delete the "id" alarmRule.
     *
     * @param id the id of the alarmRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alarm-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlarmRule(@PathVariable Long id) {
        log.debug("REST request to delete AlarmRule : {}", id);
        alarmRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
