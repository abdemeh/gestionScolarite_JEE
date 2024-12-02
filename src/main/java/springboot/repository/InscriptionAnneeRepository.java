package springboot.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.modelesringboot.InscriptionAnnee;

import java.util.List;

@Repository
public interface InscriptionAnneeRepository extends JpaRepository<InscriptionAnnee, Long> {

    // Trouver les inscriptions par étudiant
    List<InscriptionAnnee> findByEtudiantIdEtudiant(int idEtudiant);
}

