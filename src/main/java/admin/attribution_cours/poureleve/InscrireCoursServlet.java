package admin.attribution_cours.poureleve;

import admin.ExecuteSchema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "InscrireCoursServlet", value = "/inscrireCours")
public class InscrireCoursServlet extends HttpServlet {

    private static final String DB_URL = ExecuteSchema.getDbUrl() + "/jee_project";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des paramètres
        String idEtudiant = request.getParameter("id_etudiant");
        String idCours = request.getParameter("id_cours");
        String dateCours = request.getParameter("dateCours"); // Date sélectionnée
        String debutHeure = request.getParameter("debutHeure"); // Heure de début
        String finHeure = request.getParameter("finHeure"); // Heure de fin

        if (idEtudiant == null || idCours == null || dateCours == null || debutHeure == null || finHeure == null) {
            request.setAttribute("message", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("listeInscriptions").forward(request, response);
            return;
        }

        // Combiner la date et les heures pour obtenir les timestamps complets
        String debutCours = dateCours + "T" + debutHeure;
        String finCours = dateCours + "T" + finHeure;

        // Vérification que l'heure de fin est après l'heure de début
        if (debutCours.compareTo(finCours) >= 0) {
            request.setAttribute("message", "L'heure de fin doit être après l'heure de début.");
            request.getRequestDispatcher("listeInscriptions").forward(request, response);
            return;
        }

        // Requêtes SQL
        String sqlInsertInscriptions = "INSERT INTO inscriptions_cours (id_etudiant, id_cours, debut_cours, fin_cours) VALUES (?, ?, ?, ?)";
        String sqlInsertResultats = "INSERT IGNORE INTO resultats (id_etudiant, id_cours, note) VALUES (?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement stmtInscriptions = conn.prepareStatement(sqlInsertInscriptions);
                 PreparedStatement stmtResultats = conn.prepareStatement(sqlInsertResultats)) {

                // Préparation de l'insertion des inscriptions
                stmtInscriptions.setInt(1, Integer.parseInt(idEtudiant));
                stmtInscriptions.setInt(2, Integer.parseInt(idCours));
                stmtInscriptions.setString(3, debutCours);
                stmtInscriptions.setString(4, finCours);

                // Préparation de l'insertion dans les résultats
                stmtResultats.setInt(1, Integer.parseInt(idEtudiant));
                stmtResultats.setInt(2, Integer.parseInt(idCours));

                // Exécution des requêtes
                stmtInscriptions.executeUpdate();
                stmtResultats.executeUpdate();

                request.setAttribute("message", "Inscription réussie !");
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Erreur lors de l'inscription : " + e.getMessage());
        }

        // Redirection vers la liste des inscriptions
        response.sendRedirect("listeInscriptions");
    }
}
