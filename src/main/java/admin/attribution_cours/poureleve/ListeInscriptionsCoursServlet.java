package admin.attribution_cours.poureleve;

import dao.InscriptionCoursDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.InscriptionCours;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListeInscriptionsServlet", value = "/listeInscriptions")
public class ListeInscriptionsCoursServlet extends HttpServlet {

  /*  private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire
*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<InscriptionCours> inscriptions=new InscriptionCoursDAO().getAllInscriptions();



        request.setAttribute("inscriptions", inscriptions);
        request.getRequestDispatcher("admin/inscription_eleve_cours_par_admin.jsp").forward(request, response);
       /* List<Map<String, String>> inscriptions = new ArrayList<>();

        String sql = "SELECT e.id_etudiant, e.classe, e.filiere, c.nom_cours, u.nom AS nom_professeur, i.date_inscription " +
                "FROM inscriptions_cours i " +
                "JOIN etudiants e ON i.id_etudiant = e.id_etudiant " +
                "JOIN cours c ON i.id_cours = c.id_cours " +
                "LEFT JOIN enseignants en ON c.id_enseignant = en.id_enseignant " +
                "LEFT JOIN utilisateurs u ON en.id_utilisateur = u.id_utilisateur";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> inscription = new HashMap<>();
                inscription.put("id_etudiant", rs.getString("id_etudiant"));
                inscription.put("classe", rs.getString("classe"));
                inscription.put("filiere", rs.getString("filiere"));
                inscription.put("nom_cours", rs.getString("nom_cours"));
                inscription.put("nom_professeur", rs.getString("nom_professeur"));
                inscription.put("date_inscription", rs.getString("date_inscription"));
                inscriptions.add(inscription);
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des inscriptions.", e);
        }

        request.setAttribute("inscriptions", inscriptions);
        request.getRequestDispatcher("admin/inscription_eleve_cours_par_admin.jsp").forward(request, response);

    */
    }

}
