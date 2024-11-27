package admin.attribution_cours.pourprof;

import admin.ExecuteSchema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RechercherProfesseurServlet", value = "/rechercherProfesseur")
public class RechercherProfesseurServlet extends HttpServlet {

    private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search"); // Récupère le terme de recherche

        if (search == null || search.trim().isEmpty()) {
            request.setAttribute("message", "Veuillez entrer un critère de recherche (nom ou prénom).");
            request.getRequestDispatcher("attribution_cours_au_prof_par_admin.jsp").forward(request, response);
            return;
        }

        // Requête SQL pour chercher les professeurs
        String sql = "SELECT e.id_enseignant, u.nom, u.prenom " +
                "FROM enseignants e " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur " +
                "WHERE u.nom LIKE ? OR u.prenom LIKE ? OR e.id_enseignant LIKE ?";


        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Appliquer les paramètres de recherche pour le nom, prénom et ID
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            stmt.setString(3, "%" + search + "%");
            ;
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    Pourprofstat.prof.put("id_enseignant", String.valueOf(rs.getInt("id_enseignant")));
                    Pourprofstat.prof.put("nom", rs.getString("nom"));
                    Pourprofstat.prof.put("prenom", rs.getString("prenom"));
                    // Ajouter le professeur à la liste
                }
            }

            if (Pourprofstat.prof.isEmpty()) {
                request.setAttribute("message", "Aucun professeur trouvé pour la recherche : " + search);
            } else {
                request.setAttribute("professeurs", Pourprofstat.prof);
            }

        } catch (SQLException e) {
            request.setAttribute("message", "Erreur lors de la recherche : " + e.getMessage());
        }

        // Retourner vers la page JSP
        request.getRequestDispatcher("listeCours").forward(request, response);
    }

}
