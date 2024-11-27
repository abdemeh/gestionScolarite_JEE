package admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ConnexionAdminServlet", urlPatterns = "/connexionAdmin")
public class ConnexionAdminServlet extends HttpServlet {

    private static final String ADMIN_ID = "admin"; // Remplacez par un ID réel
    private static final String ADMIN_PASSWORD = "password123"; // Remplacez par un mot de passe réel

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige vers la page de connexion
        request.getRequestDispatcher("/admin/pagedeconnexionAdmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idAdmin = request.getParameter("id_admin");
        String motDePasse = request.getParameter("mot_de_passe");

        if (ADMIN_ID.equals(idAdmin) && ADMIN_PASSWORD.equals(motDePasse)) {
            // Connexion réussie
            response.sendRedirect(request.getContextPath() + "/admin/pageAdmin.jsp");
        } else {
            // Connexion échouée
            request.setAttribute("message", "ID ou mot de passe incorrect.");
            request.getRequestDispatcher("/admin/pagedeconnexionAdmin.jsp").forward(request, response);
        }
    }
}
