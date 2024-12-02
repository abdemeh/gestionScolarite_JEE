package springboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springboot.repository.EtudiantRepository;

@Component
public class DatabaseTester implements CommandLineRunner {

    private final EtudiantRepository etudiantRepository;

    public DatabaseTester(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Test simple pour vérifier l'accès à la base
            System.out.println("Test de connexion à la base de données...");

            long count = etudiantRepository.count();
            System.out.println("La connexion à la base de données est réussie !");
            System.out.println("Nombre d'étudiants dans la base : " + count);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'accès à la base de données !");
            e.printStackTrace();
        }
    }
}
