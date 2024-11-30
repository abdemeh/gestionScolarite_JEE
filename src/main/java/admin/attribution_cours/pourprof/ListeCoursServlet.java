package admin.attribution_cours.pourprof;

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

@WebServlet(name = "ListeCoursServlet", value = "/listeCours")
public class ListeCoursServlet extends HttpServlet {

    /*private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire*/


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        /*List<Map<String, String>> cours = new ArrayList<>();

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
    }*/

        List<Cours> cours=new CoursDAO().getAllCours();
        for (Cours c : cours) {
            if (c.getEnseignant() != null) {
                // Initialisation explicite pour éviter LazyInitializationException
                c.getEnseignant().getUtilisateur().getNom();
            }
        }
        request.setAttribute("cours", cours);
        request.getRequestDispatcher("admin/attribution_cours_au_prof_par_admin.jsp").forward(request, response);


}
}
