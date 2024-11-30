package admin.liste.etudiant_professeur.ensuspens;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import admin.liste.etudiant_professeur.modele.Etudiant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "ListeEtudiantsServlet1", value = "/listeEtudiants1")
public class ListeEtudiantsServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_project";
    private static final String DB_USER = "root"; // Modifier si nécessaire
    private static final String DB_PASSWORD = "root"; // Modifier si nécessaire

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Etudiant> etudiants = new ArrayList<>();

        String sql = "SELECT e.id_etudiant, " +
                "u.nom, " +
                "u.prenom, " +
                "u.adresse, " +
                "u.email, " +
                "e.contact, " +
                "e.classe, " +
                "e.filiere " +
                "FROM etudiants e " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur";


        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                etudiants.add(new Etudiant(
                        rs.getInt("id_etudiant"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("classe"),
                        rs.getString("filiere")
                ));
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des étudiants", e);
        }

        System.out.println(etudiants);

        request.setAttribute("etudiants", etudiants);

            request.getRequestDispatcher("admin/liste_etudiant.jsp").forward(request, response);
    }
}