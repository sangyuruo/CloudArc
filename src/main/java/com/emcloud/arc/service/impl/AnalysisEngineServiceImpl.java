package com.emcloud.arc.service.impl;

import com.emcloud.arc.service.AnalysisEngineService;
import com.emcloud.arc.domain.AnalysisEngine;
import com.emcloud.arc.repository.AnalysisEngineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AnalysisEngine.
 */
@Service
@Transactional
public class AnalysisEngineServiceImpl implements AnalysisEngineService{

    private final Logger log = LoggerFactory.getLogger(AnalysisEngineServiceImpl.class);

    private final AnalysisEngineRepository analysisEngineRepository;

    public AnalysisEngineServiceImpl(AnalysisEngineRepository analysisEngineRepository) {
        this.analysisEngineRepository = analysisEngineRepository;
    }

    /**
     * Save a analysisEngine.
     *
     * @param analysisEngine the entity to save
     * @return the persisted entity
     */
    @Override
    public AnalysisEngine save(AnalysisEngine analysisEngine) {
        log.debug("Request to save AnalysisEngine : {}", analysisEngine);
        return analysisEngineRepository.save(analysisEngine);
    }

    /**
     *  Get all the analysisEngines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnalysisEngine> findAll(Pageable pageable) {
        log.debug("Request to get all AnalysisEngines");
        return analysisEngineRepository.findAll(pageable);
    }

    /**
     *  Get one analysisEngine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AnalysisEngine findOne(Long id) {
        log.debug("Request to get AnalysisEngine : {}", id);
        return analysisEngineRepository.findOne(id);
    }

    /**
     *  Delete the  analysisEngine by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalysisEngine : {}", id);
        analysisEngineRepository.delete(id);
    }
}
