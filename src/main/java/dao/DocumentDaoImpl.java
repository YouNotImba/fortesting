package dao;

import javax.ejb.Stateless;
import javax.inject.Named;

import model.Document;

/**
 * @author younotimba
 *
 */
@Named
@Stateless
public class DocumentDaoImpl extends GenericJpaDao<Document, Integer> implements DocumentDao {

	public DocumentDaoImpl() {
		super(Document.class);
	}

	@Override
	public void deleteById(int id) {
		getEm().createQuery("delete from Document d where d.id = :id").setParameter("id", id).executeUpdate();

	}

}
