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
import omniwear.dao.OrdineProdottoDAO;
import omniwear.dao.OrdineProdottoDAOImpl;
import omniwear.dao.UtenteDAOImpl;
import omniwear.model.OrdineBean;
import omniwear.model.OrdineProdottoBean;
import omniwear.model.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

@WebServlet("/dettaglio_ordini")
public class DettaglioOrdineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrdineDAO ordineDAO;
    private OrdineProdottoDAO ordineProdDAO;

    @Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
		ordineDAO = new OrdineDAOImpl(ds);
		ordineProdDAO = new OrdineProdottoDAOImpl(ds);
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String grabIdOrdine = request.getParameter("id_ordine");
		List<String> errors = new ArrayList<>();
        
        if (grabIdOrdine != null) {
            try {
                int idOrdine = Integer.parseInt(grabIdOrdine);
                
                OrdineBean ordine = ordineDAO.doRetrieveByKey(idOrdine); 
                Collection<OrdineProdottoBean> prodottiOrdine = ordineProdDAO.doRetrieveByOrdine(idOrdine);

                request.setAttribute("ordine", ordine);
                request.setAttribute("prodottiOrdine", prodottiOrdine);

                request.getRequestDispatcher("/WEB-INF/views/dettaglio_ordini.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                errors.add(e.toString());
            }
        }
        
        if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
        response.sendRedirect(request.getContextPath() + "/user_ordini");
    }
}