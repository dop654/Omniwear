package omniwear.control;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import omniwear.dao.ImmagineDAO;
import omniwear.dao.ImmagineDAOImpl;
import omniwear.dao.ProdottoDAO;
import omniwear.dao.ProdottoDAOImpl;
import omniwear.model.ImmagineBean;
import omniwear.model.ProdottoBean;

import javax.sql.DataSource;


@WebServlet("/image")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, // max 5 MB per file
	maxRequestSize = 10 * 1024 * 1024, // max 10 MB per request
	fileSizeThreshold = 2* 1024 * 1024) // 2 MB after which the file will be temporarily stored on disk
public class ImageControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIR = "uploads";

	private ProdottoDAO prodottoDAO;
	private ImmagineDAO immagineDAO;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		if (ds == null) {
			throw new ServletException("DataSource non disponibile nel contesto applicativo.");
		}
		prodottoDAO = new ProdottoDAOImpl(ds);
		immagineDAO = new ImmagineDAOImpl(ds);

		String uploadPath = getServletContext().getRealPath(File.separator + UPLOAD_DIR);
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists())
			uploadDir.mkdir();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
        String action = request.getParameter("action");
        
        if (action.equalsIgnoreCase("show")){
        	int productCode = Integer.parseInt(request.getParameter("id_prodotto"));
        	
        	try {
        		ProdottoBean bean = prodottoDAO.doRetrieveByKey(productCode);
        		Collection<ImmagineBean> img = immagineDAO.doRetrieveAllByProduct(bean.getIdProdotto());
        		
        		response.setContentType("image");
        		for(ImmagineBean i : img) {
        			try (InputStream is = new FileInputStream(i.getPath())) {
            			OutputStream os = response.getOutputStream();
            			is.transferTo(os);
            		} catch (IOException ioe) {
        				errors.add(ioe.getMessage());
        			}
        		}
  
        	} catch (SQLException e) {
				errors.add(e.getMessage());
			}
        }
        
        if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
		String action = request.getParameter("action");
		
		if ("inserisci".equalsIgnoreCase(action)) {
			int productCode = Integer.parseInt(request.getParameter("id_prodotto"));
			Part part = request.getPart("image");
			
			if (part != null) {
				String originalFileName = part.getSubmittedFileName();
				if (originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
					String uniqueFileName = buildUniqueFileName(part);
					String uploadPath = getServletContext().getRealPath(File.separator + UPLOAD_DIR + File.separator + uniqueFileName);
		
					ImmagineBean img = new ImmagineBean();

					img.setIdProdotto(productCode);
					img.setPath(uploadPath);
					try {
						part.write(uploadPath);
						immagineDAO.doSave(img);
					} catch (SQLException e) {
						errors.add(e.getMessage());
					}
				}
			}
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
		}
		response.sendRedirect("/admin/prodotti");
	}

	private String buildUniqueFileName(Part part) {
		String originalName = part.getSubmittedFileName();
		String extension;
		if (originalName.contains(".")) {
		    extension = originalName.substring(originalName.lastIndexOf("."));
		} else {
		    extension = "";
		}
		return UUID.randomUUID() + extension;
	}

	
}