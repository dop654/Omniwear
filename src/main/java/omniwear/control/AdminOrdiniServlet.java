package omniwear.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import omniwear.dao.OrdineDAO;
import omniwear.dao.OrdineDAOImpl;
import java.util.ArrayList;
import java.util.List;

import omniwear.model.OrdineBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineDAO ordineDAO;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
		ordineDAO = new OrdineDAOImpl(ds);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> errors = new ArrayList<>();
		String filtroData = request.getParameter("filtroData");
	    String filtroEmail = request.getParameter("filtroEmail");
		
	    try {
	        List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.doRetrieveFiltered(filtroEmail, filtroData);
	        request.setAttribute("listaOrdini", ordini);
	        
	    } catch(SQLException e) {
	        errors.add(e.toString());
	    }
	    
	    if(!errors.isEmpty()) {
	        request.setAttribute("errors", errors);
	    }
	     
	    request.getRequestDispatcher("/WEB-INF/views/admin/dashboard_ordini.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> errors = new ArrayList<>();
		
		int newStato = Integer.parseInt(RegisterServlet.validateField(request.getParameter("stato"), "stato", errors));
		int idOrder = Integer.parseInt(RegisterServlet.validateField(request.getParameter("id_ordine"), "id ordine", errors));
		
		try {
			ordineDAO.doUpdateStato(idOrder, newStato);
			
			response.sendRedirect(request.getContextPath() + "/admin/ordini");
			return;
		} catch(SQLException e) {
			errors.add(e.toString());
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		
		request.getRequestDispatcher("/WEB-INF/views/admin/dashboard_ordini.jsp").forward(request, response);
		return;
	}

}