package admin.liste.etudiant_professeur.ensuspens;

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

@WebServlet(name = "SupprimerEtudiantServlet1", value = "/supprimerEtudiant1")
public class SupprimerEtudiantServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_project";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String idEtudiant = request.getParameter("id");

        if (idEtudiant == null || !idEtudiant.matches("\\d+")) {
            response.sendRedirect("listeEtudiants");
            return;
        }

        String sqlDelete = "DELETE FROM etudiants WHERE id_etudiant = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.setInt(1, Integer.parseInt(idEtudiant));
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la suppression de l'étudiant", e);
        }

        response.sendRedirect("listeEtudiants");
    }
}
