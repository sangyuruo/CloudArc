package com.emcloud.arc.service;

import com.emcloud.arc.domain.MeterCategoryRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing MeterCategoryRule.
 */
public interface MeterCategoryRuleService {

    /**
     * Save a meterCategoryRule.
     *
     * @param meterCategoryRule the entity to save
     * @return the persisted entity
     */
    MeterCategoryRule save(MeterCategoryRule meterCategoryRule);

    /**
     * update a meterCategoryRule.
     *
     * @param meterCategoryRule the entity to update
     * @return the persisted entity
     */
    MeterCategoryRule update(MeterCategoryRule meterCategoryRule);

    /**
     *  Get all the meterCategoryRules.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterCategoryRule> findAll(Pageable pageable);





    List<MeterCategoryRule> findAll();

    /**
     *  Get the "id" meterCategoryRule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeterCategoryRule findOne(Long id);

    /**
     *  Delete the "id" meterCategoryRule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
