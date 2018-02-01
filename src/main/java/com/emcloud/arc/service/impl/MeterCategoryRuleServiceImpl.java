package com.emcloud.arc.service.impl;

import com.emcloud.arc.security.SecurityUtils;
import com.emcloud.arc.service.MeterCategoryRuleService;
import com.emcloud.arc.domain.MeterCategoryRule;
import com.emcloud.arc.repository.MeterCategoryRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * Service Implementation for managing MeterCategoryRule.
 */
@Service
@Transactional
public class MeterCategoryRuleServiceImpl implements MeterCategoryRuleService{

    private final Logger log = LoggerFactory.getLogger(MeterCategoryRuleServiceImpl.class);

    private final MeterCategoryRuleRepository meterCategoryRuleRepository;

    public MeterCategoryRuleServiceImpl(MeterCategoryRuleRepository meterCategoryRuleRepository) {
        this.meterCategoryRuleRepository = meterCategoryRuleRepository;
    }

    /**
     * Save a meterCategoryRule.
     *
     * @param meterCategoryRule the entity to save
     * @return the persisted entity
     */
    @Override
    public MeterCategoryRule save(MeterCategoryRule meterCategoryRule) {
        log.debug("Request to save MeterCategoryRule : {}", meterCategoryRule);
      /*  return meterCategoryRuleRepository.save(meterCategoryRule);
    }*/
 meterCategoryRule.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        meterCategoryRule.setCreateTime(Instant.now());
        meterCategoryRule.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterCategoryRule.setUpdateTime(Instant.now());
        return meterCategoryRuleRepository.save(meterCategoryRule);
}

    /**
     * update a MeterCategoryRule.
     *
     * @param meterCategoryRule the entity to update
     * @return the persisted entity
     */
    @Override
    public MeterCategoryRule update(MeterCategoryRule meterCategoryRule) {
        log.debug("Request to save Company : {}", meterCategoryRule);
        meterCategoryRule.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterCategoryRule.setUpdateTime(Instant.now());
        return meterCategoryRuleRepository.save(meterCategoryRule);
    }
    /**
     *  Get all the meterCategoryRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterCategoryRule> findAll(Pageable pageable) {
        log.debug("Request to get all MeterCategoryRules");
        return meterCategoryRuleRepository.findAll(pageable);
    }

    /**
     *  Get one meterCategoryRule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeterCategoryRule findOne(Long id) {
        log.debug("Request to get MeterCategoryRule : {}", id);
        return meterCategoryRuleRepository.findOne(id);
    }

    /**
     *  Delete the  meterCategoryRule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeterCategoryRule : {}", id);
        meterCategoryRuleRepository.delete(id);
    }
}
