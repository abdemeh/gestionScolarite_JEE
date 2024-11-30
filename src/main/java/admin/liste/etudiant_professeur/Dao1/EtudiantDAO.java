package admin.liste.etudiant_professeur.Dao1;



import admin.liste.etudiant_professeur.modele.Etudiant;
import jakarta.persistence.*;

import java.util.List;

public class EtudiantDAO {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jee_project");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    // Récupérer tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        String jpql = "SELECT e FROM Etudiant e";
        TypedQuery<Etudiant> query = entityManager.createQuery(jpql, Etudiant.class);
        return query.getResultList();
    }

    // Supprimer un étudiant
    public void supprimerEtudiant(int idEtudiant) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Recherche de l'étudiant à supprimer
            Etudiant etudiant = entityManager.find(Etudiant.class, idEtudiant);
            if (etudiant != null) {
                entityManager.remove(etudiant); // Supprimer l'étudiant
            }

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

