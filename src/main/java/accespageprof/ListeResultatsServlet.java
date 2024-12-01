package accespageprof;

import dao.EnseignantDAO;
import dao.NoteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Enseignant;
import modele.Note;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListeResultatsServlet", value = "/listeResultats")
public class ListeResultatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the login page
        request.getRequestDispatcher("/prof/connexion_prof.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email_enseignant");
        String motDePasse = request.getParameter("mot_de_passe");
        EnseignantDAO enseignantDAO = new EnseignantDAO();

        if (email == null || motDePasse == null || email.isEmpty() || motDePasse.isEmpty()) {
            request.setAttribute("error", "Email et mot de passe sont requis.");
            request.getRequestDispatcher("prof/connexion_prof.jsp").forward(request, response);
            return;
        }

        Enseignant enseignant = enseignantDAO.getProfesseurByEmail(email);

        if (enseignant != null && enseignant.getUtilisateur().getMotDePasse().equals(motDePasse)) {
            // Authentication successful
            request.getSession().setAttribute("user", "prof");
            request.getSession().setAttribute("id_professeur", enseignant.getIdEnseignant());

            NoteDAO noteDAO = new NoteDAO();
            List<Note> resultats = noteDAO.getNotesByEnseignant(enseignant.getIdEnseignant());

            request.setAttribute("resultats", resultats);
            request.setAttribute("email", email);
            request.getRequestDispatcher("prof/gestion_notes.jsp").forward(request, response);
        } else {
            // Authentication failed
            request.setAttribute("error", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("prof/connexion_prof.jsp").forward(request, response);
        }
    }
}
