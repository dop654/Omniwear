package omniwear.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import omniwear.dao.UtenteDAO;
import omniwear.dao.UtenteDAOImpl;
import java.util.ArrayList;
import java.util.List;

import omniwear.model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

@WebServlet("/admin/users")
public class AdminUtentiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDAO utenteDAO;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
		utenteDAO = new UtenteDAOImpl(ds);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> errors = new ArrayList<>();
		
		try {
			List<UtenteBean> utenti = (List<UtenteBean>) utenteDAO.doRetrieveAll(null);
			
			request.setAttribute("listaUtenti", utenti);
            
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard_utenti.jsp").forward(request, response);
		} catch(SQLException e) {
			errors.add(e.toString());
		}
		
		 if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> errors = new ArrayList<>();
		
		int idUser = Integer.parseInt(RegisterServlet.validateField(request.getParameter("id_utente"), "id utente", errors));
		
		try {
			utenteDAO.doDelete(idUser);
			
			response.sendRedirect(request.getContextPath() + "/admin/users");

		} catch(SQLException e) {
			errors.add(e.toString());
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		return;
	}
}