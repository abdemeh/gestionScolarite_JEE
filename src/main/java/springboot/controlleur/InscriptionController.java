package springboot.controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.service.CoursService;
import springboot.service.EtudiantService;
import springboot.service.InscriptionCoursService;
import springboot.modelesringboot.Cours;
import springboot.modelesringboot.Etudiant;
import springboot.modelesringboot.InscriptionCours;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InscriptionController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private CoursService coursService;

    @Autowired
    private InscriptionCoursService inscriptionCoursService;

    // Recherche d'étudiant
    @GetMapping("/rechercherEtudiant")
    public String rechercherEtudiant(@RequestParam("searchEtudiant") String searchEtudiant, Model model) {
        Etudiant etudiant = etudiantService.getEtudiantById(Integer.valueOf(searchEtudiant));
        List<Etudiant> etudiants=new ArrayList<>();

        if (etudiant != null) {
            model.addAttribute("etudiant",etudiants);
            }
        else {
            model.addAttribute("etudiant",etudiants.add(etudiantService.getEtudiantById(1)));
        }
        return "admin/inscription_eleve_cours_par_admin";
    }

    // Recherche de cours
    @GetMapping("/rechercherCours")
    public String rechercherCours(@RequestParam("searchCours") String searchCours, Model model) {
        List<Cours> cours = coursService.findCoursByNom(searchCours);
        if (!cours.isEmpty()) {
            model.addAttribute("cours", cours);
        } else {
            model.addAttribute("messageCours", "Aucun cours trouvé.");
        }
        return "admin/inscription_eleve_cours_par_admin";
    }

    // Inscription d'un étudiant à un cours
    @PostMapping("/inscrireCours")
    public String inscrireCours(
            @RequestParam("id_etudiant") Integer idEtudiant,
            @RequestParam("id_cours") Integer idCours,
            @RequestParam("dateCours") String dateCours,
            @RequestParam("debutHeure") String debutHeure,
            @RequestParam("finHeure") String finHeure,
            Model model) {

        Etudiant etudiant = etudiantService.getEtudiantById(idEtudiant);
        Cours cours = coursService.findCoursById(idCours);

        if (etudiant == null || cours == null) {
            model.addAttribute("messageInscription", "Erreur : Étudiant ou cours non trouvé.");
            return "admin/inscription_eleve_cours_par_admin";
        }

        // Créer une nouvelle inscription
        InscriptionCours inscriptionCours = new InscriptionCours();
        inscriptionCours.setEtudiant(etudiant);
        inscriptionCours.setCours(cours);
        inscriptionCours.setDebutCours(LocalDate.parse(dateCours).atTime(Integer.parseInt(debutHeure.split(":")[0]), 0));
        inscriptionCours.setFinCours(LocalDate.parse(dateCours).atTime(Integer.parseInt(finHeure.split(":")[0]), 0));

        inscriptionCoursService.saveInscription(inscriptionCours);
        model.addAttribute("messageSuccess", "Étudiant inscrit avec succès au cours.");

        // Ajouter la liste des inscriptions mises à jour
        List<InscriptionCours> inscriptions = inscriptionCoursService.getAllInscriptions();
        model.addAttribute("inscriptions", inscriptions);

        return "admin/inscription_eleve_cours_par_admin";
    }

    // Chargement de la page initiale
    @GetMapping("/inscription_eleve_cours")
    public String afficherPageInscription(Model model) {
        List<InscriptionCours> inscriptions = inscriptionCoursService.getAllInscriptions();
        model.addAttribute("inscriptions", inscriptions);
        return "admin/inscription_eleve_cours_par_admin";
    }
}
