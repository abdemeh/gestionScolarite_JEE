package springboot.controlleur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springboot.modelesringboot.InscriptionCours;
import springboot.service.InscriptionCoursService;

@Controller
public class InscriptionCoursController {

    @Autowired
    private InscriptionCoursService inscriptionCoursService;

    @GetMapping("/inscriptions")
    public String afficherInscriptions(Model model) {
        model.addAttribute("inscriptions", inscriptionCoursService.getAllInscriptions());
        return "liste_inscriptions"; // fichier JSP
    }
}

