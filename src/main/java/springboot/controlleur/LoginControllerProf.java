package springboot.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.modelesringboot.Utilisateur;
import springboot.service.EnseignantService;


@Controller
public class LoginControllerProf {

    @Autowired
    private EnseignantService enseignantService;
    @GetMapping("/loginprof")
    public String afficherFormulaireConnexionprof() {
        return "prof/connexion_prof"; // Nom de la vue JSP pour le formulaire de connexion
    }


    @PostMapping("/loginprof")
    public String authentifierEnseignant(
            @RequestParam("id_enseignant") String idEnseignantParam,
            @RequestParam("mot_de_passe") String motDePasse,
            Model model) {
        try {
            Integer idProf = Integer.parseInt(idEnseignantParam);

            Utilisateur utilisateur = enseignantService.getEnseignantById(idProf).getUtilisateur();

            if (utilisateur == null || !utilisateur.getMotDePasse().equals(motDePasse)) {
                model.addAttribute("error", "Identifiant ou mot de passe incorrect.");
                return "prof/connexion_prof"; // Retourne à la page de connexion en cas d'erreur
            }

            // Redirection vers la page attendue après succès
            return "redirect:/prof/" + idProf;

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Identifiant non valide.");
            return "prof/connexion_prof"; // Erreur d'identifiant
        } catch (Exception e) {
            model.addAttribute("error", "Erreur interne : " + e.getMessage());
            e.printStackTrace();
            return "prof/connexion_prof"; // Erreur générale
        }
    }

}
