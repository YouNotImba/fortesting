package dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author younotimba
 *
 */
public abstract class GenericJpaDao<T, ID extends Serializable> implements GenericDao<T, ID> {

	@PersistenceContext(unitName = "bookDatabase")
	private EntityManager em;

	private Class<T> persistenceClass;

	@Override
	public void persist(T entity) {
		em.persist(entity);
	}

	@Override
	public void merge(T entity) {
		em.merge(entity);
	}

	@Override
	public T finfById(ID id) {
		return em.find(persistenceClass, id);
	}

	@Override
	public void delete(T entity) {
		em.remove(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return em.createQuery("SELECT x FROM " + getPersistenceClass().getSimpleName() + " x").getResultList();
	}

	public GenericJpaDao(Class<T> persistenceClass) {
		super();
		this.persistenceClass = persistenceClass;
	}

	public GenericJpaDao() {
		super();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Class<T> getPersistenceClass() {
		return persistenceClass;
	}

	public void setPersistenceClass(Class<T> persistenceClass) {
		this.persistenceClass = persistenceClass;
	}

}
