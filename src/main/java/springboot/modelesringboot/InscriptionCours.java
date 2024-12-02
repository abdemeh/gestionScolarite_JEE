package springboot.modelesringboot;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscriptions_cours")
public class InscriptionCours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscription")
    private Integer idInscription;

    @ManyToOne
    @JoinColumn(name = "id_etudiant", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "id_cours", nullable = false)
    private Cours cours;

    @Column(name = "debut_cours")
    private LocalDateTime debutCours;

    @Column(name = "fin_cours")
    private LocalDateTime finCours;

    public InscriptionCours() {}

    public InscriptionCours(Etudiant etudiant, Cours cours, LocalDateTime debutCours, LocalDateTime finCours) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.debutCours = debutCours;
        this.finCours = finCours;
    }

    public Integer getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(Integer idInscription) {
        this.idInscription = idInscription;
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

    public LocalDateTime getDebutCours() {
        return debutCours;
    }

    public void setDebutCours(LocalDateTime debutCours) {
        this.debutCours = debutCours;
    }

    public LocalDateTime getFinCours() {
        return finCours;
    }

    public void setFinCours(LocalDateTime finCours) {
        this.finCours = finCours;
    }
}
