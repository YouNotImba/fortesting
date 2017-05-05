package dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author younotimba
 *
 */
public interface GenericDao<T, ID extends Serializable> {

	void persist(T entity);

	void merge(T entity);

	T finfById(ID id);

	void delete(T entity);

	List<T> findAll();
}
