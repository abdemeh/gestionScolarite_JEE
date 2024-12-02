package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.modelesringboot.Etudiant;

import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    @Query("SELECT e FROM Etudiant e WHERE e.utilisateur.nom LIKE %:nom% OR e.idEtudiant = :id")
    List<Etudiant> findByNomOrId(@Param("nom") String nom, @Param("id") Integer id);
}
