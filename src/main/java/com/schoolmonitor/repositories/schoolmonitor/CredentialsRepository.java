package com.schoolmonitor.repositories.schoolmonitor;

import org.springframework.stereotype.Repository;

import com.schoolmonitor.entities.schoolmonitor.Credential;
import com.schoolmonitor.repositories.BaseRepository;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Repository
public interface CredentialsRepository extends BaseRepository<Credential, Integer> {


	Credential findByUserName(String UserName);
    


}
