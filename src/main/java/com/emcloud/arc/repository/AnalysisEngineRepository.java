package com.emcloud.arc.repository;

import com.emcloud.arc.domain.AnalysisEngine;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AnalysisEngine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisEngineRepository extends JpaRepository<AnalysisEngine, Long> {

}
