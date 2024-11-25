package projetprototypejee.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreationEleveDB {

    // Configuration de la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root"; // À personnaliser
    private static final String DB_PASSWORD = "1234"; // À personnaliser

    // Méthode pour établir une connexion à la base de données
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Méthode pour ajouter un élève dans la base de données
    public static void ajouterEleve(String nom, String prenom, String telephone, String email,String mot_de_passe,
                                    String adresse, String dateNaissance,String classe,String filiere)
            throws SQLException, ClassNotFoundException {
        String sqlUtilisateur = "INSERT INTO utilisateurs (nom, prenom,  adresse,email, mot_de_passe, id_role) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlEtudiant = "INSERT INTO etudiants (date_naissance, contact, classe, filiere, id_utilisateur) VALUES (?, ?, ?, ?, ?)";


        // Connexion à la base et insertion des données
        try (Connection conn = getConnection()) {
            // Requête SQL pour insérer un utilisateur

            // Étape 1 : Insérer l'utilisateur
            try (PreparedStatement stmtUtilisateur = conn.prepareStatement(sqlUtilisateur, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtUtilisateur.setString(1, nom);
                stmtUtilisateur.setString(2, prenom);
                stmtUtilisateur.setString(3, adresse);
                stmtUtilisateur.setString(4, email);
                stmtUtilisateur.setString(5, mot_de_passe); // Assurez-vous de hacher le mot de passe
                stmtUtilisateur.setInt(6, 2); // Rôle étudiant (par exemple, ID 2)

                int rowsUtilisateur = stmtUtilisateur.executeUpdate();
                if (rowsUtilisateur > 0) {
                    // Récupérer l'ID utilisateur généré
                    try (var rs = stmtUtilisateur.getGeneratedKeys()) {
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
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de l'enregistrement : " + e.getMessage());
            throw e; // Relancer l'exception pour permettre au code appelant de la gérer
        }
    }
}
