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
import omniwear.dao.UtenteDAO;
import omniwear.dao.UtenteDAOImpl;
import omniwear.model.OrdineBean;
import omniwear.model.UtenteBean;
import omniwear.model.Carrello;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineDAO ordineDAO;
	private UtenteDAO utenteDAO;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
		ordineDAO = new OrdineDAOImpl(ds);
		utenteDAO = new UtenteDAOImpl(ds);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		Carrello cart = (Carrello) session.getAttribute("carrello");
		
		if(cart==null || cart.getNProdotti()==0) {
			response.sendRedirect(request.getContextPath() + "/catalogo");
			return;
		}
		
		request.setAttribute("totale", cart.getTotale());
		
		request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		List<String> errors = new ArrayList<>();
		
		String numeroCarta = RegisterServlet.validateField(request.getParameter("numero_carta"), "numero carta", errors);
		String cvc = RegisterServlet.validateField(request.getParameter("cvc"), "cvc", errors);
		String titolare = RegisterServlet.validateField(request.getParameter("titolare"), "titolare", errors);
		
		if(numeroCarta.length()!=16 || cvc.length()!=3) {
			errors.add("Dati della carta non validi");
			
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
		    return;
		}
		
		String grabIdUser = request.getParameter("id_utente");
		String address = RegisterServlet.validateField(request.getParameter("indirizzo"), "indirizzo", errors);
		Integer idUser = null;
		
		OrdineBean newOrder = new OrdineBean();
		
		if(grabIdUser!=null) {
			
			idUser = Integer.parseInt(grabIdUser);
			
			try {
				UtenteBean user = utenteDAO.doRetrieveByKey(idUser);
				
				newOrder.setUtente(user);
				
			} catch (SQLException e) {
				errors.add(e.toString());
			}
		} else
			newOrder.setUtente(null);
		
		newOrder.setIdUtente(idUser);
		newOrder.setIndirizzoDestinazione(address);
		newOrder.setStatoOrdine(0);
		
		Carrello cart = (Carrello) session.getAttribute("carrello");
		newOrder.setTotale(cart.getTotale());
		
	/*	try {
			ordineDAO.doSave(newOrder);
			
		}
	*/	
	}
}