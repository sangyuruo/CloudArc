package com.emcloud.arc.repository;

import com.emcloud.arc.domain.RuleAttributes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the RuleAttributes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuleAttributesRepository extends JpaRepository<RuleAttributes, Long> {
        List<RuleAttributes> findByRuleCode(String c);
}
