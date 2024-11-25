<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="admin.attribution_cours.poureleve.Pourelevestat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <th>Date d'Inscription</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Map<String, String>> inscriptions = (List<Map<String, String>>) request.getAttribute("inscriptions");
                if (inscriptions != null && !inscriptions.isEmpty()) {
                    for (Map<String, String> inscription : inscriptions) {
            %>
            <tr>
                <td><%= inscription.get("id_etudiant") %></td>
                <td><%= inscription.get("classe") %></td>
                <td><%= inscription.get("filiere") %></td>
                <td><%= inscription.get("nom_cours") %></td>
                <td><%= inscription.get("nom_professeur") %></td>
                <td><%= inscription.get("date_inscription") %></td>
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
            Map<String, String> etudiant = Pourelevestat.etudiant;
            if (etudiant != null) {
        %>
        <div style="margin-top: 20px;">
            <p><strong>ID Étudiant :</strong> <%= etudiant.get("id_etudiant") %></p>
            <p><strong>Nom :</strong> <%= etudiant.get("nom") %></p>
            <p><strong>Prénom :</strong> <%= etudiant.get("prenom") %></p>
            <p><strong>Classe :</strong> <%= etudiant.get("classe") %></p>
            <p><strong>Filière :</strong> <%= etudiant.get("filiere") %></p>
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
            List<Map<String, String>> listeProfesseursCours = Pourelevestat.ListeProfesseurpourcecours;
            if (listeProfesseursCours != null && !listeProfesseursCours.isEmpty()) {
        %>
        <form action="inscrireCours" method="post" style="margin-top: 20px;">
            <label for="selectCours">Sélectionner un cours :</label>
            <select name="id_cours" id="selectCours" required>
                <%
                    for (Map<String, String> profCours : listeProfesseursCours) {
                %>
                <option value="<%= profCours.get("id_cours") %>">
                    Cours: <%= profCours.get("nom_cours") %> | Professeur: <%= profCours.get("nom_professeur") %>
                </option>
                <%
                    }
                %>
            </select><br />

            <!-- Champ pour la date -->
            <label for="date_inscription">Date du cours :</label>
            <input type="datetime-local" name="date_inscription" id="date_inscription" required /><br />

            <!-- Bouton pour soumettre le formulaire -->
            <button type="submit" style="margin-top: 10px;">Enregistrer</button>
            <% if (request.getAttribute("message") != null) { %>
            <p style="color: green;"><%= request.getAttribute("message") %></p>
            <% } %>

        </form>
        <%
        } else if (request.getParameter("searchCours") != null) {
        %>
        <div style="margin-top: 20px; color: red;">Aucun cours disponible.</div>
        <%
            }
        %>
    </div>
</div>

<footer>
    <p>© 2024 - Gestion des Inscriptions</p>
</footer>
</body>
</html>
