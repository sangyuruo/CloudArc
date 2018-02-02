package com.emcloud.arc.service;

import com.emcloud.arc.domain.AnalysisEngine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AnalysisEngine.
 */
public interface AnalysisEngineService {

    /**
     * Save a analysisEngine.
     *
     * @param analysisEngine the entity to save
     * @return the persisted entity
     */
    AnalysisEngine save(AnalysisEngine analysisEngine);

    /**
     * update a analysisEngine.
     *
     * @param analysisEngine the entity to update
     * @return the persisted entity
     */
    AnalysisEngine update(AnalysisEngine analysisEngine);

    /**
     * Get all the analysisEngines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AnalysisEngine> findAll(Pageable pageable);

    /**
     * Get the "id" analysisEngine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AnalysisEngine findOne(Long id);

    /**
     * Delete the "id" analysisEngine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
