package dao;

import modele.Classe;
import modele.Etudiant;
import modele.Filiere;
import modele.Utilisateur;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EtudiantDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Erreur de configuration Hibernate : " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Récupérer un étudiant par ID
    public Etudiant getEtudiantById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Etudiant.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'étudiant avec l'ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    // Récupérer tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        try (Session session = sessionFactory.openSession()) {
            // Utilisation de HQL pour récupérer la liste des étudiants
            return session.createQuery("FROM Etudiant", Etudiant.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la liste des étudiants : " + e.getMessage());
            return null;
        }
    }

    // Ajouter un étudiant
    public void saveEtudiant(Etudiant etudiant) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(etudiant);
            session.getTransaction().commit();
            System.out.println("Étudiant ajouté avec succès : " + etudiant);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'étudiant : " + e.getMessage());
        }
    }
    // Supprimer un étudiant par ID
    public void deleteEtudiantById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Etudiant etudiant = session.get(Etudiant.class, id);
            if (etudiant != null) {
                session.remove(etudiant);
                session.getTransaction().commit();
                System.out.println("Étudiant supprimé avec succès : " + id);
            } else {
                System.out.println("Étudiant avec l'ID " + id + " n'existe pas.");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression de l'étudiant avec l'ID " + id + ": " + e.getMessage());
        }
    }
    // Programme de test
    public static void main(String[] args) {
        EtudiantDAO etudiantDAO = new EtudiantDAO();



        // Récupérer tous les étudiants
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();
        if (etudiants != null) {
            for (Etudiant e : etudiants) {
                System.out.println("Étudiant : " + e.getUtilisateur().getNom() +
                        ", Classe : " + e.getClasse() +
                        ", Filière : " + e.getFiliere());
            }
        } else {
            System.out.println("Aucun étudiant trouvé.");
        }
    }
}
