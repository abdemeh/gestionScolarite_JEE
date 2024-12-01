import dao.EnseignantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Enseignant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "TelechargerProfesseursCSVServlet", value = "/telechargerProfesseursCSV")
public class TelechargerProfesseursCSVServlet extends HttpServlet {

    private EnseignantDAO professeurDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        professeurDAO = new EnseignantDAO(); // Initialisation du DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer la liste des professeurs depuis la base de données
        List<Enseignant> professeurs = professeurDAO.getAllProfesseurs();

        // Définir le type de contenu et le nom du fichier
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=professeurs.csv");

        // Écrire les données dans le fichier CSV
        try (PrintWriter writer = response.getWriter()) {
            // En-tête CSV
            writer.println("ID,Nom,Prénom,Email,Spécialité");

            // Contenu CSV
            for (Enseignant professeur : professeurs) {
                writer.printf("%d,%s,%s,%s,%s%n",
                        professeur.getIdEnseignant(),
                        professeur.getUtilisateur().getNom(),
                        professeur.getUtilisateur().getPrenom(),
                        professeur.getUtilisateur().getEmail(),
                        professeur.getSpecialite());
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la génération du fichier CSV : " + e.getMessage());
        }
    }
}
