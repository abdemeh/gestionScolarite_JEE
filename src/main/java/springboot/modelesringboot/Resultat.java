package springboot.modelesringboot;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "resultats")
public class Resultat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultat", nullable = false)
    private int idResultat;

    @ManyToOne
    @JoinColumn(name = "id_etudiant", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "id_cours", nullable = false)
    private Cours cours;

    @Column(precision = 10) // Supprimez scale pour les types à virgule flottante
    private Double note;


    // Constructors, getters, setters, toString()

    public Resultat() {}

    public Resultat(Etudiant etudiant, Cours cours, double note) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.note = note;
    }

    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Resultat{" +
                "idResultat=" + idResultat +
                ", note=" + note +
                '}';
    }
}
