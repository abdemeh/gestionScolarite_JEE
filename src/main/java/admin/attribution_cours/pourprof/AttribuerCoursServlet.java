package admin.attribution_cours.pourprof;

import admin.ExecuteSchema;
import dao.CoursDAO;
import dao.EnseignantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Cours;
import modele.Enseignant;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "AttribuerCoursServlet", value = "/attribuerCours")
public class AttribuerCoursServlet extends HttpServlet {

   /* private static final String DB_URL = ExecuteSchema.getDbUrl() + "/jee_project";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire
    */


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des paramètres
        String idProfesseurStr = request.getParameter("id_professeur");

        String nomCours = request.getParameter("nom_cours");
        String description = request.getParameter("description");


        // Validation des paramètres
        if (idProfesseurStr == null || idProfesseurStr.isEmpty()) {
            request.setAttribute("message", "Le professeur est requis.");
            request.getRequestDispatcher("listeCours").forward(request, response);
            return;
        }
        if (nomCours == null || nomCours.isEmpty()) {
            request.setAttribute("message", "Le nom du cours est requis.");
            request.getRequestDispatcher("listeCours").forward(request, response);
            return;
        }
        if (description == null || description.isEmpty()) {
            request.setAttribute("message", "La description est requise.");
            request.getRequestDispatcher("listeCours").forward(request, response);
            return;
        }

        // Conversion de l'ID professeur
        int idProfesseur;
        try {
            idProfesseur = Integer.parseInt(idProfesseurStr);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID professeur invalide.");
            request.getRequestDispatcher("listeCours").forward(request, response);
            return;
        }


        CoursDAO coursDAO = new CoursDAO();
        Cours cours = new Cours();
       Enseignant enseignant= coursDAO.getEnseignantByNomCoursAndProfesseur(nomCours, idProfesseur);


        if (enseignant==null){

            cours.setNomCours(nomCours);
            cours.setDescription(description);
            cours.setEnseignant(new EnseignantDAO().getProfesseurById(Integer.parseInt(idProfesseurStr)));
            coursDAO.saveCours(cours);

    }else {

            request.setAttribute("message", "enseigne deja.");
            request.getRequestDispatcher("listeCours").forward(request, response);

        }

        response.sendRedirect("listeCours");

        /*
        // Requête d'insertion dans la base de données
        String sqlInsert = "INSERT INTO cours (nom_cours, description, id_enseignant) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {

            stmt.setString(1, nomCours);
            stmt.setString(2, description);
            stmt.setInt(3, idProfesseur);
            stmt.executeUpdate();

            // Ajouter un message de confirmation
            request.setAttribute("message", "Cours attribué avec succès !");
        } catch (SQLException e) {
            request.setAttribute("message", "Erreur lors de l'attribution du cours : " + e.getMessage());
        }

        // Redirection après enregistrement
        response.sendRedirect("listeCours");*/
    }
}
