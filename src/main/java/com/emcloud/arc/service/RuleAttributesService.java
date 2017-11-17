package com.emcloud.arc.service;

import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RuleAttributes.
 */
public interface RuleAttributesService {

    /**
     * Save a ruleAttributes.
     *
     * @param ruleAttributes the entity to save
     * @return the persisted entity
     */
    RuleAttributes save(RuleAttributes ruleAttributes);


    /**
     * update a ruleAttributes.
     *
     * @param ruleAttributes the entity to update
     * @return the persisted entity
     */
    RuleAttributes update(RuleAttributes ruleAttributes);

    /**
     *  Get all the ruleAttributes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RuleAttributes> findAll(Pageable pageable);

    /**
     *  Get the "id" ruleAttributes.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RuleAttributes findOne(Long id);

    /**
     *  Delete the "id" ruleAttributes.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
