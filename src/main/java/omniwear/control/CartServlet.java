package omniwear.control;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import omniwear.dao.ProdottoDAO;
import omniwear.dao.ProdottoDAOImpl;
import omniwear.model.Carrello;
import omniwear.model.ProdottoCarrello;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
		prodottoDAO = new ProdottoDAOImpl(ds);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		HttpSession session = request.getSession();
	
		Carrello cart = (Carrello) session.getAttribute("carrello");
			
			if(cart==null) {
				cart = new Carrello();
				session.setAttribute("carrello", cart);
			}
			request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
			return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<>();
		String action = request.getParameter("action");
	 
		Carrello cart = (Carrello) session.getAttribute("carrello");
			
		if(action!=null) {
	    	if(action.equalsIgnoreCase("aggiungi")) {
    			 String grabProdId= request.getParameter("id_prodotto");
    			 int prodID = Integer.parseInt(grabProdId);
    			 
    			 String grabProdQuantity= request.getParameter("quantita");
    			 int prodQuantity = Integer.parseInt(grabProdQuantity);
    		        
    		     try {
    		    	 ProdottoCarrello product = new ProdottoCarrello(prodottoDAO.doRetrieveByKey(prodID), prodQuantity);
	    		     cart.aggiungiProdotto(product);
	    		     response.sendRedirect(request.getContextPath() + "/CartServlet");
    			} catch(SQLException e) {
    				 errors.add(e.toString());
    			}
	    	} else if(action.equalsIgnoreCase("rimuovi")) {
	    		if(cart==null) {
	    			response.sendRedirect(request.getContextPath() + "/CartServlet");
	    		}
	    			
	    		String idDel = request.getParameter("id_prodotto");
	    		int idProdDel = Integer.parseInt(idDel);
	    	
	    		cart.rimuoviProdotto(idProdDel);
	    		response.sendRedirect(request.getContextPath() + "/CartServlet");
	    		
	    	} else if(action.equalsIgnoreCase("modifica")) {
	    		if(cart==null) {
	    			response.sendRedirect(request.getContextPath() + "/CartServlet");
	    		}
	    		
	    		String grabProdId= request.getParameter("id_prodotto");
	   			int prodID = Integer.parseInt(grabProdId);
	   			 
	   			String grabProdQuantity= request.getParameter("quantita");
	   			int prodQuantity = Integer.parseInt(grabProdQuantity);
	    	
	    		cart.modificaQuantita(prodID, prodQuantity);
	    		response.sendRedirect(request.getContextPath() + "/CartServlet");
	    	}
	    		
	    	if(!errors.isEmpty()) {
	    		request.setAttribute("errors", errors);
	    	} 
	    }
		else {
			response.sendRedirect(request.getContextPath() + "/CartServlet");
		}
		return;
    	
	}
		
}
