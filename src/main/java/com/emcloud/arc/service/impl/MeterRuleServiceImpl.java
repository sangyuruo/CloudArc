package com.emcloud.arc.service.impl;

import com.emcloud.arc.domain.MeterCategoryRule;
import com.emcloud.arc.repository.MeterCategoryRuleRepository;
import com.emcloud.arc.security.SecurityUtils;
import com.emcloud.arc.service.MeterCategoryRuleService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Service Implementation for managing MeterRule.
 */
@Service
@Transactional
public class MeterRuleServiceImpl implements MeterRuleService{

    private final Logger log = LoggerFactory.getLogger(MeterRuleServiceImpl.class);

    private final MeterRuleRepository meterRuleRepository;
    private final MeterCategoryRuleService meterCategoryRuleService;

    public MeterRuleServiceImpl(MeterRuleRepository meterRuleRepository,   MeterCategoryRuleService meterCategoryRuleService) {
        this.meterRuleRepository = meterRuleRepository;
        this.meterCategoryRuleService = meterCategoryRuleService;
    }

    @Override
    public List<String> getTest() {
        log.debug("REST request to get a page of MeterCategoryRules");
        List<MeterCategoryRule> list = meterCategoryRuleService.findAll();
        List<MeterRule> list2 = findAll();
        String mcr;
        String  mr;
        List<String> string1 = new ArrayList<>();
        List<String> string2 = new ArrayList<>();
        for (MeterCategoryRule rule : list){
            for (MeterRule rule1:list2) {
                mr = rule1.getRuleName();
                mcr=rule.getRuleName();
                string1.add(mcr);
                string2.add(mr);
            }
            string2.addAll(string1);
        }
        return  string2;
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
        meterRule.setRuleCode( UUID.randomUUID().toString() );
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

    @Override
    public List<MeterRule> findAll() {
        return meterRuleRepository.findAll();
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
