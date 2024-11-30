<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="modele.Cours" %>
<%@ page import="modele.Enseignant" %>
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
                    <%= c.getEnseignant() != null
                            ? c.getEnseignant().getUtilisateur().getNom() + " " + c.getEnseignant().getUtilisateur().getPrenom()
                            : "Non assigné" %>
                </td>
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

        <%
            List<Enseignant> professeurs = (List<Enseignant>) request.getAttribute("professeurs");
            if (professeurs != null && !professeurs.isEmpty()) {
        %>
        <h3>Résultats de la recherche</h3>
        <ul>
            <%
                for (Enseignant prof : professeurs) {
            %>
            <li>
                <strong>ID :</strong> <%= prof.getIdEnseignant() %> |
                <strong>Nom :</strong> <%= prof.getUtilisateur().getNom() %> <%= prof.getUtilisateur().getPrenom() %>
            </li>
            <%
                }
            %>
        </ul>
        <% } else if (request.getParameter("search") != null) { %>
        <p style="color: red;">Aucun professeur trouvé.</p>
        <% } %>

        <!-- Formulaire pour attribuer un cours -->
        <form action="attribuerCours" method="post" style="margin-top: 20px;">
            <label for="id_professeur">Sélectionner un professeur :</label>
            <select name="id_professeur" id="id_professeur" required>
                <%
                    if (professeurs != null && !professeurs.isEmpty()) {
                        for (Enseignant prof : professeurs) {
                %>
                <option value="<%= prof.getIdEnseignant() %>">
                    <%= prof.getUtilisateur().getNom() %> <%= prof.getUtilisateur().getPrenom() %>
                </option>
                <%
                        }
                    }
                %>
            </select><br />

            <label for="nom_cours">Nom du cours :</label>
            <input type="text" name="nom_cours" id="nom_cours" required /><br />

            <label for="description">Description :</label>
            <textarea name="description" id="description" rows="5" required></textarea><br />

            <button type="submit" style="margin-top: 10px;">Attribuer</button>
        </form>

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
