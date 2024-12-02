package springboot.modelesringboot;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "cours")
public class Cours implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours", nullable = false)
    private int idCours;

    @Column(name = "nom_cours", nullable = false, length = 100)
    private String nomCours;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_enseignant")
    private Enseignant enseignant;

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Resultat> resultats;

    // Constructors, getters, setters, toString()

    public Cours() {}

    public Cours(String nomCours, String description, Enseignant enseignant) {
        this.nomCours = nomCours;
        this.description = description;
        this.enseignant = enseignant;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Set<Resultat> getResultats() {
        return resultats;
    }

    public void setResultats(Set<Resultat> resultats) {
        this.resultats = resultats;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "idCours=" + idCours +
                ", nomCours='" + nomCours + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
