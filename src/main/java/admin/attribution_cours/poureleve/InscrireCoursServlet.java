package admin.attribution_cours.poureleve;

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

import static admin.attribution_cours.poureleve.Pourelevestat.*;

@WebServlet(name = "InscrireCoursServlet", value = "/inscrireCours")
public class InscrireCoursServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEtudiant =etudiant.get("id_etudiant");
        String idCours = ListeProfesseurpourcecours.get(0).get("id_cours");
        String dateInscription = request.getParameter("date_inscription");

        if (idEtudiant == null || idCours == null || dateInscription == null) {
            request.setAttribute("message", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("listeInscriptions").forward(request, response);
            return;
        }

        String sqlInsertInscriptions = "INSERT INTO inscriptions_cours (id_etudiant, id_cours, date_inscription) VALUES (?, ?, ?)";
        String sqlInsertResultats = "INSERT INTO resultats (id_etudiant, id_cours, note) VALUES (?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement stmtInscriptions = conn.prepareStatement(sqlInsertInscriptions);
                 PreparedStatement stmtResultats = conn.prepareStatement(sqlInsertResultats)) {

                stmtInscriptions.setInt(1, Integer.parseInt(idEtudiant));
                stmtInscriptions.setInt(2, Integer.parseInt(idCours));
                stmtInscriptions.setString(3, dateInscription);

                stmtResultats.setInt(1, Integer.parseInt(idEtudiant));
                stmtResultats.setInt(2, Integer.parseInt(idCours));

                stmtInscriptions.executeUpdate();
                stmtResultats.executeUpdate();

                request.setAttribute("message", "Inscription réussie !");
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Erreur lors de l'inscription : " + e.getMessage());
        }

        response.sendRedirect("listeInscriptions");
    }
}

