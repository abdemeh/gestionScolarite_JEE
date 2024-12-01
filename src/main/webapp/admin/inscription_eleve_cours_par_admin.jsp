<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="admin.attribution_cours.poureleve.Pourelevestat" %>
<%@ page import="modele.InscriptionAnnee" %>
<%@ page import="dao.CoursDAO" %>
<%@ page import="modele.InscriptionCours" %>
<%@ page import="modele.Cours" %>
<%@ page import="modele.Etudiant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Session verification
    String userRole = (String) session.getAttribute("user");
    if (userRole == null || !"admin".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return; // Ensure the rest of the page is not processed
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription des Étudiants aux Cours</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/form-style.css">
</head>
<body>
<header>
    <h1>Inscription des Étudiants aux Cours</h1>
</header>

<div style="display: flex; justify-content: space-between;">

    <!-- Partie gauche : Liste des inscriptions -->
    <div style="width: 45%; border: 1px solid #ccc; padding: 15px;">
        <h2>Liste des Étudiants Inscrits</h2>
        <table border="1" cellpadding="10" style="width: 100%;">
            <thead>
            <tr>
                <th>ID Étudiant</th>
                <th>Classe</th>
                <th>Filière</th>
                <th>Nom du Cours</th>
                <th>Professeur</th>
                <th>Période</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<InscriptionCours> inscriptions = (List<InscriptionCours>) request.getAttribute("inscriptions");
                if (inscriptions != null && !inscriptions.isEmpty()) {
                    for (InscriptionCours inscription : inscriptions) {
            %>
            <tr>
                <td><%= inscription.getEtudiant().getIdEtudiant() %></td>
                <td><%= inscription.getEtudiant().getClasse() %></td>
                <td><%= inscription.getEtudiant().getFiliere() %></td>
                <td><%= inscription.getCours().getNomCours() %></td>
                <td><%= inscription.getCours().getEnseignant().getUtilisateur().getPrenom() %>
                    <%= inscription.getCours().getEnseignant().getUtilisateur().getNom() %>
                </td>
                <td><%= inscription.getDebutCours() %> - <%= inscription.getFinCours() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="6">Aucune inscription trouvée.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- Partie droite : Inscription -->
    <div style="width: 45%; border: 1px solid #ccc; padding: 15px;">
        <h2>Inscrire un Étudiant à un Cours</h2>

        <!-- Recherche d'étudiant -->
        <form action="rechercherEtudiant" method="get">
            <label for="searchEtudiant">Rechercher un étudiant :</label>
            <input type="text" name="searchEtudiant" id="searchEtudiant" placeholder="ID ou Nom de l'Étudiant" required />
            <button type="submit">Rechercher</button>
        </form>
        <%
            Etudiant etudiant = Pourelevestat.etudiantparticulier;
            if (etudiant != null) {
        %>
        <div style="margin-top: 20px;">
            <p><strong>ID Étudiant :</strong> <%= etudiant.getIdEtudiant() %></p>
            <p><strong>Nom :</strong> <%= etudiant.getUtilisateur().getNom() %></p>
            <p><strong>Prénom :</strong> <%= etudiant.getUtilisateur().getPrenom() %></p>
            <p><strong>Classe :</strong> <%= etudiant.getClasse() %></p>
            <p><strong>Filière :</strong> <%= etudiant.getFiliere() %></p>
        </div>
        <% } else if (request.getParameter("searchEtudiant") != null) { %>
        <div style="margin-top: 20px; color: red;">Aucun étudiant trouvé.</div>
        <% } %>

        <!-- Recherche de cours -->
        <form action="rechercherCours" method="get" style="margin-top: 20px;">
            <label for="searchCours">Rechercher un cours :</label>
            <input type="text" name="searchCours" id="searchCours" placeholder="Nom du Cours" required />
            <button type="submit">Rechercher</button>
        </form>

        <%
            List<Cours> cours = (List<Cours>) request.getAttribute("cours");
            if (cours != null && !cours.isEmpty()) {
        %>
        <form action="inscrireCours" method="post" style="margin-top: 20px;">
            <label for="selectCours">Sélectionner un cours :</label>
            <select name="id_cours" id="selectCours" required>
                <%
                    for (Cours lecours : cours) {
                %>
                <option value="<%= lecours.getIdCours() %>">
                    <%= lecours.getNomCours() %> | Professeur : <%= lecours.getEnseignant().getUtilisateur().getNom() %>
                </option>
                <%
                    }
                %>
            </select><br />

            <!-- Sélection de la date -->
            <label for="dateCours">Date du cours :</label>
            <input type="date" name="dateCours" id="dateCours" required /><br />

            <!-- Sélection de l'heure de début -->
            <label for="debutHeure">Heure de début :</label>
            <select name="debutHeure" id="debutHeure" required>
                <%
                    for (int heure = 8; heure <= 20; heure++) {
                        String heureFormattee = String.format("%02d:00", heure);
                %>
                <option value="<%= heureFormattee %>"><%= heureFormattee %></option>
                <%
                    }
                %>
            </select><br />

            <!-- Sélection de l'heure de fin -->
            <label for="finHeure">Heure de fin :</label>
            <select name="finHeure" id="finHeure" required>
                <%
                    for (int heure = 9; heure <= 21; heure++) {
                        String heureFormattee = String.format("%02d:00", heure);
                %>
                <option value="<%= heureFormattee %>"><%= heureFormattee %></option>
                <%
                    }
                %>
            </select><br />

            <input type="hidden" name="id_etudiant" value="<%= etudiant != null ? etudiant.getIdEtudiant() : "" %>" />
            <button type="submit">Inscrire</button>
        </form>

        <% } else if (etudiant != null) { %>
        <div style="margin-top: 20px; color: red;">Aucun cours trouvé.</div>
        <% } %>
    </div>
</div>

<footer>
    <p>© 2024 - Gestion des Inscriptions</p>
</footer>
</body>
</html>
