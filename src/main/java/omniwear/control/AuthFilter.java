package omniwear.control;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import omniwear.model.UtenteBean;

@WebFilter(urlPatterns = {"/admin/home", "/admin/prodotti", "/user_page", "/admin/ordini", "/user_ordini"})
public class AuthFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;	
	
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String path = request.getServletPath();
		
		if(!path.startsWith("/admin/") && !path.startsWith("/common/")) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = request.getSession(false);
		UtenteBean utenteSession = (session!=null) ? (UtenteBean) session.getAttribute("utente") : null; 
				
		boolean autorizzato = false;
		if(utenteSession!=null) {
			if(path.startsWith("/admin/")) {
				autorizzato = utenteSession.getAdmin();
			} else if(path.startsWith("/common/")) {
				autorizzato = true;
			}
		}
		if(autorizzato) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/index");
		}
	}

}
