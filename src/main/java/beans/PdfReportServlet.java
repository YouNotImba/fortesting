package beans;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author younotimba
 *
 */
@WebServlet("/report.pdf")
public class PdfReportServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		byte[] content = (byte[]) request.getSession().getAttribute("reportBytes");
		response.setContentType("application/pdf");
		response.setContentLength(content.length);
		response.getOutputStream().write(content);
	}

}
