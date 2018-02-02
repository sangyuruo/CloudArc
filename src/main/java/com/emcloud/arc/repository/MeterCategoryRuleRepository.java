package com.emcloud.arc.repository;

import com.emcloud.arc.domain.MeterCategoryRule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MeterCategoryRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeterCategoryRuleRepository extends JpaRepository<MeterCategoryRule, Long> {

}
