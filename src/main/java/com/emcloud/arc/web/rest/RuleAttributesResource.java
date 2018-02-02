package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.domain.RuleAttributes;
import com.emcloud.arc.service.RuleAttributesService;
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
 * REST controller for managing RuleAttributes.
 */
@RestController
@RequestMapping("/api")
public class RuleAttributesResource {

    private final Logger log = LoggerFactory.getLogger(RuleAttributesResource.class);

    private static final String ENTITY_NAME = "ruleAttributes";

    private final RuleAttributesService ruleAttributesService;

    public RuleAttributesResource(RuleAttributesService ruleAttributesService) {
        this.ruleAttributesService = ruleAttributesService;
    }

    /**
     * POST  /rule-attributes : Create a new ruleAttributes.
     *
     * @param ruleAttributes the ruleAttributes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ruleAttributes, or with status 400 (Bad Request) if the ruleAttributes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rule-attributes")
    @Timed
    public ResponseEntity<RuleAttributes> createRuleAttributes(@Valid @RequestBody RuleAttributes ruleAttributes) throws URISyntaxException {
        log.debug("REST request to save RuleAttributes : {}", ruleAttributes);
        if (ruleAttributes.getId() != null) {
            throw new BadRequestAlertException("A new ruleAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RuleAttributes result = ruleAttributesService.save(ruleAttributes);
        return ResponseEntity.created(new URI("/api/rule-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rule-attributes : Updates an existing ruleAttributes.
     *
     * @param ruleAttributes the ruleAttributes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ruleAttributes,
     * or with status 400 (Bad Request) if the ruleAttributes is not valid,
     * or with status 500 (Internal Server Error) if the ruleAttributes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rule-attributes")
    @Timed
    public ResponseEntity<RuleAttributes> updateRuleAttributes(@Valid @RequestBody RuleAttributes ruleAttributes) throws URISyntaxException {
        log.debug("REST request to update RuleAttributes : {}", ruleAttributes);
        if (ruleAttributes.getId() == null) {
            return createRuleAttributes(ruleAttributes);
        }
        RuleAttributes result = ruleAttributesService.save(ruleAttributes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ruleAttributes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rule-attributes : get all the ruleAttributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ruleAttributes in body
     */
    @GetMapping("/rule-attributes")
    @Timed
    public ResponseEntity<List<RuleAttributes>> getAllRuleAttributes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RuleAttributes");
        Page<RuleAttributes> page = ruleAttributesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rule-attributes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rule-attributes/:id : get the "id" ruleAttributes.
     *
     * @param id the id of the ruleAttributes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ruleAttributes, or with status 404 (Not Found)
     */
    @GetMapping("/rule-attributes/{id}")
    @Timed
    public ResponseEntity<RuleAttributes> getRuleAttributes(@PathVariable Long id) {
        log.debug("REST request to get RuleAttributes : {}", id);
        RuleAttributes ruleAttributes = ruleAttributesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ruleAttributes));
    }

    /**
     * DELETE  /rule-attributes/:id : delete the "id" ruleAttributes.
     *
     * @param id the id of the ruleAttributes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rule-attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRuleAttributes(@PathVariable Long id) {
        log.debug("REST request to delete RuleAttributes : {}", id);
        ruleAttributesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
