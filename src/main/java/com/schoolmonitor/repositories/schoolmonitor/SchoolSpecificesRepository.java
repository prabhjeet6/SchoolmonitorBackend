package com.schoolmonitor.repositories.schoolmonitor;

import org.springframework.stereotype.Repository;

import com.schoolmonitor.entities.schoolmonitor.Schoolspecific;
import com.schoolmonitor.repositories.BaseRepository;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Repository
public interface SchoolSpecificesRepository extends BaseRepository<Schoolspecific, Integer> {

}
