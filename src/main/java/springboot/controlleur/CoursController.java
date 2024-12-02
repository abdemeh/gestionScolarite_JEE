package springboot.controlleur;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springboot.modelesringboot.Cours;
import springboot.service.CoursService;

@Controller
public class CoursController {

    @Autowired
    private CoursService coursService;

    @GetMapping("/cours")
    public String afficherCours(Model model) {
        model.addAttribute("cours", coursService.getAllCours());
        return "liste_cours"; // fichier JSP
    }
}
