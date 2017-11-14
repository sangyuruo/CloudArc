package com.emcloud.arc.service;

import com.emcloud.arc.domain.AlarmRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AlarmRule.
 */
public interface AlarmRuleService {

    /**
     * Save a alarmRule.
     *
     * @param alarmRule the entity to save
     * @return the persisted entity
     */
    AlarmRule save(AlarmRule alarmRule);

    /**
     * Uspdate a alarmRule.
     *
     * @param alarmRule the entity to update
     * @return the persisted entity
     */
    AlarmRule update(AlarmRule alarmRule);


    /**
     *  Get all the alarmRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AlarmRule> findAll(Pageable pageable);

    /**
     *  Get the "id" alarmRule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AlarmRule findOne(Long id);

    /**
     *  Delete the "id" alarmRule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
