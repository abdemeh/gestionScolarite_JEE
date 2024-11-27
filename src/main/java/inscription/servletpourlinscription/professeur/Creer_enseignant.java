package inscription.servletpourlinscription.professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Creer_enseignant", value = "/creer_enseignant")
public class Creer_enseignant extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des paramètres du formulaire
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String mot_de_passe = request.getParameter("mot_de_passe");
        String dateNaissance = request.getParameter("date_naissance");
        String numeroAdresse = request.getParameter("numero_adresse");
        String adresse = request.getParameter("adresse");
        String commune = request.getParameter("commune");
        String codePostal = request.getParameter("code_postal");
        String specialite = request.getParameter("specialite");

        // Construire l'adresse complète
        String adresseComplete = numeroAdresse + " " + adresse + ", " + codePostal + ", " + commune;

        // Ajouter les informations comme attributs de la requête
        request.setAttribute("nom", nom);
        request.setAttribute("prenom", prenom);
        request.setAttribute("telephone", telephone);
        request.setAttribute("email", email);
        request.setAttribute("mot_de_passe",mot_de_passe);
        request.setAttribute("numero_adresse", numeroAdresse);
        request.setAttribute("adresse", adresse);
        request.setAttribute("code_postal", codePostal);
        request.setAttribute("commune",commune);
        request.setAttribute("date_naissance", dateNaissance);
        request.setAttribute("specialite", specialite);

        // Transférer la requête et la réponse au fichier JSP pour afficher les informations
        response.sendRedirect("afficherEnseignant.jsp");
    }
}
