package omniwear.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import omniwear.dao.UtenteDAO;
import omniwear.dao.UtenteDAOImpl;
import omniwear.model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet("/user_page")
public class UserServlet extends HttpServlet{
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
    	
		HttpSession session = request.getSession(false);
		List<String> errors = new ArrayList<>();
		
		UtenteBean utenteSession = (UtenteBean) session.getAttribute("utente");
		int userId = utenteSession.getIdUtente();
		
		try {
			UtenteBean currentUser = utenteDAO.doRetrieveByKey(userId);
			
			request.setAttribute("utente", currentUser);
			request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
		}catch(SQLException e) {
			errors.add(e.toString());
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
		}
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<>();
		
		UtenteBean utenteSession = (UtenteBean) session.getAttribute("utente");
		
		if(utenteSession == null) {
		    response.sendRedirect(request.getContextPath() + "/LoginServlet");
		    return;
		}
		
		int userId = utenteSession.getIdUtente();
		
		try {
			UtenteBean currentUser = utenteDAO.doRetrieveByKey(userId);
			
			String newName = RegisterServlet.validateField(request.getParameter("nome"), "nome", errors);
			String newSurname = RegisterServlet.validateField(request.getParameter("cognome"), "cognome", errors);
			String newBirth = RegisterServlet.validateField(request.getParameter("data_nascita"), "data di nascita", errors);
			
			currentUser.setNome(newName);
			currentUser.setCognome(newSurname);
			currentUser.setDataNascita(newBirth);
			
			String newPass = request.getParameter("password");
			
			if (newPass != null && !newPass.trim().isEmpty()) {
		        String validPass = RegisterServlet.validateField(newPass, "password", errors); 
		        currentUser.setPassword(RegisterServlet.toDigest(validPass));
		    }
			
			try {
				utenteDAO.doUpdate(currentUser);
				session.setAttribute("utente", currentUser);
				response.sendRedirect(request.getContextPath() + "/index");
			}catch(SQLException e) {
				errors.add(e.toString());
			}
		}catch(SQLException err) {
			errors.add(err.toString());
		}
		return;
	}
		
}
	

