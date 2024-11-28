package accespagetudiant;

import admin.ExecuteSchema;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servletexecutionbdd.SchemaInitializer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoginController", value = "/loginController")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirige vers la page de connexion
        request.getRequestDispatcher("/etudiant/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEtudiant = request.getParameter("id_etudiant");
        String motDePasseSaisi = request.getParameter("mot_de_passe");

        // Connexion à la base de données pour vérifier les identifiants
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jee_projet", ExecuteSchema.getDbUser(), ExecuteSchema.getDbPassword());

            // Récupérer le mot de passe de l'étudiant depuis la table utilisateur
            String sql = "SELECT mot_de_passe FROM utilisateur WHERE id_utilisateur = (SELECT id_utilisateur FROM etudiants WHERE id_etudiant = ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idEtudiant);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String motDePasseStocke = rs.getString("mot_de_passe");

                // Comparer le mot de passe saisi avec celui stocké
                if (motDePasseStocke.equals(motDePasseSaisi)) {
                    // L'étudiant est authentifié, récupérer ses notes
                    sql = "SELECT c.nom_cours, r.note FROM resultat r JOIN cours c ON r.id_cours = c.id_cours WHERE r.id_etudiant = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, idEtudiant);
                    rs = stmt.executeQuery();

                    List<Note> notes = new ArrayList<>();

                    while (rs.next()) {
                        String coursNom = rs.getString("nom_cours");
                        int note = rs.getInt("note");
                        notes.add(new Note(coursNom, note));
                    }

                    // Passer la liste des notes à la page JSP
                    request.setAttribute("notes", notes);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("etudiant/notes.jsp");
                    dispatcher.forward(request, response);
                } else {
                    // Si les mots de passe ne correspondent pas
                    response.sendRedirect("etudiant/login.jsp?error=true");
                }
            } else {
                // Si l'étudiant n'existe pas dans la base de données
                response.sendRedirect("etudiant/login.jsp?error=true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("etudiant/login.jsp?error=true");
        }
    }
}
