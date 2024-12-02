package springboot.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.service.EtudiantService;
import springboot.modelesringboot.Utilisateur;

@Controller
public class LoginControllerEleve {

    @Autowired
    private EtudiantService etudiantService;


    @GetMapping("/logineleve")
    public String afficherFormulaireConnexioneleve() {
        return "etudiant/login"; // Nom de la vue JSP pour le formulaire de connexion
    }






    @PostMapping("/logineleve")
    public String authentifierEtudiant(
            @RequestParam("id_etudiant") String idEtudiantParam,
            @RequestParam("mot_de_passe") String motDePasse,
            Model model) {
        try {
            // Vérifier que l'ID étudiant est un entier
            Integer idEtudiant = Integer.parseInt(idEtudiantParam);

            // Récupérer l'utilisateur associé à l'étudiant
           Utilisateur utilisateur = etudiantService.getEtudiantById(idEtudiant).getUtilisateur();

            if (utilisateur == null || !utilisateur.getMotDePasse().equals(motDePasse)) {
                model.addAttribute("error", "Identifiant ou mot de passe incorrect.");
                return "etudiant/login"; // Retourner à la page de connexion avec un message d'erreur
            }

            // Redirection vers les résultats
            return "redirect:/etudiant/" + idEtudiant;

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Identifiant non valide.");
            return "etudiant/login"; // Retourner à la page de connexion avec un message d'erreur
        } catch (Exception e) {
            model.addAttribute("error", "Erreur interne : " + e.getMessage());
            e.printStackTrace(); // Loguer l'erreur pour le diagnostic
            return "etudiant/login";
        }
    }
}
