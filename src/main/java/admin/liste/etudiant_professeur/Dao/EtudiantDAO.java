package admin.liste.etudiant_professeur.Dao;


import admin.liste.etudiant_professeur.modele.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// DAO pour gérer les opérations liées à l'entité Etudiant
public class EtudiantDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root"; // Modifier si nécessaire
    private static final String DB_PASSWORD = "1234"; // Modifier si nécessaire

    public List<Etudiant> getAllEtudiants() throws SQLException {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT e.id_etudiant, " +
                "u.nom, " +
                "u.prenom, " +
                "u.adresse, " +
                "u.email, " +
                "e.contact, " +
                "e.classe, " +
                "e.filiere " +
                "FROM etudiants e " +
                "JOIN utilisateurs u ON e.id_utilisateur = u.id_utilisateur";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Etudiant etudiant = new Etudiant(
                        rs.getInt("id_etudiant"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("classe"),
                        rs.getString("filiere")
                );
                etudiants.add(etudiant);
            }
        }
        return etudiants;
    }

    public void supprimerEtudiant(int idEtudiant) throws SQLException {
        String sql = "DELETE FROM etudiants WHERE id_etudiant = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEtudiant);
            stmt.executeUpdate();
        }
    }



}

