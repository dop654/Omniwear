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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<>();
		
		String email = RegisterServlet.validateField(request.getParameter("email"), "email", errors);
		String pw = RegisterServlet.validateField(request.getParameter("pass"), "password", errors);
		
		String pwCriptata = RegisterServlet.toDigest(pw);
		
		try {
			UtenteBean utente = utenteDAO.doRetrieveByEmailPassword(email, pwCriptata);
			if(utente.getIdUtente() != 0) {
				session.setAttribute("id_utente", utente.getIdUtente());
				session.setAttribute("nome_utente", utente.getNome());
				session.setAttribute("role", utente.getAdmin() ? "admin" : "user");
				response.sendRedirect(request.getContextPath() + "/HomeServlet");
				return;
			}else {
				errors.add("Credenziali errate");
			}
		}catch(SQLException e) {
			errors.add(e.toString());
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		return;
	}
}