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
		
		if(request.getParameter("email") != null) {
			UtenteBean user = new UtenteBean();
			
			user.setNome(request.getParameter("nome"));
			user.setCognome(request.getParameter("cognome"));
			user.setEmail(request.getParameter("email"));
			user.setPassword(toDigest(request.getParameter("password")));
			user.setDataNascita(LocalDate.parse(request.getParameter("dataNascita")));
			
			try {
				utenteDAO.doSave(user);
				System.out.println("Registrazione effettuata con successo");
				request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
			}catch(SQLException e) {
				System.out.println("Errore nella registrazione");
				request.getRequestDispatcher("WEB-INF/views/register.jsp").forward(request, response);
			}
		} else {
			System.out.println("Dati mancanti");
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
}
