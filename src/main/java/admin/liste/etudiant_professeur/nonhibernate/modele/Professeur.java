package admin.liste.etudiant_professeur.nonhibernate.modele;

public class Professeur {
    public int idProfesseur;
    public String nom;
    public String prenom;
    public String adresse;
    public String email;
    public String contact;
    public String specialite;

    public Professeur(int idProfesseur, String nom, String prenom, String adresse, String email, String contact, String specialite) {
        this.idProfesseur = idProfesseur;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.contact = contact;
        this.specialite = specialite;
    }

    public int getIdProfesseur() {
        return idProfesseur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getSpecialite() {
        return specialite;
    }
}