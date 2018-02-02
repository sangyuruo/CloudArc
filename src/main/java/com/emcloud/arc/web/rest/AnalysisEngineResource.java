package com.emcloud.arc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.emcloud.arc.domain.AnalysisEngine;
import com.emcloud.arc.service.AnalysisEngineService;
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
 * REST controller for managing AnalysisEngine.
 */
@RestController
@RequestMapping("/api")
public class AnalysisEngineResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisEngineResource.class);

    private static final String ENTITY_NAME = "analysisEngine";

    private final AnalysisEngineService analysisEngineService;

    public AnalysisEngineResource(AnalysisEngineService analysisEngineService) {
        this.analysisEngineService = analysisEngineService;
    }

    /**
     * POST  /analysis-engines : Create a new analysisEngine.
     *
     * @param analysisEngine the analysisEngine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new analysisEngine, or with status 400 (Bad Request) if the analysisEngine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/analysis-engines")
    @Timed
    public ResponseEntity<AnalysisEngine> createAnalysisEngine(@Valid @RequestBody AnalysisEngine analysisEngine) throws URISyntaxException {
        log.debug("REST request to save AnalysisEngine : {}", analysisEngine);
        if (analysisEngine.getId() != null) {
            throw new BadRequestAlertException("A new analysisEngine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalysisEngine result = analysisEngineService.save(analysisEngine);
        return ResponseEntity.created(new URI("/api/analysis-engines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /analysis-engines : Updates an existing analysisEngine.
     *
     * @param analysisEngine the analysisEngine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated analysisEngine,
     * or with status 400 (Bad Request) if the analysisEngine is not valid,
     * or with status 500 (Internal Server Error) if the analysisEngine couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/analysis-engines")
    @Timed
    public ResponseEntity<AnalysisEngine> updateAnalysisEngine(@Valid @RequestBody AnalysisEngine analysisEngine) throws URISyntaxException {
        log.debug("REST request to update AnalysisEngine : {}", analysisEngine);
        if (analysisEngine.getId() == null) {
            return createAnalysisEngine(analysisEngine);
        }
        AnalysisEngine result = analysisEngineService.save(analysisEngine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, analysisEngine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /analysis-engines : get all the analysisEngines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of analysisEngines in body
     */
    @GetMapping("/analysis-engines")
    @Timed
    public ResponseEntity<List<AnalysisEngine>> getAllAnalysisEngines(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AnalysisEngines");
        Page<AnalysisEngine> page = analysisEngineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/analysis-engines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /analysis-engines/:id : get the "id" analysisEngine.
     *
     * @param id the id of the analysisEngine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the analysisEngine, or with status 404 (Not Found)
     */
    @GetMapping("/analysis-engines/{id}")
    @Timed
    public ResponseEntity<AnalysisEngine> getAnalysisEngine(@PathVariable Long id) {
        log.debug("REST request to get AnalysisEngine : {}", id);
        AnalysisEngine analysisEngine = analysisEngineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(analysisEngine));
    }

    /**
     * DELETE  /analysis-engines/:id : delete the "id" analysisEngine.
     *
     * @param id the id of the analysisEngine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/analysis-engines/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnalysisEngine(@PathVariable Long id) {
        log.debug("REST request to delete AnalysisEngine : {}", id);
        analysisEngineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
