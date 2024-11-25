package projetprototypejee.servlet.eleve;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import projetprototypejee.ExecuteSchema;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Creer_eleve extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Configuration pour la base de données

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        // Récupération des paramètres du formulaire
        ExecuteSchema.creationBase(); // Crée la base si nécessaire


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





