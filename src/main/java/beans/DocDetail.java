package beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import dao.DocumentDao;
import model.Document;

/**
 * @author younotimba
 *
 */
@Named
@ApplicationScoped
public class DocDetail implements Serializable {

	@Inject
	private DocumentDao documentDao;

	StreamedContent content;

	public DocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public StreamedContent getContent() {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			int id = Integer
					.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
			Document currentDoc = documentDao.finfById(id);
			DefaultStreamedContent dsc = new DefaultStreamedContent(new ByteArrayInputStream(currentDoc.getDocData()),
					"application/pdf");
			System.out.println(dsc);
			return dsc;
		}
	}

	public void setContent(StreamedContent content) {
		this.content = content;
	}

}
