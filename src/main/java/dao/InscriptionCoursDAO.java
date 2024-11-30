package dao;


import modele.InscriptionCours;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class InscriptionCoursDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Erreur de configuration Hibernate : " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Ajouter une inscription
    public void saveInscription(InscriptionCours inscription) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(inscription);
            transaction.commit();
            System.out.println("Inscription ajoutée avec succès.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de l'ajout de l'inscription : " + e.getMessage());
        }
    }

    // Récupérer une inscription par ID
    public InscriptionCours getInscriptionById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(InscriptionCours.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'inscription avec l'ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    // Récupérer toutes les inscriptions
    public List<InscriptionCours> getAllInscriptions() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM InscriptionCours", InscriptionCours.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des inscriptions : " + e.getMessage());
            return null;
        }
    }

    // Supprimer une inscription
    public void deleteInscriptionById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            InscriptionCours inscription = session.get(InscriptionCours.class, id);
            if (inscription != null) {
                session.delete(inscription);
                transaction.commit();
                System.out.println("Inscription supprimée avec succès.");
            } else {
                System.out.println("Inscription introuvable avec l'ID : " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de la suppression de l'inscription : " + e.getMessage());
        }
    }

    // Récupérer les inscriptions d'un étudiant
    public List<InscriptionCours> getInscriptionsByEtudiant(int idEtudiant) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT i FROM InscriptionCours i WHERE i.etudiant.idEtudiant = :idEtudiant",
                    InscriptionCours.class
            ).setParameter("idEtudiant", idEtudiant).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des inscriptions pour l'étudiant ID " + idEtudiant + ": " + e.getMessage());
            return null;
        }
    }

    // Récupérer les inscriptions pour un cours spécifique
    public List<InscriptionCours> getInscriptionsByCours(int idCours) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT i FROM InscriptionCours i WHERE i.cours.idCours = :idCours",
                    InscriptionCours.class
            ).setParameter("idCours", idCours).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des inscriptions pour le cours ID " + idCours + ": " + e.getMessage());
            return null;
        }
    }

    // Test
    public static void main(String[] args) {
        InscriptionCoursDAO dao = new InscriptionCoursDAO();

        System.out.println("Liste des inscriptions :");
        dao.getAllInscriptions().forEach(i -> System.out.println(
                "Inscription ID: " + i.getIdInscription() +
                        ", Étudiant: " + i.getEtudiant().getUtilisateur().getNom() +
                        ", Cours: " + i.getCours().getNomCours() +
                        ", Date d'inscription: " + i.getDateInscription()
        ));
    }
}

