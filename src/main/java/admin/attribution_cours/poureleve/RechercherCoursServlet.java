package admin.attribution_cours.poureleve;

import admin.ExecuteSchema;
import dao.CoursDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Cours;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static admin.attribution_cours.poureleve.Pourelevestat.ListeProfesseurpourcecours;


@WebServlet(name = "RechercherCoursServlet", value = "/rechercherCours")
public class RechercherCoursServlet extends HttpServlet {
   /* private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire
*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        String searchCours = request.getParameter("searchCours");

        if (searchCours == null || searchCours.trim().isEmpty()) {
            request.setAttribute("message", "Veuillez saisir un critère de recherche.");
            request.getRequestDispatcher("inscription_eleve_cours_par_admin.jsp").forward(request, response);
            return;
        }

        List<Cours> cours = new CoursDAO().getAllCours();
        List<Cours> courscherche = new ArrayList<>(); // Initialiser une liste vide

        try {
            // Rechercher dans les cours par nom ou par ID
            for (Cours lecours : cours) {
                if (lecours.getNomCours().equalsIgnoreCase(searchCours)) {
                    courscherche.add(lecours);
                } else {
                    try {
                        int idSearchCours = Integer.parseInt(searchCours); // Conversion en entier si possible
                        if (lecours.getIdCours() == idSearchCours) {
                            courscherche.add(lecours);
                        }
                    } catch (NumberFormatException e) {
                        // Ignorer si searchCours n'est pas un entier
                    }
                }
            }

            // Si aucun cours trouvé
            if (courscherche.isEmpty()) {
                request.setAttribute("message", "Aucun cours correspondant n'a été trouvé.");
                request.getRequestDispatcher("inscription_eleve_cours_par_admin.jsp").forward(request, response);
                return;
            }

            // Cours trouvé, rediriger vers la liste des inscriptions
            request.setAttribute("cours", courscherche);
            request.getRequestDispatcher("listeInscriptions").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", "Erreur lors de la recherche : " + e.getMessage());
            request.getRequestDispatcher("inscription_eleve_cours_par_admin.jsp").forward(request, response);
        }

       /* if (searchCours == null || searchCours.trim().isEmpty()) {
            request.setAttribute("message", "Veuillez saisir un critère de recherche.");
            request.getRequestDispatcher("inscription_eleve_cours_par_admin.jsp").forward(request, response);
            return;
        }

        String sql = "SELECT c.id_cours, c.nom_cours, u.nom AS nom_professeur " +
                "FROM cours c " +
                "JOIN enseignants e ON c.id_enseignant = e.id_enseignant " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur " +
                "WHERE c.nom_cours LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchCours + "%"); // Recherche par nom du cours


            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    HashMap<String,String>cours = new HashMap<>();
                    cours.put("id_cours", rs.getString("id_cours"));
                    cours.put("nom_cours", rs.getString("nom_cours"));
                    cours.put("nom_professeur", rs.getString("nom_professeur"));
                    ListeProfesseurpourcecours.add(cours);
                    while (rs.next()){
                        cours.put("id_cours", rs.getString("id_cours"));
                        cours.put("nom_cours", rs.getString("nom_cours"));
                        cours.put("nom_professeur", rs.getString("nom_professeur"));
                        ListeProfesseurpourcecours.add(cours);

                    }

                    request.setAttribute("cours", cours);
                } else {
                    request.setAttribute("message", "Aucun cours trouvé.");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la recherche du cours.", e);


        }*/


    }
}
