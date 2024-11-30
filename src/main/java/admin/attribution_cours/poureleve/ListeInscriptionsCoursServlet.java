package admin.attribution_cours.poureleve;

import dao.InscriptionCoursDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.InscriptionCours;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListeInscriptionsServlet", value = "/listeInscriptions")
public class ListeInscriptionsCoursServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all inscriptions
        List<InscriptionCours> inscriptions = new InscriptionCoursDAO().getAllInscriptions();

        // Set attribute for the JSP page
        request.setAttribute("inscriptions", inscriptions);
        request.getRequestDispatcher("admin/inscription_eleve_cours_par_admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST requests, for example filtering inscriptions by student or course
        String filter = request.getParameter("filter");
        String value = request.getParameter("value");

        List<InscriptionCours> filteredInscriptions;

        // Logic for filtering inscriptions based on parameters
        if ("etudiant".equalsIgnoreCase(filter)) {
            filteredInscriptions = new InscriptionCoursDAO().getInscriptionsByEtudiant(Integer.parseInt(value));
        } else if ("cours".equalsIgnoreCase(filter)) {
            filteredInscriptions = new InscriptionCoursDAO().getInscriptionsByCours(Integer.parseInt(value));
        } else {
            // If no filter is provided, fetch all inscriptions
            filteredInscriptions = new InscriptionCoursDAO().getAllInscriptions();
        }

        // Set filtered inscriptions as a request attribute
        request.setAttribute("inscriptions", filteredInscriptions);
        request.setAttribute("filter", filter);
        request.setAttribute("value", value);

        // Forward to the JSP page to display results
        request.getRequestDispatcher("admin/inscription_eleve_cours_par_admin.jsp").forward(request, response);
    }
}
