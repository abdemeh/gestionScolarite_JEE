package inscription.ensuspens.creation;

import admin.ExecuteSchema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreationEleveDB {

    // Configuration de la base de données
    private static final String DB_URL = ExecuteSchema.getDbUrl() + "/jee_projet";

    // Méthode pour établir une connexion à la base de données
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, ExecuteSchema.getDbUser(), ExecuteSchema.getDbPassword());
    }

    // Méthode pour ajouter un élève dans la base de données
    public static String ajouterEleve(String nom, String prenom, String telephone, String email, String mot_de_passe,
                                      String adresse, String dateNaissance, String classe, String filiere)
            throws SQLException, ClassNotFoundException {

        // Vérification si l'email existe déjà dans la base de données
        String checkEmailQuery = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
        String sqlUtilisateur = "INSERT INTO utilisateurs (nom, prenom, adresse, email, mot_de_passe, id_role) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlEtudiant = "INSERT INTO etudiants (date_naissance, contact, classe, filiere, id_utilisateur) VALUES (?, ?, ?, ?, ?)";

        // Connexion à la base et insertion des données
        try (Connection conn = getConnection()) {

            // Vérification si l'email est déjà utilisé
            try (PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    return "Erreur : L'email est déjà utilisé."; // Email déjà utilisé
                }
            }

            // Requête SQL pour insérer un utilisateur

            // Étape 1 : Insérer l'utilisateur
            try (PreparedStatement stmtUtilisateur = conn.prepareStatement(sqlUtilisateur, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtUtilisateur.setString(1, nom);
                stmtUtilisateur.setString(2, prenom);
                stmtUtilisateur.setString(3, adresse);
                stmtUtilisateur.setString(4, email);
                stmtUtilisateur.setString(5, mot_de_passe); // Assurez-vous de hacher le mot de passe
                stmtUtilisateur.setInt(6, 3); // Rôle étudiant (par exemple, ID 3)

                int rowsUtilisateur = stmtUtilisateur.executeUpdate();
                if (rowsUtilisateur > 0) {
                    // Récupérer l'ID utilisateur généré
                    try (ResultSet rs = stmtUtilisateur.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idUtilisateur = rs.getInt(1); // Récupérer l'ID

                            // Étape 2 : Insérer les détails de l'étudiant
                            try (PreparedStatement stmtEtudiant = conn.prepareStatement(sqlEtudiant)) {
                                stmtEtudiant.setString(1, dateNaissance);
                                stmtEtudiant.setString(2, telephone); // Contact
                                stmtEtudiant.setString(3, classe);
                                stmtEtudiant.setString(4, filiere);
                                stmtEtudiant.setInt(5, idUtilisateur); // Liaison avec l'utilisateur

                                int rowsEtudiant = stmtEtudiant.executeUpdate();
                                if (rowsEtudiant > 0) {
                                    System.out.println("L'étudiant a été enregistré avec succès !");
                                } else {
                                    System.err.println("Erreur : Les détails de l'étudiant n'ont pas pu être enregistrés.");
                                }
                            }
                        }
                    }
                } else {
                    System.err.println("Erreur : L'utilisateur n'a pas pu être enregistré.");
                    return "Erreur lors de l'enregistrement de l'utilisateur.";
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de l'enregistrement : " + e.getMessage());
            throw e; // Relancer l'exception pour permettre au code appelant de la gérer
        }

        return "L'élève a été ajouté avec succès.";
    }
}
