package admin.liste.etudiant_professeur.controlleur.professeur;

import admin.liste.etudiant_professeur.Dao.ProfesseurDAO;
import admin.liste.etudiant_professeur.modele.Professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ListeProfesseursServlet", urlPatterns = "/listeProfesseurs")
public class ListeProfesseursServlet extends HttpServlet {

    private ProfesseurDAO professeurDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialisation de ProfesseurDAO (si nécessaire, injectez une instance partagée ou une factory)
        professeurDAO = new ProfesseurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupération de la liste des professeurs depuis le DAO
            List<Professeur> professeurs = professeurDAO.getAllProfesseurs();

            // Ajout des professeurs comme attribut de la requête
            request.setAttribute("professeurs", professeurs);

            // Redirection vers la vue JSP correspondante
            request.getRequestDispatcher("admin/vues/liste_professeur.jsp").forward(request, response);
        } catch (SQLException e) {
            // Gestion des exceptions liées à la base de données
            throw new ServletException("Erreur lors de la récupération des professeurs.", e);
        }
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources si nécessaire
        super.destroy();
    }
}
