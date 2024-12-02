package dao;


import modele.Cours;
import modele.Enseignant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CoursDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.invalide").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Erreur de configuration Hibernate : " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Cours getCoursById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cours.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du cours avec l'ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    public Enseignant getEnseignantByNomCoursAndProfesseur(String nomCours, int idProfesseur) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT c.enseignant FROM Cours c " +
                                    "WHERE c.nomCours = :nomCours AND c.enseignant.idEnseignant = :idProfesseur",
                            Enseignant.class
                    )
                    .setParameter("nomCours", nomCours)
                    .setParameter("idProfesseur", idProfesseur)
                    .uniqueResult(); // Retourne l'enseignant ou null si aucun cours ne correspond
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'enseignant : " + e.getMessage());
            return null;
        }
    }




    public List<Cours> getAllCours() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Cours", Cours.class).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la liste des cours : " + e.getMessage());
            return null;
        }
    }

    public void saveCours(Cours cours) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(cours);
            session.getTransaction().commit();
            System.out.println("Cours ajouté avec succès : " + cours.getNomCours());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du cours : " + e.getMessage());
        }
    }

    public void deleteCours(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Cours cours = session.get(Cours.class, id);
            if (cours != null) {
                session.remove(cours);
                System.out.println("Cours supprimé avec succès : " + cours.getNomCours());
            } else {
                System.err.println("Cours avec ID " + id + " non trouvé.");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du cours : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CoursDAO coursDAO = new CoursDAO();

        // Récupérer tous les cours
        List<Cours> coursList = coursDAO.getAllCours();
        if (coursList != null) {
            for (Cours c : coursList) {
                System.out.println("Cours : " + c.getNomCours() + ", Description : " + c.getDescription());
            }
        } else {
            System.out.println("Aucun cours trouvé.");
        }

    }
}

