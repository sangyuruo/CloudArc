package com.emcloud.arc.service.impl;

import com.emcloud.arc.security.SecurityUtils;
import com.emcloud.arc.service.AlarmRuleService;
import com.emcloud.arc.domain.AlarmRule;
import com.emcloud.arc.repository.AlarmRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


/**
 * Service Implementation for managing AlarmRule.
 */
@Service
@Transactional
public class AlarmRuleServiceImpl implements AlarmRuleService{

    private final Logger log = LoggerFactory.getLogger(AlarmRuleServiceImpl.class);

    private final AlarmRuleRepository alarmRuleRepository;

    public AlarmRuleServiceImpl(AlarmRuleRepository alarmRuleRepository) {
        this.alarmRuleRepository = alarmRuleRepository;
    }

    /**
     * Save a alarmRule.
     *
     * @param alarmRule the entity to save
     * @return the persisted entity
     */
    @Override
    public AlarmRule save(AlarmRule alarmRule) {
        log.debug("Request to save AlarmRule : {}", alarmRule);
        alarmRule.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        alarmRule.setCreateTime(Instant.now());
        alarmRule.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        alarmRule.setUpdateTime(Instant.now());
        return alarmRuleRepository.save(alarmRule);
    }

    /**
     * update a alarmRule.
     *
     * @param alarmRule the entity to update
     * @return the persisted entity
     */
    @Override
    public AlarmRule update(AlarmRule alarmRule) {
        log.debug("Request to save Company : {}", alarmRule);
        alarmRule.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        alarmRule.setUpdateTime(Instant.now());
        return alarmRuleRepository.save(alarmRule);
    }

    /**
     *  Get all the alarmRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlarmRule> findAll(Pageable pageable) {
        log.debug("Request to get all AlarmRules");
        return alarmRuleRepository.findAll(pageable);
    }

    /**
     *  Get one alarmRule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AlarmRule findOne(Long id) {
        log.debug("Request to get AlarmRule : {}", id);
        return alarmRuleRepository.findOne(id);
    }

    /**
     *  Delete the  alarmRule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlarmRule : {}", id);
        alarmRuleRepository.delete(id);
    }
}
