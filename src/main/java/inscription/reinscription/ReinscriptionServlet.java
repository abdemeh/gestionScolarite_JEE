package inscription.reinscription;

import dao.EtudiantDAO;
import dao.InscriptionAnneeDAO;
import dao.InscriptionCoursDAO;
import dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.*;

import java.io.IOException;

@WebServlet(name = "MettreAJourEtudiant", value = "/mettreAJourEtudiant")
public class ReinscriptionServlet extends HttpServlet {

    // Configuration de la base de données

   /* private static final String DB_URL = ExecuteSchema.getDbUrl() + "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire
*/

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Charge la vue (formulaire d'inscription) sans afficher l'URL JSP
        request.getRequestDispatcher("/reinscription.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // Récupération des paramètres
        String idEtudiant = request.getParameter("id_etudiant");
        EtudiantDAO etudiantDAO=new EtudiantDAO();



        InscriptionAnnee inscriptionAnnee=new InscriptionAnnee();
        InscriptionAnneeDAO inscriptionAnneeDAO=new InscriptionAnneeDAO();

        Utilisateur utilisateur = null;
        Etudiant etudiant= null;

        InscriptionAnnee inscription = inscriptionAnneeDAO.getInscriptionByEtudiantId(Integer.parseInt(idEtudiant));

        if (inscription == null) {
            etudiant = etudiantDAO.getEtudiantById(Integer.parseInt(idEtudiant));
            if (etudiant == null) {
                request.setAttribute("message", "L'étudiant avec cet ID n'existe pas.");
                request.getRequestDispatcher("reinscription.jsp").forward(request, response);
                return;
            }
            utilisateur = etudiant.getUtilisateur();
        } else {
            request.setAttribute("message", "L'étudiant est déjà inscrit.");
            request.getRequestDispatcher("reinscription.jsp").forward(request, response);
            return;
        }




        String numeroAdresse = request.getParameter("numero_adresse");
        String adresse = request.getParameter("adresse");
        String codePostal = request.getParameter("code_postal");
        String commune = request.getParameter("commune");
        // Construction de l'adresse complète

        String adresseComplete = numeroAdresse + " " + adresse + ", " + codePostal + ", " + commune;
        utilisateur.setAdresse(adresseComplete);


        utilisateur.setEmail( request.getParameter("email"));
        etudiant.setContact( request.getParameter("contact"));
        etudiant.setClasse(Classe.valueOf(request.getParameter("classe")));
        etudiant.setFiliere(Filiere.valueOf(request.getParameter("filiere")));
        etudiant.setUtilisateur(utilisateur);


        inscriptionAnnee.setEtudiant(etudiant);


        try {

            etudiantDAO.saveEtudiant(etudiant); // Met à jour les informations de l'étudiant
            inscriptionAnneeDAO.saveInscription(inscriptionAnnee);


            request.setAttribute("message", "Les informations de l'étudiant ont été mises à jour avec succès.");
            request.getRequestDispatcher("reinscription.jsp").forward(request, response); // Redirigez vers une page de confirmation
        } catch (Exception e) {
            request.setAttribute("message", "Erreur lors de la mise à jour des informations : " + e.getMessage());
            request.getRequestDispatcher("reinscription.jsp").forward(request, response); // Retournez au formulaire avec un message d'erreur
        }


        /*
        // Requête SQL pour vérifier si l'étudiant est déjà inscrit
        String sqlCheckInscription = "SELECT COUNT(*) FROM inscriptions_annee WHERE id_etudiant = ?";

        // Requête SQL pour mettre à jour les informations de l'étudiant
        String sqlUpdate = "UPDATE etudiants e " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur " +
                "SET u.adresse = ?, u.email = ?, e.contact = ?, e.classe = ?, e.filiere = ? " +
                "WHERE e.id_etudiant = ?";

        // Requête SQL pour ajouter l'ID étudiant dans la table inscriptions_annee
        String sqlInsertInscription = "INSERT INTO inscriptions_annee (id_etudiant) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Vérifier si l'étudiant est déjà inscrit
            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheckInscription)) {
                stmtCheck.setInt(1, Integer.parseInt(idEtudiant));
                ResultSet rsCheck = stmtCheck.executeQuery();

                if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                    // Si l'étudiant est déjà inscrit, afficher un message d'erreur
                    request.setAttribute("message", "Cet étudiant est déjà inscrit pour cette année.");
                    request.getRequestDispatcher("reinscription.jsp").forward(request, response);
                    return;
                }
            }

            conn.setAutoCommit(false); // Démarrer une transaction

            // Mise à jour des informations de l'étudiant
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setString(1, adresseComplete);
                stmtUpdate.setString(2, email);
                stmtUpdate.setString(3, contact);
                stmtUpdate.setString(4, classe);
                stmtUpdate.setString(5, filiere);
                stmtUpdate.setInt(6, Integer.parseInt(idEtudiant));

                int rowsUpdated = stmtUpdate.executeUpdate();

                if (rowsUpdated > 0) {
                    // Ajout dans la table inscriptions_annee
                    try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertInscription)) {
                        stmtInsert.setInt(1, Integer.parseInt(idEtudiant));
                        stmtInsert.executeUpdate();
                    }

                    conn.commit(); // Valider la transaction
                    request.setAttribute("message", "Les informations de l'étudiant ont été mises à jour et inscrit pour l'année.");
                } else {
                    conn.rollback(); // Annuler si aucune mise à jour
                    request.setAttribute("message", "Identifiant étudiant introuvable. Veuillez vérifier les informations.");
                }
            }
        } catch (SQLException e) {
            // Gestion des erreurs SQL
            request.setAttribute("message", "Erreur lors de la mise à jour : " + e.getMessage());
        }*/


    }
}
