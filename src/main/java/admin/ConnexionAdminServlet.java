package admin;

import dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ConnexionAdminServlet", urlPatterns = "/connexionAdmin")
public class ConnexionAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige vers la page de connexion
        request.getRequestDispatcher("/admin/pagedeconnexionAdmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailAdmin = request.getParameter("email_admin");
        String motDePasse = request.getParameter("mot_de_passe");

        String ADMIN_EMAIL = new UtilisateurDAO().getUtilisateurByEmail(emailAdmin).getEmail();
        String ADMIN_PASSWORD = new UtilisateurDAO().getUtilisateurByEmail(emailAdmin).getMotDePasse();
        int ADMIN_ROLE = new UtilisateurDAO().getUtilisateurByEmail(emailAdmin).getRole();
        int ADMIN_ID = new UtilisateurDAO().getUtilisateurByEmail(emailAdmin).getIdUtilisateur();

        if (ADMIN_EMAIL.equals(emailAdmin) && ADMIN_PASSWORD.equals(motDePasse) && ADMIN_ROLE==1) {
            // Connexion réussie
            request.getSession().setAttribute("user", "admin");
            request.getSession().setAttribute("id_admin", ADMIN_ID);

            request.getRequestDispatcher("/admin/pageAdmin.jsp").forward(request, response);
        } else {
            // Connexion échouée
            request.setAttribute("message", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("/admin/pagedeconnexionAdmin.jsp").forward(request, response);
        }
    }
}
