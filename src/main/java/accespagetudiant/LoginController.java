package accespagetudiant;

import admin.ExecuteSchema;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@WebServlet(name = "LoginController", value = "/loginController")
public class LoginController extends HttpServlet {
    private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige vers la page de connexion
        request.getRequestDispatcher("/etudiant/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEtudiant = request.getParameter("id_etudiant");
        String motDePasseSaisi = request.getParameter("mot_de_passe");

        // Connexion à la base de données pour vérifier les identifiants
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // Récupérer le mot de passe de l'étudiant depuis la table utilisateur
            String sql = "SELECT mot_de_passe " +
                    "FROM utilisateurs " +
                    "JOIN etudiants ON utilisateurs.id_utilisateur = etudiants.id_utilisateur " +
                    "WHERE etudiants.id_etudiant = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idEtudiant);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String motDePasseStocke = rs.getString("mot_de_passe");

                // Comparer le mot de passe saisi avec celui stocké
                if (motDePasseStocke.equals(motDePasseSaisi)) {
                    // L'étudiant est authentifié, récupérer ses notes
                    sql = "SELECT c.nom_cours, r.note FROM resultats r JOIN cours c ON r.id_cours = c.id_cours WHERE r.id_etudiant = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, idEtudiant);
                    rs = stmt.executeQuery();

                    List<Note> notes = new ArrayList<>();
                    List<Horaire_cours> datecours=new ArrayList<>();

                    while (rs.next()) {
                        String coursNom = rs.getString("nom_cours");
                        int note = rs.getInt("note");
                        notes.add(new Note(coursNom, note));
                    }
                    sql = "SELECT c.nom_cours, i.date_inscription " +
                            "FROM cours c " +
                            "JOIN inscriptions_cours i ON c.id_cours = i.id_cours " +
                            "WHERE i.id_etudiant = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, idEtudiant);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        String coursNom = rs.getString("nom_cours");
                        Timestamp enrollmentDate= Timestamp.valueOf(rs.getString("date_inscription"));
                        datecours.add(new Horaire_cours(coursNom, enrollmentDate));
                    }


                    // Passer la liste des notes à la page JSP
                    request.setAttribute("notes", notes);
                    request.setAttribute("date_cours",datecours);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("etudiant/notes.jsp");
                    dispatcher.forward(request, response);
                } else {
                    // Si les mots de passe ne correspondent pas, afficher un message d'erreur
                    request.setAttribute("error", "Mot de passe incorrect.");
                    request.getRequestDispatcher("etudiant/login.jsp").forward(request, response);
                }
            } else {
                // Si l'étudiant n'existe pas dans la base de données
                request.setAttribute("error", "Identifiant étudiant incorrect.");
                request.getRequestDispatcher("etudiant/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la connexion à la base de données.");
            request.getRequestDispatcher("etudiant/login.jsp").forward(request, response);
        }
    }
}
