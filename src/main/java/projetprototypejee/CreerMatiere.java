package projetprototypejee;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CreerCours", value = "/creer_cours")
public class CreerMatiere extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire
        String nom = request.getParameter("nom");
        String[] types = request.getParameterValues("type");
        String grade = request.getParameter("grade");
        String filiere = request.getParameter("filiere");

        // Ajouter les informations comme attributs de la requête
        request.setAttribute("nom", nom);
        request.setAttribute("types", types);
        request.setAttribute("grade", grade);
        request.setAttribute("filiere", filiere);

        // Rediriger vers la page de confirmation
        request.getRequestDispatcher("confirmationCreationMatiere.jsp").forward(request, response);
    }
}

