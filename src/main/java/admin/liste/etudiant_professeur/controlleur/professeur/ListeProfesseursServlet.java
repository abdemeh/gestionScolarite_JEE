package admin.liste.etudiant_professeur.controlleur.professeur;

import dao.EnseignantDAO;
import modele.Enseignant;
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

    private EnseignantDAO professeurDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialisation de ProfesseurDAO (si nécessaire, injectez une instance partagée ou une factory)
        professeurDAO = new EnseignantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération de la liste des professeurs depuis le DAO
        List<Enseignant> professeurs = professeurDAO.getAllProfesseurs();

        // Ajout des professeurs comme attribut de la requête
        request.setAttribute("professeurs", professeurs);

        // Redirection vers la vue JSP correspondante
        request.getRequestDispatcher("admin/vues/liste_professeur.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources si nécessaire
        super.destroy();
    }
}
