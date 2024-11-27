package accespageprof;

import admin.ExecuteSchema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ModifierNotesServlet", value = "/modifierNotes")
public class ModifierNotesServlet extends HttpServlet {

    private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] idResultats = request.getParameterValues("id_resultat");
        String idProfesseur = Professeur_id.id;

        if (idResultats == null || idResultats.length == 0 || idProfesseur == null || idProfesseur.isEmpty()) {
            request.setAttribute("message", "Aucune note ou ID professeur fourni pour la mise à jour.");
            request.getRequestDispatcher("gestion_notes.jsp").forward(request, response);
            return;
        }

        String sqlUpdate = "UPDATE resultats SET note = ? WHERE id_resultat = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            conn.setAutoCommit(false); // Démarrer une transaction pour la mise à jour des notes

            try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
                for (String idResultat : idResultats) {
                    String noteParam = "note_" + idResultat;
                    String noteValue = request.getParameter(noteParam);

                    if (noteValue != null && !noteValue.isEmpty()) {
                        stmt.setBigDecimal(1, new BigDecimal(noteValue));
                        stmt.setInt(2, Integer.parseInt(idResultat));
                        stmt.executeUpdate();
                    }
                }

                conn.commit(); // Valider les modifications
                request.setAttribute("message", "Les notes ont été mises à jour avec succès.");
            } catch (SQLException e) {
                conn.rollback(); // Annuler la transaction en cas d'erreur
                request.setAttribute("message", "Erreur lors de la mise à jour des notes : " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la connexion à la base de données.", e);
        }

        // Charger les résultats mis à jour pour le professeur
        String sqlResultats = "SELECT r.id_resultat, e.id_etudiant, u.nom, u.prenom, c.nom_cours, r.note " +
                "FROM resultats r " +
                "JOIN etudiants e ON r.id_etudiant = e.id_etudiant " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur " +
                "JOIN cours c ON r.id_cours = c.id_cours " +
                "WHERE c.id_enseignant = ?";

        List<Map<String, String>> resultats = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmtResultats = conn.prepareStatement(sqlResultats)) {

            stmtResultats.setInt(1, Integer.parseInt(idProfesseur));

            try (ResultSet rs = stmtResultats.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> resultat = new HashMap<>();
                    resultat.put("id_resultat", rs.getString("id_resultat"));
                    resultat.put("id_etudiant", rs.getString("id_etudiant"));
                    resultat.put("nom", rs.getString("nom"));
                    resultat.put("prenom", rs.getString("prenom"));
                    resultat.put("nom_cours", rs.getString("nom_cours"));
                    resultat.put("note", rs.getString("note"));
                    resultats.add(resultat);
                }
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Erreur lors de la récupération des résultats mis à jour : " + e.getMessage());
        }


        response.sendRedirect("listeResultats");
    }
}
