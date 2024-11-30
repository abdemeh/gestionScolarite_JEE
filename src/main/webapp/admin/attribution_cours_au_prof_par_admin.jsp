<%@ page import="java.util.List" %>
<%@ page import="modele.Cours" %>
<%@ page import="modele.Enseignant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Attribution de Cours</title>
</head>
<body>
<header>
    <h1>Attribution de Cours aux Professeurs</h1>
</header>

<div>
    <!-- Partie gauche : Liste des cours -->
    <div>
        <h2>Liste des Cours</h2>
        <table border="1" cellpadding="10">
            <thead>
            <tr>
                <th>ID Cours</th>
                <th>Nom</th>
                <th>Description</th>
                <th>Professeur</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Cours> cours = (List<Cours>) request.getAttribute("cours");
                if (cours != null && !cours.isEmpty()) {
                    for (Cours c : cours) {
            %>
            <tr>
                <td><%= c.getIdCours() %></td>
                <td><%= c.getNomCours() %></td>
                <td><%= c.getDescription() %></td>
                <td>
                    <% if (c.getEnseignant() != null) { %>
                    <%= c.getEnseignant().getUtilisateur().getNom() %> <%= c.getEnseignant().getUtilisateur().getPrenom() %>
                    <% } else { %>
                    Non attribué
                    <% } %>
                </td>
            </tr>
            <% } } else { %>
            <tr>
                <td colspan="4">Aucun cours trouvé.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- Partie droite : Attribution de cours -->
    <div>
        <h2>Attribuer un Cours</h2>
        <!-- Recherche d'un professeur -->
        <form action="rechercherProfesseur" method="get">
            <label for="search">Rechercher un professeur :</label>
            <input type="text" name="search" id="search" placeholder="Nom ou Prénom" required>
            <button type="submit">Rechercher</button>
        </form>

        <!-- Affichage des professeurs -->
        <%
            List<Enseignant> professeurs = (List<Enseignant>) request.getAttribute("professeurs");
            if (professeurs != null && !professeurs.isEmpty()) {
        %>
        <form action="attribuerCours" method="post">
            <label for="selectProf">Sélectionner un professeur :</label>
            <select name="id_professeur" id="selectProf" required>
                <% for (Enseignant prof : professeurs) { %>
                <option value="<%= prof.getIdEnseignant() %>">
                    <%= prof.getUtilisateur().getNom() %> <%= prof.getUtilisateur().getPrenom() %>
                </option>
                <% } %>
            </select>
            <br>
            <!-- Formulaire pour ajouter un cours -->
            <label for="nom_cours">Nom du cours :</label>
            <input type="text" name="nom_cours" id="nom_cours" required><br>

            <label for="description">Description :</label>
            <textarea name="description" id="description" rows="5" required></textarea><br>

            <button type="submit">Enregistrer</button>
        </form>
        <% } else if (request.getParameter("search") != null) { %>
        <p style="color: red;">Aucun professeur trouvé.</p>
        <% } %>

        <!-- Affichage des messages -->
        <% if (request.getAttribute("message") != null) { %>
        <p style="color: green;"><%= request.getAttribute("message") %></p>
        <% } %>
    </div>
</div>

<footer>
    <p>© 2024 - Gestion des Cours</p>
</footer>
</body>
</html>
