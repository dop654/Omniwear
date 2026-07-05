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

import javax.sql.DataSource;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
		HttpSession session = request.getSession();
		
		String email = request.getParameter("email");
		String pw = request.getParameter("pass");
		
		if(email == null || pw == null) {
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			return;
		}
		String pwCriptata = RegisterServlet.toDigest(pw);
			try {
				UtenteBean utente = utenteDAO.doRetrieveByEmailPassword(email, pw);
				if(utente != null) {
					session.setAttribute("id_utente", utente.getIdUtente());
					request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
				}
			}catch(SQLException e) {
				System.out.println(e);
			}
		}
	}



