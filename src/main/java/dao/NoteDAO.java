package dao;



import hibernate.HibernateUtil;
import modele.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.List;

public class NoteDAO {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Erreur de configuration Hibernate : " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Ajouter une note
    public void saveNote(Note note) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(note);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Erreur lors de l'enregistrement de la note : " + e.getMessage());
            throw e; // Rejeter l'exception pour la remonter au servlet
        }
    }
    // Récupérer une note par ID
    public Note getNoteById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Note.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de la note avec l'ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    // Récupérer toutes les notes
    // DAO des notes avec JOIN FETCH
    public List<Note> getAllNotes() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT n FROM Note n " +
                            "JOIN FETCH n.etudiant e " +
                            "JOIN FETCH n.cours c",
                    Note.class
            ).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des notes : " + e.getMessage());
            return null;
        }
    }




    // Récupérer les notes pour un étudiant spécifique
    public List<Note> getNotesByEtudiant(int idEtudiant) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Note n WHERE n.etudiant.idEtudiant = :idEtudiant", Note.class)
                    .setParameter("idEtudiant", idEtudiant)
                    .list();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des notes pour l'étudiant avec l'ID " + idEtudiant + ": " + e.getMessage());
            return null;
        }
    }
    public List<Note> getNotesByEnseignant(int idEnseignant) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT n FROM Note n " +
                                    "JOIN n.cours c " +
                                    "JOIN c.enseignant e " +
                                    "WHERE e.idEnseignant = :idEnseignant",
                            Note.class
                    ).setParameter("idEnseignant", idEnseignant)
                    .list();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des notes pour l'enseignant avec l'ID " + idEnseignant + ": " + e.getMessage());
            return null;
        }
    }


    // Supprimer une note
    public void deleteNoteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Note note = session.get(Note.class, id);
            if (note != null) {
                session.delete(note);
                transaction.commit();
                System.out.println("Note supprimée avec succès.");
            } else {
                System.out.println("Note introuvable avec l'ID : " + id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de la suppression de la note : " + e.getMessage());
        }
    }
    public void updateNotes(List<Integer> idsResultats, List<BigDecimal> nouvellesNotes) {
        if (idsResultats == null || nouvellesNotes == null || idsResultats.size() != nouvellesNotes.size()) {
            throw new IllegalArgumentException("Les IDs des résultats et les notes doivent être non nuls et de même taille.");
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Parcourir les listes des IDs et notes
            for (int i = 0; i < idsResultats.size(); i++) {
                session.createQuery("UPDATE Note n SET n.note = :nouvelleNote WHERE n.idNote = :idResultat")
                        .setParameter("nouvelleNote", nouvellesNotes.get(i))
                        .setParameter("idResultat", idsResultats.get(i))
                        .executeUpdate();
            }

            transaction.commit();
            System.out.println("Notes mises à jour avec succès.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de la mise à jour des notes : " + e.getMessage());
        }
    }




    public static void main(String[] args) {
            NoteDAO noteDAO = new NoteDAO();

            System.out.println("Liste des notes :");
            noteDAO.getAllNotes().forEach(n -> System.out.println(
                    "Note ID: " + n.getIdNote() +
                            ", Note: " + n.getNote() +
                            ", Étudiant: " + n.getEtudiant().getUtilisateur().getNom() +
                            ", Cours: " + n.getCours().getNomCours()
            ));


        }
}

