package admin.liste.etudiant_professeur.controlleur.professeur;



import admin.liste.etudiant_professeur.Dao.ProfesseurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet(name = "SupprimerProfesseurServlet", urlPatterns = "/supprimerProfesseur")
public class SupprimerProfesseurServlet extends HttpServlet {

    private ProfesseurDAO professeurDAO = new ProfesseurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProfesseurParam = request.getParameter("id");
        if (idProfesseurParam != null && !idProfesseurParam.trim().isEmpty()) {
            try {
                int idProfesseur = Integer.parseInt(idProfesseurParam);
                professeurDAO.supprimerProfesseur(idProfesseur);
                response.sendRedirect("listeProfesseurs");
            } catch (Exception e) {
                throw new ServletException("Erreur lors de la suppression du professeur", e);
            }
        } else {
            response.sendRedirect("listeProfesseurs");
        }
    }
}
