package dao;

import modele.Utilisateur;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UtilisateurDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Initialisation de la SessionFactory avec configuration Hibernate
            sessionFactory = new Configuration().configure("hibernate.cfg.invalide").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Échec de la création de SessionFactory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Méthode pour enregistrer un utilisateur
    public void saveUtilisateur(Utilisateur utilisateur) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(utilisateur); // Persist l'utilisateur dans la base
            transaction.commit();
            System.out.println("Utilisateur ajouté avec succès : " + utilisateur.getNom());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Annulation en cas d'erreur
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    // Méthode pour récupérer un utilisateur par ID
    public Utilisateur getUtilisateurById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Utilisateur.class, id); // Récupère l'utilisateur à partir de son ID
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur avec ID " + id + " : " + e.getMessage());
            return null;
        }
    }

    // Méthode pour vérifier si un utilisateur existe avec un email donné
    public Utilisateur getUtilisateurByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .uniqueResult(); // Retourne un seul résultat ou null si non trouvé
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification de l'email : " + e.getMessage());
            return null;
        }
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUtilisateurById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Utilisateur utilisateur = session.get(Utilisateur.class, id);
            if (utilisateur != null) {
                session.delete(utilisateur);
                transaction.commit();
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour un utilisateur
    public void updateUtilisateur(Utilisateur utilisateur) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(utilisateur); // Met à jour l'utilisateur existant
            transaction.commit();
            System.out.println("Utilisateur mis à jour avec succès : " + utilisateur.getNom());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }
}
