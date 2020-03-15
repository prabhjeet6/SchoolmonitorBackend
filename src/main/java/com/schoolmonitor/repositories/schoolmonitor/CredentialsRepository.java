package com.schoolmonitor.repositories.schoolmonitor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.schoolmonitor.entities.schoolmonitor.Credential;
import com.schoolmonitor.repositories.BaseRepository;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Repository
public interface CredentialsRepository extends BaseRepository<Credential, Integer> {

	Credential findByUserNameAndPassword(String userName, String password);

	Credential findByUserName(String UserName);

	@Query("select c.userName from Credential c where c.linkedStudentId=?1")
	String findUserNameByLinkedStudentId(String studentId);

}
