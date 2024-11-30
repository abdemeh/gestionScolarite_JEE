package admin.liste.etudiant_professeur.controlleur.etudiants;


import dao.EtudiantDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;



@WebServlet(name = "SupprimerEtudiantServlet", urlPatterns = "/supprimerEtudiant")
public class SupprimerEtudiantServlet extends HttpServlet {


    private EtudiantDAO etudiantDAO;

    @Override
    public void init() throws ServletException {
        // Initialiser le DAO
        etudiantDAO = new EtudiantDAO();
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idEtudiantParam = request.getParameter("id");
        if (idEtudiantParam != null && !idEtudiantParam.trim().isEmpty()) {
            try {
                int idEtudiant = Integer.parseInt(idEtudiantParam);
               etudiantDAO.deleteEtudiantById(Integer.parseInt(idEtudiantParam));

                // Redirection après suppression
                response.sendRedirect("listeEtudiants");
            } catch (Exception e) {
                throw new ServletException("Erreur lors de la suppression de l'étudiant", e);
            }
        } else {
            response.sendRedirect("listeEtudiants");
        }
    }
}

