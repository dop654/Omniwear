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
import omniwear.model.UtenteBean;
import omniwear.dao.ImmagineDAO;
import omniwear.dao.ImmagineDAOImpl;
import omniwear.dao.ProdottoDAO;
import omniwear.dao.ProdottoDAOImpl;


@WebServlet("/admin/prodotti")
public class AdminProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;
    private ImmagineDAO immagineDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
       
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
        this.prodottoDAO = new ProdottoDAOImpl(ds);
        this.immagineDAO = new ImmagineDAOImpl(ds);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        List<String> errors = new ArrayList<>();
        
        
        try {
            Collection<ProdottoBean> catalogo = prodottoDAO.doRetrieveAll(null);
            
            for(ProdottoBean p : catalogo) {
            	p.setImmagini(immagineDAO.doRetrieveAllByProduct(p.getIdProdotto()));
            }
            
            request.setAttribute("listaProdotti", catalogo);
        } catch (SQLException e) {
            errors.add(e.toString());
        }
        
        if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
        
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard_prodotti.jsp").forward(request, response);
		return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        List<String> errors = new ArrayList<>();
   
        if(action != null) {
            if(action.equalsIgnoreCase("inserisci")) {
                String nome = RegisterServlet.validateField(request.getParameter("nomeProdotto"), "nome prodotto", errors);
                String prezzo = RegisterServlet.validateField(request.getParameter("prezzo"), "prezzo", errors);
                String quantita = RegisterServlet.validateField(request.getParameter("quantita"), "quantità", errors);
                
                float price = Float.parseFloat(prezzo);
                int qt = Integer.parseInt(quantita);
                
                ProdottoBean prodotto = new ProdottoBean();
                HttpSession session = request.getSession();
                UtenteBean user = (UtenteBean)session.getAttribute("utente");
                int idAdmin = user.getIdUtente();
                
                prodotto.setNomeProdotto(nome);
                prodotto.setPrezzo(price);
                prodotto.setQt(qt);
                prodotto.setIdUtente(idAdmin);
                
                try {
                    prodottoDAO.doSave(prodotto);
                    request.setAttribute("msg", "Prodotto aggiunto con successo");
                    response.sendRedirect(request.getContextPath() + "/image");
                    return;
                } catch(SQLException e) {
                    errors.add(e.toString());
                }
            }
            else if(action.equalsIgnoreCase("elimina")) {
                String idDel = RegisterServlet.validateField(request.getParameter("id_prodotto"), "id_prodotto", errors);
                int idProdDel = Integer.parseInt(idDel);
                
                try {
                    prodottoDAO.doDelete(idProdDel);
                    response.sendRedirect(request.getContextPath() + "/admin/prodotti");
                    return;
                } catch(SQLException e) {
                    errors.add(e.toString());
                }
            }
            else if(action.equalsIgnoreCase("aggiorna")) {
                String idProd = RegisterServlet.validateField(request.getParameter("id_prodotto"), "id_prodotto", errors); 
                String nome = RegisterServlet.validateField(request.getParameter("nomeProdotto"), "nome prodotto", errors);
                String prezzo = RegisterServlet.validateField(request.getParameter("prezzo"), "prezzo", errors);
                String quantita = RegisterServlet.validateField(request.getParameter("quantita"), "quantità", errors);
                
                int idProduct = Integer.parseInt(idProd);
                float price = Float.parseFloat(prezzo);
                int qt = Integer.parseInt(quantita);
                
                ProdottoBean prodottoAggiornato = new ProdottoBean();
                
                prodottoAggiornato.setIdProdotto(idProduct);
                prodottoAggiornato.setPrezzo(price);
                prodottoAggiornato.setNomeProdotto(nome);
                prodottoAggiornato.setQt(qt);
                
                try {
                    prodottoDAO.doUpdate(prodottoAggiornato);
                    response.sendRedirect(request.getContextPath() + "/admin/prodotti");
                    return;
                } catch(SQLException e) {
                    errors.add(e.toString());
                }
            }
        }
        
        if(!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        }
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard_prodotti.jsp").forward(request, response);
        return;
    }
}