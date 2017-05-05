package beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dao.DocumentDao;
import model.Document;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author younotimba
 *
 */
@Named(value = "docs")
@RequestScoped
public class DocumentsTableBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private DocumentDao documentDao;

	private List<Document> documents;

	@Resource(name = "fortest")
	private DataSource dataSource;

	@PostConstruct
	public void init() {
		documents = documentDao.findAll();
	}

	public void generatePdfReport(ActionEvent event) throws JRException, IOException {
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(documents);
		InputStream is = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/reports/simple.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(is, new HashMap(), dataSource);
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		ServletOutputStream sos = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, sos);

	}

	public void generateHtmlReport(ActionEvent event) throws JRException, SQLException, IOException {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("query", "(select * from document where id = 8) as data");
		InputStream is = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/reports/dynamic.jrxml");
		JasperReport report = JasperCompileManager.compileReport(is);
		Connection conn = dataSource.getConnection();
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.setContentType("text/html");
		ServletOutputStream sos = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, sos);
	}

	public void setPdfToSession() {
		int id = Integer
				.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		Document currentDoc = documentDao.finfById(id);
		Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		session.put("reportBytes", currentDoc.getDocData());
	}

	public DocumentDao getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
}
