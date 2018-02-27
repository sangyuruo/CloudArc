package com.emcloud.arc.repository;

import com.emcloud.arc.domain.MeterCategoryRule;
import com.emcloud.arc.domain.MeterRule;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the MeterRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeterRuleRepository extends JpaRepository<MeterRule, Long> {

    List<MeterRule> findByMeterCode(String m);
    List<MeterRule> findAll();

    //private  MeterCategoryRule meterCategoryRule;
/*
@Query("select mr.ruleName,mcr.ruleName from MeterRule mr,MeterCategoryRule mcr where ")
    List<MeterRule>  a(String ruleName1, String ruleName2, Pageable pageable);
*/
}
