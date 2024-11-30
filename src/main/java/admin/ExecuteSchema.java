package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteSchema {

    // Configuration de la base de données
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USER = "root"; // à personnaliser
    private static final String DB_PASSWORD = "root"; // à personnaliser
    private static final String SCHEMA_FILE = "/db/schema.sql"; // Chemin relatif dans le classpath

    /**
     * Méthode pour créer ou initialiser la base de données en exécutant un script SQL.
     */
    public static void creationBase() {
        try {
            // Charger explicitement le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement();
                 InputStream inputStream = ExecuteSchema.class.getResourceAsStream(SCHEMA_FILE);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                System.out.println("Connexion à la base de données réussie.");

                StringBuilder sql = new StringBuilder();
                String line;

                // Lecture du fichier ligne par ligne
                while ((line = reader.readLine()) != null) {
                    // Ignorer les commentaires et les lignes vides
                    line = line.trim();
                    if (line.startsWith("--") || line.startsWith("/*") || line.endsWith("*/") || line.isEmpty()) {
                        continue;
                    }

                    sql.append(line).append(" ");
                    // Exécuter les commandes SQL lorsqu'une ligne se termine par ";"
                    if (line.endsWith(";")) {
                        statement.execute(sql.toString());
                        System.out.println("Exécuté : " + sql);
                        sql.setLength(0); // Réinitialiser le StringBuilder
                    }
                }

                System.out.println("Script SQL exécuté avec succès.");

            } catch (SQLException e) {
                System.err.println("Erreur SQL : " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.err.println("Erreur de lecture du fichier SQL : " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL non trouvé : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * Getter pour l'URL de la base de données.
     */
    public static String getDbUrl() {
        return DB_URL;
    }

    /**
     * Getter pour l'utilisateur de la base de données.
     */
    public static String getDbUser() {
        return DB_USER;
    }

    /**
     * Getter pour le mot de passe de la base de données.
     */
    public static String getDbPassword() {
        return DB_PASSWORD;
    }


}
