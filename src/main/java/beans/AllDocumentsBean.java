package beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
// @ViewScoped
@SessionScoped
public class AllDocumentsBean implements Serializable {

	@Inject
	private DocumentDao documentDao;

	private List<Document> documents;

	private StreamedContent streamedContent;

	private Document selectedDocument;

	private int id;

	@PostConstruct
	public void init() {
		documents = documentDao.findAll();
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public DocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public StreamedContent getStreamedContent() {

		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the media. Return a real
			// StreamedContent with the media bytes.
			// Document doc = documentDao.finfById(id);
			DefaultStreamedContent content = new DefaultStreamedContent(
					new ByteArrayInputStream(selectedDocument.getDocData()), "application/pdf");
			System.out.println(content);
			return content;
		}
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdFile() {
		return java.util.UUID.randomUUID().toString();
	}

	public Document getSelectedDocument() {
		return selectedDocument;
	}

	public void setSelectedDocument(Document selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

}
