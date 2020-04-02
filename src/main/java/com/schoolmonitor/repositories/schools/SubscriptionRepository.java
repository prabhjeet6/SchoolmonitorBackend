package com.schoolmonitor.repositories.schools;

import org.springframework.stereotype.Repository;

import com.schoolmonitor.entities.schools.Subscription;
import com.schoolmonitor.repositories.BaseRepository;
/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Repository
public interface SubscriptionRepository extends BaseRepository<Subscription, Integer> {

String findSubscriptionStatusBySubscriptionId(int subscriptionId);
}
