package com.schoolmonitor.repositories.schools;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.schoolmonitor.entities.schools.School;
import com.schoolmonitor.repositories.BaseRepository;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Repository
public interface SchoolRepository extends BaseRepository<School, Integer> {
	
	School findByDomainForLogin(String domain);
	
	@Query("select s.domainForLogin from School s")
	List<String> findDomainForLogin();
}
