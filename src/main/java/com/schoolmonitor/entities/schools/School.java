package com.schoolmonitor.entities.schools;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the school database table.
 * 
 */
@Entity
@Table(schema="schools",name = "school")
@NamedQuery(name = "School.findAll", query = "SELECT s FROM School s")
public class School implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 255)
	private String domainForLogin;
	@Id
	@Column(nullable = false)
	private int schoolId;

	@Column(nullable = false, length = 255)
	private String schoolName;
	@Column(nullable = false,insertable=false,updatable=false)
	private int subscriptionId;

	public int getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	// bi-directional one-to-one association to Subscription
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subscriptionId", referencedColumnName = "subscriptionId")
	private Subscription subscription;

	public School() {
	}

	public String getDomainForLogin() {
		return this.domainForLogin;
	}

	public void setDomainForLogin(String domainForLogin) {
		this.domainForLogin = domainForLogin;
	}

	public int getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Subscription getSubscription() {
		return this.subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

}