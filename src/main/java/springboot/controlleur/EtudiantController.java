package springboot.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.modelesringboot.Etudiant;
import springboot.modelesringboot.InscriptionAnnee;
import springboot.modelesringboot.Role;
import springboot.modelesringboot.Utilisateur;
import springboot.service.EtudiantService;
import springboot.service.InscriptionAnneeService;
import springboot.service.UtilisateurService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private InscriptionAnneeService inscriptionAnneeService;

    @GetMapping("/listeEtudiants")
    public String afficherListeEtudiants(Model model) {
        model.addAttribute("etudiantList", etudiantService.getAllEtudiants());
        return "admin/liste_etudiant"; // Correspond à WEB-INF/vues/admin/liste_etudiant.jsp
    }

    @GetMapping("/creer_eleve")
    public String pagesInscriptionEleve(Model model) {
        return "inscription_eleve"; // Correspond à WEB-INF/vues/inscription_eleve.jsp
    }

    @PostMapping("/confirmer_eleve")
    public String confirmerInscription(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("telephone") String telephone,
            @RequestParam("email") String email,
            @RequestParam("mot_de_passe") String motDePasse,
            @RequestParam("numero_adresse") String numeroAdresse,
            @RequestParam("adresse") String adresse,
            @RequestParam("code_postal") String codePostal,
            @RequestParam("commune") String commune,
            @RequestParam("date_naissance") String dateNaissance,
            @RequestParam("classe") String classe,
            @RequestParam("specialite") String specialite,
            Model model
    ) {
        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        model.addAttribute("telephone", telephone);
        model.addAttribute("email", email);
        model.addAttribute("mot_de_passe", motDePasse);
        model.addAttribute("numero_adresse", numeroAdresse);
        model.addAttribute("adresse", adresse);
        model.addAttribute("code_postal", codePostal);
        model.addAttribute("commune", commune);
        model.addAttribute("date_naissance", dateNaissance);
        model.addAttribute("classe", classe);
        model.addAttribute("specialite", specialite);

        return "afficherEleve"; // Correspond à WEB-INF/vues/afficherEleve.jsp
    }

    @PostMapping("/enregistrer_eleve")
    public String enregistrerEleve(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("telephone") String telephone,
            @RequestParam("email") String email,
            @RequestParam("mot_de_passe") String motDePasse,
            @RequestParam("numero_adresse") String numeroAdresse,
            @RequestParam("adresse") String adresse,
            @RequestParam("code_postal") String codePostal,
            @RequestParam("commune") String commune,
            @RequestParam("date_naissance") String dateNaissance,
            @RequestParam("classe") String classe,
            @RequestParam("specialite") String specialite
    ) {
        // Convert date from String to LocalDate
        LocalDate dateNaissanceObj = LocalDate.parse(dateNaissance);

        // Create and persist Utilisateur first
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(motDePasse);
       utilisateur.setIdRole(3);

        String adresseComplete = String.format("%s %s, %s, %s",
                numeroAdresse != null ? numeroAdresse : "",
                adresse != null ? adresse : "",
                codePostal != null ? codePostal : "",
                commune != null ? commune : "");
        utilisateur.setAdresse(adresseComplete);

        utilisateurService.saveUtilisateur(utilisateur);





        // Create and persist Etudiant
        Etudiant etudiant = new Etudiant();
        etudiant.setUtilisateur(utilisateur);
        etudiant.setContact(telephone);
etudiant.setDateNaissance(dateNaissanceObj);
etudiant.setNiveau("licence1");
        etudiant.setClasse(classe);
        etudiant.setFiliere(specialite);
        etudiant.setIdEtudiant(utilisateur.getIdUtilisateur());
         // Link to persisted Utilisateur

        etudiantService.saveEtudiant(etudiant); // Save Etudiant

        return "redirect:/listeEtudiants"; // Redirect to list page
    }

    @GetMapping("/reinscription")
    public String pagesReinscriptionEleve(Model model) {
        return "reinscription"; // Correspond à WEB-INF/vues/inscription_eleve.jsp
    }
    @PostMapping("/mettreAJourEtudiant")
    public String mettreAJourEtudiant(
            @RequestParam("id_etudiant") int idEtudiant,
            @RequestParam("numero_adresse") String numeroAdresse,
            @RequestParam("adresse") String adresse,
            @RequestParam("code_postal") String codePostal,
            @RequestParam("commune") String commune,

            @RequestParam("contact") String contact,
            @RequestParam("classe") String classe,
            @RequestParam("filiere") String filiere,
            Model model
    ) {
        // Récupération de l'étudiant par son ID
       Etudiant etudiant= etudiantService.getEtudiantById(idEtudiant);

        if (etudiant!=null) {

            // Mise à jour des informations de l'utilisateur associé
            Utilisateur utilisateur = etudiant.getUtilisateur();
            utilisateur.setAdresse(String.format("%s %s, %s, %s",
                    numeroAdresse, adresse, codePostal, commune));


            utilisateurService.saveUtilisateur(utilisateur);

            // Mise à jour des informations de l'étudiant
            etudiant.setContact(contact);
            etudiant.setClasse(classe);
            etudiant.setFiliere(filiere);

            etudiantService.saveEtudiant(etudiant);

            InscriptionAnnee inscriptionAnnee=new InscriptionAnnee();
            inscriptionAnnee.setEtudiant(etudiant);
            inscriptionAnneeService.createInscription(inscriptionAnnee);

            model.addAttribute("message", "Réinscription réussie !");
        } else {
            model.addAttribute("message", "Étudiant introuvable !");
        }

        return "redirect:/listeEtudiants"; // Redirection vers la liste des étudiants
    }


}
