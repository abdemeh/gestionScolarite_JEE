<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="admin.attribution_cours.pourprof.Pourprofstat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Attribution de Cours</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/form-style.css">
</head>
<body>
<header>
    <h1>Attribution de Cours aux Professeurs</h1>
</header>

<div style="display: flex; justify-content: space-between;">

    <!-- Partie gauche : Liste des cours -->
    <div style="width: 45%; border: 1px solid #ccc; padding: 15px;">
        <h2>Liste des Cours</h2>
        <table border="1" cellpadding="10" style="width: 100%;">
            <thead>
            <tr>
                <th>ID Cours</th>
                <th>Nom</th>
                <th>Description</th>
                <th>ID Enseignant</th>
            </tr>
            </thead>
            <tbody>
            <%
                // Récupérer la liste des cours depuis la requête
                List<Map<String, String>> cours = (List<Map<String, String>>) request.getAttribute("cours");
                if (cours != null && !cours.isEmpty()) {
                    for (Map<String, String> c : cours) {
            %>
            <tr>
                <td><%= c.get("id_cours") %></td>
                <td><%= c.get("nom_cours") %></td>
                <td><%= c.get("description") %></td>
                <td><%= c.get("id_enseignant") != null ? c.get("id_enseignant") : "Non attribué" %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="4">Aucun cours trouvé.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- Partie droite : Recherche et ajout -->
    <div style="width: 45%; border: 1px solid #ccc; padding: 15px;">
        <h2>Attribuer un Cours à un Professeur</h2>

        <!-- Barre de recherche pour les professeurs -->
        <form action="rechercherProfesseur" method="get">
            <label for="search">Rechercher un professeur :</label>
            <input type="text" name="search" id="search" placeholder="Nom ou Prénom du Professeur" required />
            <button type="submit">Rechercher</button>
        </form>

        <!-- Affichage des résultats de recherche -->
        <%
            if (Pourprofstat.prof != null) {
        %>
        <div style="margin-top: 20px;">
            <p><strong>ID Professeur :</strong> <%= Pourprofstat.prof.get("id_enseignant") %></p>
            <p><strong>Nom :</strong> <%= Pourprofstat.prof.get("nom") %></p>
            <p><strong>Prénom :</strong> <%= Pourprofstat.prof.get("prenom") %></p>
        </div>
        <% } else if (request.getParameter("search") != null) { %>
        <div style="margin-top: 20px; color: red;">Aucun professeur trouvé.</div>
        <% } %>

        <!-- Formulaire pour ajouter un cours -->
        <%
            if (Pourprofstat.prof != null) {
        %>
        <form action="attribuerCours" method="post" style="margin-top: 20px;">
            <input type="hidden" name="id_professeur" value="<%= Pourprofstat.prof.get("id_enseignant") %>" />

            <label for="nom_cours">Nom du cours :</label>
            <input type="text" name="nom_cours" id="nom_cours" required /><br />

            <label for="description">Description :</label>
            <textarea name="description" id="description" rows="5" required></textarea><br />

            <button type="submit" style="margin-top: 10px;">Enregistrer</button>
        </form>
        <% } else { %>
        <div style="margin-top: 20px; color: red;">Veuillez d'abord rechercher et sélectionner un professeur.</div>
        <% } %>

        <% if (request.getAttribute("message") != null) { %>
        <div style="margin-top: 20px; color: green;"><%= request.getAttribute("message") %></div>
        <% } %>
    </div>
</div>

<footer>
    <p>© 2024 - Gestion des Cours</p>
</footer>
</body>
</html>
