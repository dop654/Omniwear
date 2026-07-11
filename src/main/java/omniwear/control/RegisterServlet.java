package omniwear.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import omniwear.dao.UtenteDAO;
import omniwear.dao.UtenteDAOImpl;
import omniwear.model.UtenteBean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtenteDAO utenteDAO;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException	 {
		super.init(servletConfig);
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
		utenteDAO = new UtenteDAOImpl(ds);
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
		UtenteBean user = new UtenteBean();
		
		user.setNome(validateField(request.getParameter("nome"), "nome", errors));
		user.setCognome(validateField(request.getParameter("cognome"), "cognome", errors));
		user.setEmail(validateField(request.getParameter("email"), "email", errors));
		user.setPassword(toDigest(validateField(request.getParameter("password"), "password", errors)));
		user.setDataNascita(validateField(request.getParameter("dataNascita"), "data di nascita", errors));
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("WEB-INF/views/register.jsp").forward(request, response);
		}
		
		try {
			utenteDAO.doSave(user);
			request.setAttribute("msg", "Registrazione effettuata con successo");
			request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
		}catch(SQLException e) {
			errors.add("Errore nella registrazione");
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("WEB-INF/views/register.jsp").forward(request, response);
		}
	}
	
	protected static String toDigest(String pw) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // TODO aggiorna a SHA-512
			StringBuilder sb = new StringBuilder();
			
			byte[] digestBytes = md.digest(pw.getBytes(StandardCharsets.UTF_8));
			
			for(byte b : digestBytes) {
				sb.append(String.format("%02x", b));
			}
			
			return sb.toString();
		}catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritmo SHA non disponibile", e);
		}
	}
	
	protected static String validateField(String value, String fieldName, List<String> errors) {
		if(value == null || value.trim().isEmpty()) {
			errors.add("Campo mancante: " + fieldName);
			return "";
		}
		return value.trim();
	}
}
