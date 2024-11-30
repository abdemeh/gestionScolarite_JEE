package inscription.servletpourlinscription.professeur;

import dao.EnseignantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Enseignant;
import modele.Utilisateur;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "Enregistrer_enseignant", value = "/enregister_enseignant")
public class Enregistrer_enseignant extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur utilisateur=new Utilisateur();
        Enseignant enseignant =new Enseignant();
        EnseignantDAO enseignantDAO=new EnseignantDAO();

        utilisateur.setNom(request.getParameter("nom"));
        utilisateur.setPrenom( request.getParameter("prenom"));
        enseignant.setContact( request.getParameter("telephone"));
        utilisateur.setEmail( request.getParameter("email"));
        utilisateur.setMotDePasse( request.getParameter("mot_de_passe"));

        try {
            enseignant.setDateNaissance(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date_naissance")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String numeroAdresse = request.getParameter("numero_adresse");
        String adresse = request.getParameter("adresse");
        String commune = request.getParameter("commune");
        String codePostal = request.getParameter("code_postal");
        enseignant.setSpecialite(request.getParameter("specialite"));
        adresse=numeroAdresse+" "+adresse+ ", "+ codePostal+", "+commune;
        utilisateur.setAdresse(adresse);
        enseignant.setUtilisateur(utilisateur);
        enseignantDAO.saveProfesseur(enseignant);

        /*
        try {
            ajouterEnseignant(nom, prenom, telephone, email, motDePasse, adresse, dateNaissance, specialite);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/

        response.sendRedirect("creer_enseignant");


    }




}
