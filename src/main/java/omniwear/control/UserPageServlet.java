package omniwear.control;

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
public class UserPageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private UtenteDAO utenteDAO;
	
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

		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		utenteDAO = new UtenteDAOImpl(ds);
		
		try {
			UtenteBean currentUser = utenteDAO.doRetrieveByKey(userId);
			
			request.setAttribute("utente", currentUser);
			request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
		}catch(SQLException e) {
			errors.add(e.toString());
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
