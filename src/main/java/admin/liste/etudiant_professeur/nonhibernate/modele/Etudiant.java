package admin.liste.etudiant_professeur.nonhibernate.modele;


// Classe Etudiant pour représenter les données
public class Etudiant {
    private int idEtudiant;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String contact;
    private String classe;
    private String filiere;

    public Etudiant(int idEtudiant, String nom, String prenom, String adresse, String email, String contact, String classe, String filiere) {
        this.idEtudiant = idEtudiant;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.contact = contact;
        this.classe = classe;
        this.filiere = filiere;
    }

    public int getIdEtudiant() { return idEtudiant; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getAdresse() { return adresse; }
    public String getEmail() { return email; }
    public String getContact() { return contact; }
    public String getClasse() { return classe; }
    public String getFiliere() { return filiere; }
}
