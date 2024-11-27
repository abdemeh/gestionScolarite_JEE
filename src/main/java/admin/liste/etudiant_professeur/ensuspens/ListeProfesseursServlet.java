package admin.liste.etudiant_professeur.ensuspens;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import admin.liste.etudiant_professeur.modele.Professeur;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe Professeur pour représenter les données


@WebServlet(name = "ListeProfesseursServlet1", value = "/listeProfesseurs1")
public class ListeProfesseursServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root"; // Modifier si nécessaire
    private static final String DB_PASSWORD = "1234"; // Modifier si nécessaire

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Professeur> professeurs = new ArrayList<>();

        String sql = "SELECT p.id_enseignant, u.nom, u.prenom, u.adresse, u.email, p.contact, p.specialite " +
                "FROM enseignants p " +
                "JOIN utilisateurs u ON p.id_utilisateur = u.id_utilisateur";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                professeurs.add(new Professeur(
                        rs.getInt("id_enseignant"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("specialite")
                ));
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des professeurs", e);
        }

        request.setAttribute("professeurs", professeurs);
        request.getRequestDispatcher("admin/liste_professeur.jsp").forward(request, response);
    }
}

