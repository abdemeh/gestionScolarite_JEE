package modele;

public class Professeur {
    public int idProfesseur;
    public String nom;
    public String prenom;
    public String adresse;
    public String email;
    public String contact;
    public String specialite;

    // Constructeur
    public Professeur(int idProfesseur, String nom, String prenom, String adresse, String email, String contact, String specialite) {
        this.idProfesseur = idProfesseur;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.contact = contact;
        this.specialite = specialite;
    }
}