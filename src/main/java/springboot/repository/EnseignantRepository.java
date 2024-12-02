package springboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import springboot.modelesringboot.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Integer> {
}

