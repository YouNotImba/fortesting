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
}
