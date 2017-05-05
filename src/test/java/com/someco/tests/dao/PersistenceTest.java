package com.someco.tests.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Assert;

/**
 * @author younotimba
 *
 */
public class PersistenceTest {

	public void persistenceTest() {
		EntityManager entityManager = Persistence.createEntityManagerFactory("bookDatabase").createEntityManager();
		Assert.assertNotNull(entityManager);
	}

}
