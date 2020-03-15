package com.schoolmonitor.repositories;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
@NoRepositoryBean
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID> {
private static final Logger logger = LoggerFactory.getLogger(BaseRepositoryImpl.class);
	
	private final JpaEntityInformation<T, ?> entityInformation;
	
	@PersistenceContext
	private final EntityManager entityManager;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation=entityInformation;
		this.entityManager=entityManager;
	}

	
	

	/*
	 * TODO:
	 * 
	 * @Transactional//ensures method is always invoked under read-write Transaction
	 * 
	 * @Override public Optional<T> xxxMethod(){
	 * 
	 * }
	 */
}
