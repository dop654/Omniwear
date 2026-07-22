package omniwear.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import omniwear.dao.OrdineDAO;
import omniwear.dao.OrdineDAOImpl;
import omniwear.model.OrdineBean;
import omniwear.model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet("/user_ordini")
public class OrdiniUtenteServlet extends HttpServlet{
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
    	
		HttpSession session = request.getSession(false);
		List<String> errors = new ArrayList<>();
		UtenteBean utenteSession = (UtenteBean) session.getAttribute("utente");
		int userId = utenteSession.getIdUtente();
		
		try {
			List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.doRetrieveByUtente(userId);
			
			request.setAttribute("ordini", ordini);
		} catch(SQLException e) {
			errors.add(e.toString());
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		
		request.getRequestDispatcher("/WEB-INF/views/ordini.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
		String action = request.getParameter("action");
		
		if(action != null) {
			if(action.equalsIgnoreCase("annulla")) {
				try {
					int idOrdine = Integer.parseInt(request.getParameter("id_ordine"));
					
					ordineDAO.doUpdateStato(idOrdine, 0);
					response.sendRedirect(request.getContextPath() + "/user_ordini");
					return;
				} catch(SQLException e) {
					errors.add(e.toString());
				}
			}
		}
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		
		doGet(request, response);
	}
		
}
	

