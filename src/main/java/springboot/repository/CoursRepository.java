package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.modelesringboot.Cours;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Integer> {
    Cours findByIdCours(int idCours);
    @Query("SELECT c FROM Cours c WHERE c.nomCours LIKE %:nom%")
    List<Cours> findByNom(@Param("nom") String nom);
}
