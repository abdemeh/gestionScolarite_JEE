package springboot.controlleur;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginControllerAdmin {

    @GetMapping("/loginadmin")
    public String adminconnexion() {
        return "admin/pagedeconnexionAdmin"; // Nom de la vue JSP pour le formulaire de connexion
    }

    @PostMapping("/connexionAdmin")
    public String authentifierAdmin(
            @RequestParam("id_admin") String idParam,
            @RequestParam("mot_de_passe") String motDePasse,
            Model model) {

        if ("admin".equals(idParam) && "password123".equals(motDePasse)) {
            return "admin/pageAdmin"; // Page après connexion réussie
        } else {

            return "loginadmin"; // Page de connexion avec le message d'erreur
        }
    }

}
