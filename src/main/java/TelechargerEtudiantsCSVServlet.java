import dao.EtudiantDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.Etudiant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "TelechargerEtudiantsCSVServlet", value = "/telechargerEtudiantsCSV")
public class TelechargerEtudiantsCSVServlet extends HttpServlet {

    private EtudiantDAO etudiantDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        etudiantDAO = new EtudiantDAO(); // Initialisation du DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer la liste des étudiants depuis la base de données
        List<Etudiant> etudiants = etudiantDAO.getAllEtudiants();

        // Définir le type de contenu et le nom du fichier
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=etudiants.csv");

        // Écrire les données dans le fichier CSV
        try (PrintWriter writer = response.getWriter()) {
            // En-tête CSV
            writer.println("ID,Nom,Prénom,Email,Contact,Classe,Filière");

            // Contenu CSV
            for (Etudiant etudiant : etudiants) {
                writer.printf("%d,%s,%s,%s,%s,%s,%s%n",
                        etudiant.getIdEtudiant(),
                        etudiant.getUtilisateur().getNom(),
                        etudiant.getUtilisateur().getPrenom(),
                        etudiant.getUtilisateur().getEmail(),
                        etudiant.getContact(),
                        etudiant.getClasse() != null ? etudiant.getClasse().toString() : "N/A",
                        etudiant.getFiliere() != null ? etudiant.getFiliere().toString() : "N/A");
            }
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la génération du fichier CSV : " + e.getMessage());
        }
    }
}
