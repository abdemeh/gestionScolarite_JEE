package springboot;

import admin.ExecuteSchema;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboot.modelesringboot.Etudiant;
import springboot.repository.EtudiantRepository;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ExecuteSchema.creationBase();
        SpringApplication.run(Main.class, args);


    }

    @Bean
    public CommandLineRunner testDatabaseConnection(EtudiantRepository etudiantRepository) {
        return args -> {
            System.out.println("Test de connexion à la base de données...");
            try {
                long count = etudiantRepository.count();
                System.out.println("Connexion réussie !");
                System.out.println("Nombre d'étudiants dans la base : " + count);

                // Afficher la liste des étudiants
                afficherListeEtudiants(etudiantRepository);
            } catch (Exception e) {
                System.err.println("Erreur lors de l'accès à la base de données !");
                e.printStackTrace();
            }
        };
    }

    private void afficherListeEtudiants(EtudiantRepository etudiantRepository) {
        System.out.println("\nListe des étudiants :");
        List<Etudiant> etudiants = etudiantRepository.findAll();
        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant trouvé dans la base.");
        } else {
            /*etudiants.forEach(etudiant -> {
               // System.out.println("ID: " + etudiant.getIdEtudiant()
                        + ", Nom: " + etudiant.getUtilisateur().getNom()
                        + ", Prénom: " + etudiant.getUtilisateur().getPrenom()
                        + ", Classe: " + etudiant.getClasse()
                        + ", Filière: " + etudiant.getFiliere());
            });*/
        }
    }
}
