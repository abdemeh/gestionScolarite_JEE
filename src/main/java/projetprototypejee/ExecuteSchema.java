package projetprototypejee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ExecuteSchema {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String username = "root";//à changer
        String password = "1234";//à changer
        String schemaFilePath = "src/main/resources/db/schema.sql"; // Chemin du fichier SQL

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password);
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(schemaFilePath))) {

            System.out.println("Connexion à la base de données réussie.");

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignorer les commentaires dans le fichier SQL
                if (!line.startsWith("--") && !line.trim().isEmpty()) {
                    sql.append(line);
                    if (line.trim().endsWith(";")) {
                        // Exécuter chaque commande SQL
                        statement.execute(sql.toString());
                        System.out.println("Exécuté : " + sql);
                        sql.setLength(0); // Réinitialiser le StringBuilder pour la prochaine commande
                    }
                }
            }

            System.out.println("Script SQL exécuté avec succès.");

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier SQL : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution du script SQL : " + e.getMessage());
        }
    }
}
