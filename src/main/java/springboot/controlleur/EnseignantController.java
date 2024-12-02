package springboot.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.modelesringboot.Enseignant;
import springboot.modelesringboot.Role;
import springboot.modelesringboot.Utilisateur;
import springboot.service.EnseignantService;
import springboot.service.UtilisateurService;

import java.time.LocalDate;

@Controller
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("listeProfesseurs")
    public String afficherListeEnseignants(Model model) {
        model.addAttribute("professeurs", enseignantService.getAllEnseignants());
        return "admin/liste_professeur"; // Correspond à WEB-INF/vues/admin/liste_enseignant.jsp
    }

    @GetMapping("/creer_enseignant")
    public String pagesInscriptionEnseignant(Model model) {
        return "inscription_enseignant"; // Correspond à WEB-INF/vues/inscription_enseignant.jsp
    }

    @PostMapping("/confirmer_enseignant")
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
        model.addAttribute("specialite", specialite);

        return "afficherEnseignant"; // Correspond à WEB-INF/vues/afficherEnseignant.jsp
    }

    @PostMapping("/enregistrer_enseignant")
    public String enregistrerEnseignant(
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
        utilisateur.setIdRole(2); // Role pour enseignant

        String adresseComplete = String.format("%s %s, %s, %s",
                numeroAdresse != null ? numeroAdresse : "",
                adresse != null ? adresse : "",
                codePostal != null ? codePostal : "",
                commune != null ? commune : "");
        utilisateur.setAdresse(adresseComplete);

        utilisateurService.saveUtilisateur(utilisateur);

        // Create and persist Enseignant
        Enseignant enseignant = new Enseignant();
        enseignant.setUtilisateur(utilisateur);
        enseignant.setContact(telephone);
        enseignant.setDateNaissance(dateNaissanceObj);
        enseignant.setSpecialite(specialite);

        enseignantService.saveEnseignant(enseignant); // Save Enseignant

        return "redirect:/listeEnseignants"; // Redirect to list page
    }
}
