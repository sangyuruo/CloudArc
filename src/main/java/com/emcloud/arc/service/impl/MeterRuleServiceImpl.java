package com.emcloud.arc.service.impl;

import com.emcloud.arc.security.SecurityUtils;
import com.emcloud.arc.service.MeterRuleService;
import com.emcloud.arc.domain.MeterRule;
import com.emcloud.arc.repository.MeterRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * Service Implementation for managing MeterRule.
 */
@Service
@Transactional
public class MeterRuleServiceImpl implements MeterRuleService{

    private final Logger log = LoggerFactory.getLogger(MeterRuleServiceImpl.class);

    private final MeterRuleRepository meterRuleRepository;

    public MeterRuleServiceImpl(MeterRuleRepository meterRuleRepository) {
        this.meterRuleRepository = meterRuleRepository;
    }

    /**
     * Save a meterRule.
     *
     * @param meterRule the entity to save
     * @return the persisted entity
     */
    @Override
    public MeterRule save(MeterRule meterRule) {
        log.debug("Request to save MeterRule : {}", meterRule);
        meterRule.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        meterRule.setCreateTime(Instant.now());
        meterRule.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterRule.setUpdateTime(Instant.now());
        return meterRuleRepository.save(meterRule);
    }

    /**
     * update a meterRule.
     *
     * @param meterRule the entity to update
     * @return the persisted entity
     */
    @Override
    public MeterRule update(MeterRule meterRule) {
        log.debug("Request to save Company : {}", meterRule);
        meterRule.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterRule.setUpdateTime(Instant.now());
        return meterRuleRepository.save(meterRule);
    }


    /**
     *  Get all the meterRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterRule> findAll(Pageable pageable) {
        log.debug("Request to get all MeterRules");
        return meterRuleRepository.findAll(pageable);
    }

    /**
     *  Get one meterRule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeterRule findOne(Long id) {
        log.debug("Request to get MeterRule : {}", id);
        return meterRuleRepository.findOne(id);
    }

    /**
     *  Delete the  meterRule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeterRule : {}", id);
        meterRuleRepository.delete(id);
    }
}
