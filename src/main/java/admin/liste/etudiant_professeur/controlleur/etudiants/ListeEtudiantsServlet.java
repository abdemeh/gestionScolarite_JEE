package admin.liste.etudiant_professeur.controlleur.etudiants;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.EtudiantDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ListeEtudiantsServlet", urlPatterns = "/listeEtudiants")
public class ListeEtudiantsServlet extends HttpServlet {

    private EtudiantDAO etudiantDAO;

    @Override
    public void init() throws ServletException {
        // Initialiser le DAO
        etudiantDAO = new EtudiantDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les données via le modèle
            List<modele.Etudiant> etudiantList = new dao.EtudiantDAO().getAllEtudiants();

            request.setAttribute("etudiantList", etudiantList);

            // Transférer les données à la vue
            request.getRequestDispatcher("admin/vues/liste_etudiant.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des étudiants", e);
        }
    }


}

