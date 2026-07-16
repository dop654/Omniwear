package omniwear.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import javax.sql.DataSource;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import omniwear.model.ProdottoBean;
import omniwear.dao.ProdottoDAO;
import omniwear.dao.ProdottoDAOImpl;


@WebServlet("/admin/prodotti")
public class AdminProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
       
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
        this.prodottoDAO = new ProdottoDAOImpl(ds);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        List<String> errors = new ArrayList<>();
        
        
        try {
            Collection<ProdottoBean> catalogo = prodottoDAO.doRetrieveAll(null);
            
            request.setAttribute("listaProdotti", catalogo);
            
            request.getRequestDispatcher("/WEB-INF/admin/dashboard_prodotti.jsp").forward(request, response);
            
        } catch (SQLException e) {
            errors.add(e.toString());
        }
        
        if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	String action = request.getParameter("action");
    	List<String> errors = new ArrayList<>();
   
    	if(action!=null) {
    		if(action.equalsIgnoreCase("inserisci")) {
    			 	String nome = request.getParameter("nomeProdotto");
    		        String prezzo = request.getParameter("prezzo");
    		        
    		        float price = Float.parseFloat(prezzo);
    		        
    		        ProdottoBean prodotto = new ProdottoBean();
    		        HttpSession session = request.getSession();
    		        int idAdmin = (int) session.getAttribute("id_utente");
    		        
    		        prodotto.setNomeProdotto(nome);
    		        prodotto.setPrezzo(price);
    		        prodotto.setIdUtente(idAdmin);
    		        
    		        try {
    					prodottoDAO.doSave(prodotto);
    					response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    		        } catch(SQLException e) {
    		            errors.add(e.toString());
    		        }
    		}
    		else if(action.equalsIgnoreCase("elimina")) {
    			String idDel = request.getParameter("id_prodotto");
    			int idProdDel = Integer.parseInt(idDel);
    			
    			try {
    				prodottoDAO.doDelete(idProdDel);
    				response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    			} catch(SQLException e) {
		            errors.add(e.toString());
		        }
    		}
    		else if(action.equalsIgnoreCase("aggiorna")) {
    			String idProd = request.getParameter("idProdotto");
    			String nome = request.getParameter("nomeProdotto");
		        String prezzo = request.getParameter("prezzo");
		        
		        int idProduct = Integer.parseInt(idProd);
		        float price = Float.parseFloat(prezzo);
		        
		        ProdottoBean prodottoAggiornato = new ProdottoBean();
		        
		        prodottoAggiornato.setIdProdotto(idProduct);
		        prodottoAggiornato.setPrezzo(price);
		        prodottoAggiornato.setNomeProdotto(nome);
		        
    			try {
    				prodottoDAO.doUpdate(prodottoAggiornato);
    				response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    			} catch(SQLException e) {
		            errors.add(e.toString());
		        }
    		}
    	}
    	if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		return;
    }
}