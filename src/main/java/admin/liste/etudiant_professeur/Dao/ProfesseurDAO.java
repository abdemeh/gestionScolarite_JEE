package admin.liste.etudiant_professeur.Dao;

import admin.liste.etudiant_professeur.modele.Professeur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesseurDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jee_projet";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public List<Professeur> getAllProfesseurs() throws SQLException {
        List<Professeur> professeurs = new ArrayList<>();
        String sql = "SELECT p.id_enseignant, u.nom, u.prenom, u.adresse, u.email, p.contact, p.specialite " +
                "FROM enseignants p " +
                "JOIN utilisateurs u ON p.id_utilisateur = u.id_utilisateur";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Professeur professeur = new Professeur(
                        rs.getInt("id_enseignant"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getString("contact"),
                        rs.getString("specialite")
                );
                professeurs.add(professeur);
            }
        }
        return professeurs;
    }

    // Méthode pour supprimer un professeur
    public boolean supprimerProfesseur(int idProfesseur) throws SQLException {
        String sql = "DELETE FROM enseignants WHERE id_enseignant = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfesseur);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // Renvoie true si la suppression a réussi
        }
    }
}
