package omniwear.control;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import omniwear.dao.CategoriaDAO;
import omniwear.dao.CategoriaDAOImpl;
import omniwear.dao.ProdottoDAO;
import omniwear.dao.ProdottoDAOImpl;
import omniwear.model.CategoriaBean;
import omniwear.model.ProdottoBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

@WebServlet("/SchedaProdottoServlet")
public class SchedaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;
	private CategoriaDAO categoriaDAO;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if(ds == null) {
			throw new ServletException("DataSource non disponibile");
		}
		prodottoDAO = new ProdottoDAOImpl(ds);
		categoriaDAO = new CategoriaDAOImpl(ds);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
		String idProdStr = request.getParameter("id_prodotto");
		
		if(idProdStr != null && !idProdStr.trim().isEmpty()) {
			int id_prodotto = Integer.parseInt(idProdStr);
			try {
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(id_prodotto);
				Collection<CategoriaBean> categorie = categoriaDAO.doRetrieveProductCategories(id_prodotto);
				
				if(prodotto == null) {
					errors.add("Prodotto non trovato");
				}else {
					request.setAttribute("prodotto", prodotto);
					request.setAttribute("categorie", categorie);
					request.getRequestDispatcher("/WEB-INF/views/prodotto.jsp").forward(request, response);
					return;
				}
			}catch(SQLException e) {
				errors.add(e.toString());
			}
		} else {
			errors.add("Prodotto non valido");
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
