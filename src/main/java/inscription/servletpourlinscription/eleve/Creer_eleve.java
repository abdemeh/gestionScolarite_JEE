package inscription.servletpourlinscription.eleve;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import inscription.ExecuteSchema;

import java.io.IOException;

@WebServlet(name = "Creer_eleve", urlPatterns = "/creer_eleve")
public class Creer_eleve extends HttpServlet {


    // Configuration pour la base de données
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Charge la vue (formulaire d'inscription) sans afficher l'URL JSP
        request.getRequestDispatcher("/inscription_eleve.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        //ExecuteSchema.creationBase(); // Crée la base si nécessaire
        // Récupération des paramètres du formulaire



        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String mot_de_passe=request.getParameter("mot_de_passe");
        String numeroAdresse = request.getParameter("numero_adresse");
        String adresse = request.getParameter("adresse");
        String codePostal = request.getParameter("code_postal");
        String commune = request.getParameter("commune");
        String dateNaissance = request.getParameter("date_naissance");
        String classe = request.getParameter("classe");
        String specialite = request.getParameter("specialite");

        // Ajouter les informations comme attributs de la requête (pour affichage)
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
        request.setAttribute("classe", classe);
        request.setAttribute("specialite", specialite);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.getRequestDispatcher("afficherEleve.jsp").forward(request, response);
        }
}





