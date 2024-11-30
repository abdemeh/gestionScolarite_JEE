package admin.attribution_cours.poureleve;



import admin.ExecuteSchema;
import dao.EtudiantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Etudiant;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static admin.attribution_cours.poureleve.Pourelevestat.etudiant;

@WebServlet(name = "RechercherEtudiantServlet", value = "/rechercherEtudiant")
public class RechercherEtudiantServlet extends HttpServlet {

 /*   private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire

*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchEtudiant = request.getParameter("searchEtudiant");

        if (searchEtudiant == null || searchEtudiant.trim().isEmpty()) {
            request.setAttribute("message", "Veuillez saisir un critère de recherche.");
            request.getRequestDispatcher("listeInscriptions").forward(request, response);
            return;
        }

        List<Etudiant> etudiantList = new EtudiantDAO().getAllEtudiants();
        Etudiant etudiants = null;

        for (Etudiant etudiant : etudiantList) {
            // Vérifier si le critère correspond à l'ID, au prénom ou au nom
            if (etudiant.getIdEtudiant() == Integer.parseInt((searchEtudiant))) {
                Pourelevestat.etudiantparticulier=etudiant;
            }
        }

        if (etudiants==null) {
            request.setAttribute("message", "Aucun étudiant trouvé correspondant au critère.");
        } else {
            request.setAttribute("etudiants", etudiants);
        }

        request.getRequestDispatcher("listeInscriptions").forward(request, response);

        /*

        String sql = "SELECT e.id_etudiant, u.nom, u.prenom, e.classe, e.filiere, ia.date_inscription " +
                "FROM etudiants e " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur " +
                "LEFT JOIN inscriptions_annee ia ON e.id_etudiant = ia.id_etudiant " +
                "WHERE e.id_etudiant = ?";





        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, searchEtudiant); // Recherche par ID


            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                   etudiant = new HashMap<>();
                    etudiant.put("id_etudiant", rs.getString("id_etudiant"));
                    etudiant.put("nom", rs.getString("nom"));
                    etudiant.put("prenom", rs.getString("prenom"));
                    etudiant.put("classe", rs.getString("classe"));
                    etudiant.put("filiere", rs.getString("filiere"));
                    etudiant.put("date_inscription", rs.getString("date_inscription"));

                    request.setAttribute("etudiant", etudiant);
                } else {
                    request.setAttribute("message", "Aucun étudiant trouvé.");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la recherche de l'étudiant.", e);
*/
        request.getRequestDispatcher("listeInscriptions").forward(request, response);
    }


}

