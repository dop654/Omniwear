package omniwear.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import omniwear.dao.ProdottoDAO;
import omniwear.dao.ProdottoDAOImpl;
import omniwear.model.ProdottoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
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
		
		String action = request.getParameter("action");
    	List<String> errors = new ArrayList<>();
		
    	if(action!=null) {
    		if(action.equalsIgnoreCase("categoria")) {
    			 	String category = RegisterServlet.validateField(request.getParameter("categoria"), "categoria", errors);
    			 	
    			 	try {
	    			 	List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveByCategoria(category);
	    			 	
	    			 	request.setAttribute("listaProdotti", prodotti);
	    			 	
	    			 	request.getRequestDispatcher("/WEB-INF/views/catalogo.jsp").forward(request, response);
	    			 	
    			 	} catch(SQLException e) {
    			 		errors.add(e.toString());
    			 	}
    		}
    		else if(action.equalsIgnoreCase("cerca")) {
    				String search = RegisterServlet.validateField(request.getParameter("cerca"), "cerca", errors);
    				
    				try {
    					List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveByNome(search);
    					
    					request.setAttribute("listaProdotti", prodotti);
    					
    					request.getRequestDispatcher("/WEB-INF/views/catalogo.jsp").forward(request, response);
    					
    			 	} catch(SQLException e) {
    			 		errors.add(e.toString());
    			 	}
    		}
		
    	}
    	else {
    		try {
	    		List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll(null);
	    		
	    		request.setAttribute("listaProdotti", prodotti);
				
				request.getRequestDispatcher("/WEB-INF/views/catalogo.jsp").forward(request, response);
				
    		} catch(SQLException e) {
    			errors.add(e.toString());
    		}
    	}
    	
    	if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		return;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}