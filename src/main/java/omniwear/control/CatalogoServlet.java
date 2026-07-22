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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
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
    	processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
		String[] categorie = request.getParameterValues("categorie");
		String search = request.getParameter("cerca");
		List<ProdottoBean> prodotti = new ArrayList<>();
		
		try {
	    	if(categorie != null && categorie.length > 0) {
			 	prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveByCategoria(categorie);
			} else if(search != null && !search.trim().isEmpty()) {
				prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveByNome(search);
			} else {
	    		prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll(null);
	    	}
		} catch (SQLException e) {
			errors.add(e.toString());
		}

		String requestedWith = request.getHeader("X-Requested-With");
		
		
		if (requestedWith == null || !requestedWith.equals("XMLHttpRequest")) {
			if(!errors.isEmpty()) {
				request.setAttribute("errors", errors);
			}
			request.setAttribute("listaProdotti", prodotti);
			request.getRequestDispatcher("/WEB-INF/views/catalogo.jsp").forward(request, response);
			return;
		}

		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		JSONArray jsonArray = new JSONArray();
		for (ProdottoBean p : prodotti) {
			JSONObject obj = new JSONObject();
			obj.put("id_prodotto", p.getIdProdotto());
			obj.put("nome_prodotto", p.getNomeProdotto());
			obj.put("prezzo", p.getPrezzo());
			jsonArray.put(obj);
		}
		out.print(jsonArray.toString());
		out.flush();
	}
}