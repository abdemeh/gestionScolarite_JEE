package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import springboot.modelesringboot.Utilisateur;
import springboot.repository.UtilisateurRepository;
import springboot.service.UtilisateurService;

@SpringBootApplication
public class Application {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }

    @EventListener(ApplicationReadyEvent.class)
    public void testInjection() {
        System.out.println("UtilisateurRepository injecté : " + (utilisateurRepository != null));
    }
}
