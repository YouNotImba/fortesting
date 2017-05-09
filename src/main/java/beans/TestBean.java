package beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import dao.DocumentDao;
import model.Document;

/**
 * @author younotimba
 *
 */
@Named
@RequestScoped
public class TestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 745928994619848621L;

	@Inject
	private DocumentDao docDao;

	private Document document;

	private UploadedFile file;

	@PostConstruct
	public void init() {
		document = new Document();
	}

	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		byte[] fileData = uploadedFile.getContents();
		if (fileData != null) {
			document.setDocData(fileData);
		}
	}

	@Transactional
	public void save() {
		/*
		 * if (file != null) { byte[] fileData = file.getContents();
		 * document.setDocData(fileData); }
		 */
		docDao.persist(document);
		FacesMessage msg = new FacesMessage("Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public void setDocDao(DocumentDao docDao) {
		this.docDao = docDao;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
