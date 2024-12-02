package springboot.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.modelesringboot.InscriptionAnnee;
import springboot.repository.InscriptionAnneeRepository;

import java.util.List;

@Service
@Transactional
public class InscriptionAnneeService {

    private final InscriptionAnneeRepository inscriptionAnneeRepository;

    public InscriptionAnneeService(InscriptionAnneeRepository inscriptionAnneeRepository) {
        this.inscriptionAnneeRepository = inscriptionAnneeRepository;
    }

    // Créer une nouvelle inscription
    public InscriptionAnnee createInscription(InscriptionAnnee inscription) {
        return inscriptionAnneeRepository.save(inscription);
    }

    // Trouver toutes les inscriptions
    public List<InscriptionAnnee> getAllInscriptions() {
        return inscriptionAnneeRepository.findAll();
    }

    // Trouver les inscriptions par étudiant
    public List<InscriptionAnnee> getInscriptionsByEtudiant(int idEtudiant) {
        return inscriptionAnneeRepository.findByEtudiantIdEtudiant(idEtudiant);
    }

    // Supprimer une inscription
    public void deleteInscription(Long id) {
        inscriptionAnneeRepository.deleteById(id);
    }
}
