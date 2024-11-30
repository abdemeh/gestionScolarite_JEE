package dao;


import modele.InscriptionAnnee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class InscriptionAnneeDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Erreur de configuration Hibernate : " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void saveInscription(InscriptionAnnee inscription) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(inscription);
            transaction.commit();
            System.out.println("Inscription ajoutée avec succès : " + inscription);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de l'ajout de l'inscription : " + e.getMessage());
        }
    }

    public InscriptionAnnee getInscriptionById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(InscriptionAnnee.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'inscription avec l'ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    public InscriptionAnnee getInscriptionByEtudiantId(int etudiantId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM InscriptionAnnee ia WHERE ia.etudiant.idEtudiant = :etudiantId",
                            InscriptionAnnee.class
                    )
                    .setParameter("etudiantId", etudiantId)
                    .uniqueResult(); // Récupérer un seul résultat
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'inscription pour l'étudiant avec l'ID " + etudiantId + ": " + e.getMessage());
            return null;
        }
    }


    public List<InscriptionAnnee> getAllInscriptions() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM InscriptionAnnee", InscriptionAnnee.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des inscriptions : " + e.getMessage());
            return null;
        }
    }

    public void deleteInscriptionById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            InscriptionAnnee inscription = session.get(InscriptionAnnee.class, id);
            if (inscription != null) {
                session.remove(inscription);
                transaction.commit();
                System.out.println("Inscription supprimée avec succès : " + id);
            } else {
                System.out.println("Inscription introuvable avec l'ID : " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de la suppression de l'inscription : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        InscriptionAnneeDAO inscriptionDAO = new InscriptionAnneeDAO();

        // Test d'ajout d'une inscription
        System.out.println("Liste des inscriptions :");
        inscriptionDAO.getAllInscriptions().forEach(System.out::println);
    }
}

