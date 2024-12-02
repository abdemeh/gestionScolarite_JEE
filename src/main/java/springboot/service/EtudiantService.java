package springboot.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springboot.modelesringboot.Etudiant;
import springboot.repository.EtudiantRepository;

import java.util.List;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Etudiant getEtudiantById(Integer id) {
        return etudiantRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public void deleteEtudiant(Integer id) {
        etudiantRepository.deleteById(Long.valueOf(id));
    }

    @Component
    public class DataLoader implements CommandLineRunner {

        private final EtudiantService etudiantService;

        public DataLoader(EtudiantService etudiantService) {
            this.etudiantService = etudiantService;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("Récupération des étudiants depuis MySQL :");
            etudiantService.getAllEtudiants().forEach(etudiant -> {
                System.out.println("ID: " + etudiant.getIdEtudiant()
                        );
            });
        }
    }

}
