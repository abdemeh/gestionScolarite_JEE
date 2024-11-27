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

@WebServlet(name = "SupprimerProfesseurServlet1", value = "/supprimerProfesseur1")
public class SupprimerProfesseurServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String idProfesseur = request.getParameter("id");

        if (idProfesseur == null || !idProfesseur.matches("\\d+")) {
            response.sendRedirect("listeProfesseurs");
            return;
        }

        String sqlDelete = "DELETE FROM enseignants WHERE id_enseignant = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sqlDelete)) {

            stmt.setInt(1, Integer.parseInt(idProfesseur));
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la suppression du professeur", e);
        }

        response.sendRedirect("listeProfesseurs");
    }
}

