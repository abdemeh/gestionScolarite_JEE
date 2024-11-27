package admin.liste.etudiant_professeur.controlleur.etudiants;



import admin.liste.etudiant_professeur.Dao.EtudiantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import admin.liste.etudiant_professeur.modele.Etudiant;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListeEtudiantsServlet", urlPatterns = "/listeEtudiants")
public class ListeEtudiantsServlet extends HttpServlet {

    private EtudiantDAO etudiantDAO = new EtudiantDAO(); // Instance de DAO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les données via le modèle
            List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
            request.setAttribute("etudiants", etudiants);

            // Transférer les données à la vue
            request.getRequestDispatcher("admin/vues/liste_etudiant.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des étudiants", e);
        }
    }
}

