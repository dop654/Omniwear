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


@WebServlet("/admin/image")
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<>();
		String action = request.getParameter("action");

		if ("upload".equalsIgnoreCase(action)) {
			try {
				int idProdotto = Integer.parseInt(request.getParameter("id_prodotto_img"));
				Part part = request.getPart("image");

				if (part != null) {
					String originalFileName = part.getSubmittedFileName();
					if (originalFileName != null && !originalFileName.isEmpty() && part.getSize() > 0) {
						String uniqueFileName = buildUniqueFileName(part);

						String filesystemPath = getServletContext().getRealPath(
								File.separator + UPLOAD_DIR + File.separator + uniqueFileName);

						String webPath = request.getContextPath() + "/" + UPLOAD_DIR + "/" + uniqueFileName;

						ImmagineBean img = new ImmagineBean();
						img.setIdProdotto(idProdotto);
						img.setPath(webPath);

						try {
							part.write(filesystemPath);
							immagineDAO.doSave(img);
						} catch (SQLException e) {
							errors.add(e.toString());
						}
					} else {
						errors.add("Nessun file selezionato!");
					}
				}
			} catch (NumberFormatException e) {
				errors.add("Seleziona un prodotto prima di caricare un'immagine!");
			}
		}
		else if ("elimina".equalsIgnoreCase(action)) {
			String path = request.getParameter("path");
			if (path != null && !path.isEmpty()) {
				try {
					immagineDAO.doDelete(path);

					String fileName = path.substring(path.lastIndexOf('/') + 1);
					String filesystemPath = getServletContext().getRealPath(
							File.separator + UPLOAD_DIR + File.separator + fileName);
					File file = new File(filesystemPath);
					if (file.exists()) {
						file.delete();
					}
				} catch (SQLException e) {
					errors.add(e.getMessage());
				}
			}
		}

		if (!errors.isEmpty()) {
			try {
				Collection<ProdottoBean> catalogo = prodottoDAO.doRetrieveAll(null);
				for (ProdottoBean p : catalogo) {
					p.setImmagini(immagineDAO.doRetrieveAllByProduct(p.getIdProdotto()));
				}
				request.setAttribute("listaProdotti", catalogo);
			} catch (SQLException e) {
				errors.add(e.toString());
			}
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/views/admin/dashboard_prodotti.jsp").forward(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/admin/prodotti");
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