package com.schoolmonitor.repositories.schoolmonitor;

import org.springframework.stereotype.Repository;

import com.schoolmonitor.entities.schoolmonitor.Address;
import com.schoolmonitor.repositories.BaseRepository;
/**
 * @author PrabhjeetS
 * @version 1.0
 */

@Repository
public interface AddressRepository extends BaseRepository<Address, Integer> {
}
