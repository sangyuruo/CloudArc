package com.emcloud.arc.repository;

import com.emcloud.arc.domain.AlarmRule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the AlarmRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Long> {
    List<AlarmRule> findAll();
}
