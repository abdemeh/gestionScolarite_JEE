package springboot.controlleur;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.modelesringboot.InscriptionCours;
import springboot.modelesringboot.Resultat;
import springboot.service.InscriptionCoursService;
import springboot.service.ResultatService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    @Autowired
    private InscriptionCoursService inscriptionCoursService;


    @GetMapping("/etudiant/{id_etudiant}")
    public String afficherResultats(@PathVariable("id_etudiant") Integer idEtudiant, HttpSession session, Model model) {
        // Vérifier si l'ID de l'étudiant est valide
        if (idEtudiant == null) {
            model.addAttribute("error", "Vous devez être connecté pour accéder à cette page.");
            return "etudiant/login"; // Rediriger vers la page de connexion
        }

        // Enregistrer l'ID de l'étudiant dans la session
        session.setAttribute("id_etudiant", idEtudiant);

        // Récupérer les résultats de l'étudiant
        List<Resultat> resultatsEtudiant = resultatService.getAllResultats()
                .stream()
                .filter(resultat -> resultat.getEtudiant().getIdEtudiant() == idEtudiant)
                .collect(Collectors.toList());
        model.addAttribute("notes", resultatsEtudiant);

        // Récupérer les inscriptions aux cours de l'étudiant
        List<InscriptionCours> inscriptionsEtudiant = inscriptionCoursService.getAllInscriptions()
                .stream()
                .filter(inscription -> inscription.getEtudiant().getIdEtudiant() == idEtudiant)
                .collect(Collectors.toList());
        model.addAttribute("inscriptionCours", inscriptionsEtudiant);

        // Vérification si aucun résultat ou inscription n'a été trouvé
        if (resultatsEtudiant.isEmpty() && inscriptionsEtudiant.isEmpty()) {
            model.addAttribute("error", "Aucun résultat ou inscription trouvé pour cet étudiant !");
            return "etudiant/login"; // Rediriger vers la page de connexion
        }

        return "etudiant/notes"; // Fichier JSP pour afficher les résultats
    }

    @GetMapping("/prof/{id_enseignant}")
    public String afficherResultatseleve(
            @PathVariable("id_enseignant") Integer idProf,
            HttpSession session,
            Model model) {
        // Vérifier si l'ID de l'enseignant est valide
        if (idProf == null) {
            model.addAttribute("error", "Vous devez être connecté pour accéder à cette page.");
            return "prof/connexion_prof"; // Rediriger vers la page de connexion de l'enseignant
        }

        // Enregistrer l'ID de l'enseignant dans la session
        session.setAttribute("id_enseignant", idProf);

        // Récupérer les résultats des étudiants associés à cet enseignant
        List<Resultat> resultatsEtudiant = resultatService.getAllResultats()
                .stream()
                .filter(resultat -> resultat.getCours().getEnseignant().getIdEnseignant() == idProf)
                .collect(Collectors.toList());

        model.addAttribute("resultats", resultatsEtudiant);

        // Vérifier si aucun résultat n'a été trouvé
        if (resultatsEtudiant.isEmpty()) {
            model.addAttribute("error", "Aucun résultat trouvé pour les cours de cet enseignant.");
            return "prof/gestion_notes"; // Retourner la vue, même si elle est vide
        }

        return "prof/gestion_notes"; // Fichier JSP pour afficher les résultats
    }


    @PostMapping("/modifierNotes")
    public String modifierNotes(
            @RequestParam("id_resultat") List<Integer> idResultats,
            @RequestParam Map<String, String> allParams,
            Model model) {

        List<String> messages = new ArrayList<>();

        for (Integer id : idResultats) {
            String noteParam = "note_" + id; // Key for the note input
            String noteValue = allParams.get(noteParam);

            if (noteValue != null && !noteValue.isEmpty()) {
                try {
                    ;

                    // Fetch the Resultat and update its note
                    Resultat resultat = resultatService.getResultatById(id);
                    if (resultat != null) {
                        resultat.setNote(Double.parseDouble(noteValue));
                        resultatService.saveResultat(resultat);
                        messages.add("Note mise à jour pour l'étudiant avec ID: " + id);
                    } else {
                        messages.add("Aucun résultat trouvé pour l'ID: " + id);
                    }

                } catch (NumberFormatException ex) {
                    messages.add("Note invalide pour l'ID: " + id);
                }
            }
        }

        model.addAttribute("messages", messages);
        return "redirect:"; // Redirection après modification
    }
}


