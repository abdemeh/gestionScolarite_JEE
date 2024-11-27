package inscription.servletpourlinscription.professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;


import static inscription.creation.CreationEnseignantDB.ajouterEnseignant;

@WebServlet(name = "Enregistrer_enseignant", value = "/enregister_enseignant")
public class Enregistrer_enseignant extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("mot_de_passe");
        String dateNaissance = request.getParameter("date_naissance");
        String numeroAdresse = request.getParameter("numero_adresse");
        String adresse = request.getParameter("adresse");
        String commune = request.getParameter("commune");
        String codePostal = request.getParameter("code_postal");
        String specialite = request.getParameter("specialite");
        adresse=numeroAdresse+" "+adresse+ ", "+ codePostal+", "+commune;

        try {
            ajouterEnseignant(nom, prenom, telephone, email, motDePasse, adresse, dateNaissance, specialite);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("creer_enseignant");


    }




}
