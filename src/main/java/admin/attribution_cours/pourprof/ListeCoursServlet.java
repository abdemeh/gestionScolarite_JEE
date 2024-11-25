package admin.attribution_cours.pourprof;

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

@WebServlet(name = "ListeCoursServlet", value = "/listeCours")
public class ListeCoursServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        List<Map<String, String>> cours = new ArrayList<>();

        String sql = "SELECT id_cours, nom_cours, description, id_enseignant FROM cours";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> coursInfo = new HashMap<>();
                coursInfo.put("id_cours", String.valueOf(rs.getInt("id_cours")));
                coursInfo.put("nom_cours", rs.getString("nom_cours"));
                coursInfo.put("description", rs.getString("description"));
                coursInfo.put("id_enseignant", rs.getString("id_enseignant"));
                cours.add(coursInfo);
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des cours.", e);
        }

        request.setAttribute("cours", cours);
        request.getRequestDispatcher("admin/attribution_cours_au_prof_par_admin.jsp").forward(request, response);
    }

}
