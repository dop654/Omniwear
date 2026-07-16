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
    	
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<>();
		
		Integer grabId = (Integer) session.getAttribute("id_utente");
		int userId;
		
		if(grabId != null && grabId>0) {
			userId = grabId;
		}
		else {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		try {
			UtenteBean currentUser = utenteDAO.doRetrieveByKey(userId);
			
			request.setAttribute("id_utente", currentUser.getIdUtente());
			request.setAttribute("nome_utente", currentUser.getNome());
			request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
		}catch(SQLException e) {
			errors.add(e.toString());
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<>();
		
		Integer grabId = (Integer) session.getAttribute("id_utente");
		int userId;
		
		if(grabId!=null && grabId>0) {
			userId = grabId;
		}
		else {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		
		try {
			UtenteBean currentUser = utenteDAO.doRetrieveByKey(userId);
			
			String newPass = RegisterServlet.validateField(request.getParameter("varPassword"), "password", errors); 
			String newName = RegisterServlet.validateField(request.getParameter("varName"), "nome", errors);
			String newSurname = RegisterServlet.validateField(request.getParameter("varSurname"), "cognome", errors);
			String newBirth = RegisterServlet.validateField(request.getParameter("varData"), "data di nascita", errors);
			
			currentUser.setNome(newName);
			currentUser.setCognome(newSurname);
			currentUser.setDataNascita(newBirth);
			currentUser.setPassword(RegisterServlet.toDigest(newPass));
			
			try {
				utenteDAO.doUpdate(currentUser);
				response.sendRedirect(request.getContextPath() + "/user_page");
			}catch(SQLException e) {
				errors.add(e.toString());
			}
		}catch(SQLException err) {
			errors.add(err.toString());
		}
		return;
	}
		
}
	

