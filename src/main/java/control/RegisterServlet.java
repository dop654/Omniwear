package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import omniwear.model.UtenteBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("email") != null) {
			UtenteBean user = new UtenteBean();
			
			user.setNome(request.getParameter("nome"));
			user.setCognome(request.getParameter("cognome"));
			user.setEmail(request.getParameter("email"));
			user.setPassword(toDigest(request.getParameter("password")));
			user.setDataNascita(LocalDate.parse(request.getParameter("dataNascita")));
			
			PrintWriter out = response.getWriter();
			
			out.println("<!doctype html>");
			out.println("<html><head></head><body>");
			out.println(user.getNome());
			out.println(user.getCognome());
			out.println(user.getEmail());
			out.println(user.getPassword());
			out.println(user.getDataNascita());
			out.println("</body></html>");
		} else {
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
