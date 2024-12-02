package springboot.controlleur;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String defaultPage() {
        // Redirige vers votre page JSP (chemin relatif sans extension JSP)
        return "index"; // Correspond à /WEB-INF/views/index.jsp
    }
}
