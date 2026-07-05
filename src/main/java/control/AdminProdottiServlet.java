package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import javax.sql.DataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import omniwear.model.ProdottoBean;
import omniwear.dao.ProdottoDAO;
import omniwear.dao.ProdottoDAOImpl;


@WebServlet("/admin/prodotti")
public class AdminProdottiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
 
        DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
        
        ProdottoDAO prodottoDAO = new ProdottoDAOImpl(ds);
        
        try {
            Collection<ProdottoBean> catalogo = prodottoDAO.doRetrieveAll(null);
            
            request.setAttribute("listaProdotti", catalogo);
            
            request.getRequestDispatcher("/WEB-INF/admin/dashboard_prodotti.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero dei prodotti");
        }
    }

    // 3. IL METODO PER RICEVERE I DATI DEI FORM (INSERT, UPDATE, DELETE)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Qui dentro gestiremo l'arrivo dei dati del form "Aggiungi Prodotto"
        // Per ora, se qualcuno fa un POST, lo rimandiamo al doGet (così vede la lista)
        doGet(request, response);
    }
}