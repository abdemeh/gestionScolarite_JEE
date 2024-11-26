package prof;

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

@WebServlet(name = "ListeResultatsServlet", value = "/listeResultats")
public class ListeResultatsServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet?useSSL=false&serverTimezone=UTC";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idProfesseur = request.getParameter("id_enseignant");
        String motDePasse = request.getParameter("mot_de_passe");

       Professeur_id.id=idProfesseur;



        if (idProfesseur == null || motDePasse == null || idProfesseur.isEmpty() || motDePasse.isEmpty()) {
            request.setAttribute("message", "ID professeur et mot de passe requis.");
            request.getRequestDispatcher("prof/connexion_prof.jsp").forward(request, response);
            return;
        }


        // Étape 1 : Vérifier le mot de passe de l'enseignant
        String sqlVerification = "SELECT u.mot_de_passe " +
                "FROM enseignants e " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur " +
                "WHERE e.id_enseignant = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmtVerification = conn.prepareStatement(sqlVerification)) {

            stmtVerification.setInt(1, Integer.parseInt(idProfesseur));
            try (ResultSet rs = stmtVerification.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("mot_de_passe");
                    if (!storedPassword.equals(motDePasse)) {
                        request.setAttribute("message", "Mot de passe incorrect.");
                        request.getRequestDispatcher("prof/connexion_prof.jsp").forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("message", "ID professeur introuvable.");
                    request.getRequestDispatcher("prof/connexion_prof.jsp").forward(request, response);
                    return;
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la vérification des informations.", e);
        }

        // Étape 2 : Récupérer les résultats liés au professeur
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
            throw new ServletException("Erreur lors de la récupération des résultats.", e);
        }

        request.setAttribute("resultats", resultats);
        request.getRequestDispatcher("prof/gestion_notes.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige vers la méthode doPost pour gérer les requêtes de la même manière
        doPost(request, response);
    }
}
