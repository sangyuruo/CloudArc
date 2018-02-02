package com.emcloud.arc.service.impl;

import com.emcloud.arc.security.SecurityUtils;
import com.emcloud.arc.service.RuleAttributesService;
import com.emcloud.arc.domain.RuleAttributes;
import com.emcloud.arc.repository.RuleAttributesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * Service Implementation for managing RuleAttributes.
 */
@Service
@Transactional
public class RuleAttributesServiceImpl implements RuleAttributesService{

    private final Logger log = LoggerFactory.getLogger(RuleAttributesServiceImpl.class);

    private final RuleAttributesRepository ruleAttributesRepository;

    public RuleAttributesServiceImpl(RuleAttributesRepository ruleAttributesRepository) {
        this.ruleAttributesRepository = ruleAttributesRepository;
    }

    /**
     * Save a ruleAttributes.
     *
     * @param ruleAttributes the entity to save
     * @return the persisted entity
     */
    @Override
    public RuleAttributes save(RuleAttributes ruleAttributes) {
        log.debug("Request to save RuleAttributes : {}", ruleAttributes);
        ruleAttributes.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        ruleAttributes.setCreateTime(Instant.now());
        ruleAttributes.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        ruleAttributes.setUpdateTime(Instant.now());
        return ruleAttributesRepository.save(ruleAttributes);
    }

    /**
     * update a ruleAttributes.
     *
     * @param ruleAttributes the entity to update
     * @return the persisted entity
     */
    @Override
    public RuleAttributes update(RuleAttributes ruleAttributes) {
        log.debug("Request to save Company : {}", ruleAttributes);
        ruleAttributes.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        ruleAttributes.setUpdateTime(Instant.now());
        return ruleAttributesRepository.save(ruleAttributes);
    }


    /**
     *  Get all the ruleAttributes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RuleAttributes> findAll(Pageable pageable) {
        log.debug("Request to get all RuleAttributes");
        return ruleAttributesRepository.findAll(pageable);
    }

    /**
     *  Get one ruleAttributes by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RuleAttributes findOne(Long id) {
        log.debug("Request to get RuleAttributes : {}", id);
        return ruleAttributesRepository.findOne(id);
    }

    /**
     *  Delete the  ruleAttributes by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RuleAttributes : {}", id);
        ruleAttributesRepository.delete(id);
    }
}
