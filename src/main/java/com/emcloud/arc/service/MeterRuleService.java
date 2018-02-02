package com.emcloud.arc.service;

import com.emcloud.arc.domain.MeterRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MeterRule.
 */
public interface MeterRuleService {

    /**
     * Save a meterRule.
     *
     * @param meterRule the entity to save
     * @return the persisted entity
     */
    MeterRule save(MeterRule meterRule);

    /**
     *  Get all the meterRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterRule> findAll(Pageable pageable);

    /**
     *  Get the "id" meterRule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeterRule findOne(Long id);

    /**
     *  Delete the "id" meterRule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
