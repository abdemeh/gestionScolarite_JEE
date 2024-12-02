package springboot.modelesringboot;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false)
    private Integer idRole;

    @Column(name = "nom_role", nullable = false, unique = true, length = 50)
    private String nomRole;

    // Constructeurs
    public Role() {}

    public Role(String nomRole) {
        this.nomRole = nomRole;
    }

    // Getters et Setters
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getNomRole() {
        return nomRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", nomRole='" + nomRole + '\'' +
                '}';
    }
}
