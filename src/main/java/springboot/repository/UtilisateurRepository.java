package springboot.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import springboot.modelesringboot.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire, par exemple pour rechercher par email
    Utilisateur findByEmail(String email);
}

