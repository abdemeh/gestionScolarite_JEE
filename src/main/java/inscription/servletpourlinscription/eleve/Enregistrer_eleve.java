package inscription.servletpourlinscription.eleve;

import dao.EtudiantDAO;
import dao.UtilisateurDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Classe;
import modele.Etudiant;
import modele.Filiere;
import modele.Utilisateur;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "Enregistrer_eleve", value = "/enregister_eleve")
public class Enregistrer_eleve extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EtudiantDAO etudiantDAO=new EtudiantDAO();
        UtilisateurDAO utilisateurDAO=new UtilisateurDAO();

        Etudiant etudiant=new Etudiant();
        Utilisateur utilisateur=new Utilisateur();


        utilisateur.setNom( request.getParameter("nom"));
        utilisateur.setPrenom( request.getParameter("prenom"));
        etudiant.setContact(request.getParameter("telephone"));
        utilisateur.setEmail( request.getParameter("email"));
        utilisateur.setMotDePasse(  request.getParameter("mot_de_passe"));
        String numeroAdresse = request.getParameter("numero_adresse");
        String adresse = request.getParameter("adresse");
        String codePostal = request.getParameter("code_postal");
        String commune = request.getParameter("commune");

        try {
            etudiant.setDateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date_naissance")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        etudiant.setClasse(Classe.valueOf(request.getParameter("classe")));
        etudiant.setFiliere(Filiere.valueOf(request.getParameter("specialite")));



        // Construction de l'adresse complète
        String adresseComplete = (numeroAdresse != null ? numeroAdresse + " " : "") +
                (adresse != null ? adresse + ", " : "") +
                (codePostal != null ? codePostal + ", " : "") +
                (commune != null ? commune : "");
        utilisateur.setAdresse(adresseComplete);
        etudiant.setUtilisateur(utilisateur);

        etudiantDAO.saveEtudiant(etudiant);

        response.sendRedirect("creer_eleve");


/*
        try {
            String message=ajouterEleve(nom, prenom, telephone, email,mot_de_passe,adresse,dateNaissance,classe,specialite);
            if(message=="Erreur : L'email est déjà utilisé."){
                request.setAttribute("errorMessage",message);
                request.getRequestDispatcher("inscription_eleve.jsp").forward(request, response);

            }else{
                request.setAttribute("errorMessage","Votre compte à été crée!");
                request.getRequestDispatcher("inscription_eleve.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/

        //response.sendRedirect("creer_eleve");
    }


}
