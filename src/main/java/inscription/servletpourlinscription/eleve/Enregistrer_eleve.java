package inscription.servletpourlinscription.eleve;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static inscription.creation.CreationEleveDB.ajouterEleve;

@WebServlet(name = "Enregistrer_eleve", value = "/enregister_eleve")
public class Enregistrer_eleve extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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




        adresse=numeroAdresse+" "+adresse+ ", "+ codePostal+", "+commune;
        System.out.println(adresse);


        try {
            String message=ajouterEleve(nom, prenom, telephone, email,mot_de_passe,adresse,dateNaissance,classe,specialite);
            if(message=="Erreur : L'email est déjà utilisé."){
                request.setAttribute("errorMessage",message);
                request.getRequestDispatcher("inscription_eleve.jsp").forward(request, response);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("creer_eleve");
    }


}
