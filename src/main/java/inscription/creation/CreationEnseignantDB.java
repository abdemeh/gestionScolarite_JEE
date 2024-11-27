package inscription.creation;

import java.sql.*;

public class CreationEnseignantDB {

    // Configuration de la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root"; // Modifier si nécessaire
    private static final String DB_PASSWORD = "1234"; // Modifier si nécessaire

    // Méthode pour établir une connexion à la base de données
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Méthode pour ajouter un enseignant dans la base de données
    public static void ajouterEnseignant(String nom, String prenom, String telephone, String email, String motDePasse,
                                         String adresse, String dateNaissance, String specialite)
            throws SQLException, ClassNotFoundException {

        // Requête SQL pour insérer un utilisateur
        String sqlUtilisateur = "INSERT INTO utilisateurs (nom, prenom, adresse, email, mot_de_passe, id_role) VALUES (?, ?, ?, ?, ?, ?)";

        // Requête SQL pour insérer un enseignant
        String sqlEnseignant = "INSERT INTO enseignants (date_naissance, contact, specialite, id_utilisateur) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // Commence une transaction

            // Étape 1 : Insérer les données dans la table utilisateurs
            int idUtilisateur; // ID généré pour l'utilisateur
            try (PreparedStatement stmtUtilisateur = conn.prepareStatement(sqlUtilisateur, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtUtilisateur.setString(1, nom);
                stmtUtilisateur.setString(2, prenom);
                stmtUtilisateur.setString(3, adresse);
                stmtUtilisateur.setString(4, email);
                stmtUtilisateur.setString(5, motDePasse); // Remarque : pensez à hacher le mot de passe en production
                stmtUtilisateur.setInt(6, 2); // Rôle = Enseignant (par exemple 2)

                int rowsUtilisateur = stmtUtilisateur.executeUpdate();
                if (rowsUtilisateur == 0) {
                    throw new SQLException("Échec de l'insertion dans la table utilisateurs.");
                }

                // Récupérer l'ID utilisateur généré
                try (ResultSet rs = stmtUtilisateur.getGeneratedKeys()) {
                    if (rs.next()) {
                        idUtilisateur = rs.getInt(1);
                    } else {
                        throw new SQLException("Impossible de récupérer l'ID utilisateur généré.");
                    }
                }
            }

            // Étape 2 : Insérer les données dans la table enseignants
            try (PreparedStatement stmtEnseignant = conn.prepareStatement(sqlEnseignant)) {
                stmtEnseignant.setString(1, dateNaissance); // Date de naissance
                stmtEnseignant.setString(2, telephone); // Contact
                stmtEnseignant.setString(3, specialite); // Spécialité
                stmtEnseignant.setInt(4, idUtilisateur); // ID utilisateur lié

                int rowsEnseignant = stmtEnseignant.executeUpdate();
                if (rowsEnseignant == 0) {
                    throw new SQLException("Échec de l'insertion dans la table enseignants.");
                }
            }

            conn.commit(); // Valide la transaction
            System.out.println("Enseignant ajouté avec succès !");
        } catch (SQLException | ClassNotFoundException e) {
            // Gestion des erreurs et rollback en cas d'échec
            System.err.println("Erreur lors de l'ajout de l'enseignant : " + e.getMessage());
            throw e; // Relance l'exception pour gestion externe
        }
    }
}
