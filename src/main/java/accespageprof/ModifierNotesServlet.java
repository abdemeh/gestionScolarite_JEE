package accespageprof;

import accespageprof.secours.Professeur_id;
import dao.NoteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.Note;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@WebServlet(name = "ModifierNotesServlet", value = "/modifierNotes")
public class ModifierNotesServlet extends HttpServlet {

    /*private static final String DB_URL = ExecuteSchema.getDbUrl()+ "/jee_projet";
    private static final String DB_USER = ExecuteSchema.getDbUser(); // Modifier si nécessaire
    private static final String DB_PASSWORD = ExecuteSchema.getDbPassword(); // Modifier si nécessaire*/


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] idResultats = request.getParameterValues("id_resultat");
        List<Integer> idResultatsList = new ArrayList<>();
        List<BigDecimal> nouvellesNotesList = new ArrayList<>();

        boolean hasInvalidNotes = false;

        // Validate notes
        for (String id : idResultats) {
            String noteParam = "note_" + id;
            String noteValue = request.getParameter(noteParam);

            if (noteValue != null && !noteValue.isEmpty()) {
                BigDecimal note = new BigDecimal(noteValue);

                // Check if the note is out of range
                if (note.compareTo(BigDecimal.ZERO) < 0 || note.compareTo(BigDecimal.valueOf(20)) > 0) {
                    hasInvalidNotes = true;
                    break; // Stop processing further notes
                } else {
                    idResultatsList.add(Integer.parseInt(id));
                    nouvellesNotesList.add(note);
                }
            }
        }

        // If invalid notes are found, return with an error message
        if (hasInvalidNotes) {
            request.setAttribute("errorMessage", "Toutes les notes doivent être comprises entre 0 et 20.");

            // Fetch current notes to repopulate the table
            HttpSession session = request.getSession();
            Integer idProfesseur = (Integer) session.getAttribute("id_professeur");
            if (idProfesseur != null) {
                NoteDAO noteDAO = new NoteDAO();
                List<Note> resultats = noteDAO.getNotesByEnseignant(idProfesseur);
                request.setAttribute("resultats", resultats);
            }

            // Forward back to the JSP with error message
            request.getRequestDispatcher("/prof/gestion_notes.jsp").forward(request, response);
            return;
        }

        // Update notes in the database if all validations pass
        NoteDAO noteDAO = new NoteDAO();
        noteDAO.updateNotes(idResultatsList, nouvellesNotesList);

        // Fetch updated results for the professor
        HttpSession session = request.getSession();
        Integer idProfesseur = (Integer) session.getAttribute("id_professeur");
        if (idProfesseur != null) {
            List<Note> resultats = noteDAO.getNotesByEnseignant(idProfesseur);
            request.setAttribute("resultats", resultats);
        }

        // Set success message
        request.setAttribute("message", "Les notes sont enregistrées.");

        // Forward back to the JSP
        request.getRequestDispatcher("/prof/gestion_notes.jsp").forward(request, response);
    }



}
