package inscription.reinscription;

import admin.ExecuteSchema;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "MettreAJourEtudiant", value = "/mettreAJourEtudiant")
public class MettreAJourEtudiant extends HttpServlet {

    // Configuration de la base de données
    private static final String DB_URL = ExecuteSchema.getDbUrl() + "/jee_project";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Charge la vue (formulaire d'inscription) sans afficher l'URL JSP
        request.getRequestDispatcher("/reinscription.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des paramètres
        String idEtudiant = request.getParameter("id_etudiant");
        String numeroAdresse = request.getParameter("numero_adresse");
        String adresse = request.getParameter("adresse");
        String codePostal = request.getParameter("code_postal");
        String commune = request.getParameter("commune");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        String classe = request.getParameter("classe");
        String filiere = request.getParameter("filiere");

        // Validation de l'ID étudiant
        if (idEtudiant == null || idEtudiant.isEmpty() || !idEtudiant.matches("\\d+")) {
            request.setAttribute("message", "L'identifiant étudiant doit être un entier positif.");
            request.getRequestDispatcher("reinscription.jsp").forward(request, response);
            return;
        }

        // Validation des autres champs obligatoires
        if (numeroAdresse == null || adresse == null || codePostal == null || commune == null || email == null
                || contact == null || classe == null || filiere == null ||
                numeroAdresse.isEmpty() || adresse.isEmpty() || codePostal.isEmpty() || commune.isEmpty() ||
                email.isEmpty() || contact.isEmpty() || classe.isEmpty() || filiere.isEmpty()) {
            request.setAttribute("message", "Tous les champs sont obligatoires.");
            request.getRequestDispatcher("reinscription.jsp").forward(request, response);
            return;
        }

        // Construction de l'adresse complète
        String adresseComplete = numeroAdresse + " " + adresse + ", " + codePostal + ", " + commune;

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
        }

        // Redirection vers la page d'inscription
        request.getRequestDispatcher("reinscription.jsp").forward(request, response);
    }
}
