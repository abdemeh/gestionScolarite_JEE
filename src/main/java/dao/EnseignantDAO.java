package dao;


import hibernate.HibernateUtil;
import modele.Enseignant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class EnseignantDAO {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Erreur de configuration Hibernate : " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void saveProfesseur(Enseignant professeur) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(professeur);
            tx.commit();
            System.out.println("Professeur ajouté avec succès : " + professeur.getUtilisateur().getNom());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur lors de l'ajout du professeur : " + e.getMessage());
        }
    }

    public Enseignant getProfesseurById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Enseignant.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du professeur avec l'ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    public List<Enseignant> getAllProfesseurs() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Enseignant ", Enseignant.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la liste des professeurs : " + e.getMessage());
            return null;
        }
    }


    public List<Enseignant> rechercherProfesseurs(String search) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL pour la recherche avec des critères
            String hql = "FROM Enseignant e " +
                    "WHERE e.utilisateur.nom LIKE :search " +
                    "OR e.utilisateur.prenom LIKE :search " +
                    "OR CAST(e.idEnseignant AS string) LIKE :search";

            Query<Enseignant> query = session.createQuery(hql, Enseignant.class);
            query.setParameter("search", "%" + search + "%");

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteProfesseur(int id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Enseignant  professeur = session.get(Enseignant .class, id);
            if (professeur != null) {
                session.remove(professeur);
                System.out.println("Professeur supprimé avec succès : " + id);
            } else {
                System.out.println("Aucun professeur trouvé avec l'ID : " + id);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur lors de la suppression du professeur : " + e.getMessage());
        }
    }

    // Programme de test
    public static void main(String[] args) {
        EnseignantDAO enseignantDAO = new EnseignantDAO();

        // Afficher tous les professeurs
        List<Enseignant> professeurs = enseignantDAO.rechercherProfesseurs("john");
        if (professeurs != null) {
            for (Enseignant p : professeurs) {
                System.out.println("Professeur : " + p.getUtilisateur().getNom() +
                        ", Spécialité : " + p.getSpecialite() +
                        ", Contact : " + p.getContact());
            }
        }
    }
}
