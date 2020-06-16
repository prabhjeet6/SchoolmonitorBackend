package com.schoolmonitor.repositories.schoolmonitor;

import org.springframework.stereotype.Repository;

import com.schoolmonitor.entities.schoolmonitor.Teacher;
import com.schoolmonitor.repositories.BaseRepository;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Repository
public interface TeachersRepository extends BaseRepository<Teacher, String> {
	Teacher findByTeacherEmailId(String teacherEmailId);
}
