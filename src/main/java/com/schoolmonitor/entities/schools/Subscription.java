package com.schoolmonitor.entities.schools;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the subscription database table.
 * 
 */
@Entity
@Table(schema = "schools", name = "subscription")
@NamedQuery(name = "Subscription.findAll", query = "SELECT s FROM Subscription s")
public class Subscription implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(unique = true, nullable = false)
	int subscriptionId;

	public int getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(int subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date subscribedFrom;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date subscribedTo;

	@Column(nullable = false, length = 255)
	private String subscriptionMode;

	// bi-directional one-to-one association to School
	@OneToOne(mappedBy = "subscription", fetch = FetchType.LAZY)
	private School school;

	public Subscription() {
	}

	public Date getSubscribedFrom() {
		return this.subscribedFrom;
	}

	public void setSubscribedFrom(Date subscribedFrom) {
		this.subscribedFrom = subscribedFrom;
	}

	public Date getSubscribedTo() {
		return this.subscribedTo;
	}

	public void setSubscribedTo(Date subscribedTo) {
		this.subscribedTo = subscribedTo;
	}

	public String getSubscriptionMode() {
		return this.subscriptionMode;
	}

	public void setSubscriptionMode(String subscriptionMode) {
		this.subscriptionMode = subscriptionMode;
	}

	public School getSchool() {
		return this.school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

}