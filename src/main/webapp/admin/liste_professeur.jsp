<%@ page import="modele.Professeur" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Professeurs</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/table-style.css">
</head>
<body>
<header>
    <h1>Liste des Professeurs</h1>
</header>

<table border="1" cellpadding="10">
    <thead>
    <tr>
        <th>ID Professeur</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Adresse</th>
        <th>Email</th>
        <th>Contact</th>
        <th>Spécialité</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Professeur> professeurs = (List<Professeur>) request.getAttribute("professeurs");
        if (professeurs != null) {
            for (Professeur professeur : professeurs) {
    %>
    <tr>
        <td><%= professeur.idProfesseur %></td>
        <td><%= professeur.nom %></td>
        <td><%= professeur.prenom %></td>
        <td><%= professeur.adresse %></td>
        <td><%= professeur.email %></td>
        <td><%= professeur.contact %></td>
        <td><%= professeur.specialite %></td>
        <td>
            <a href="modifierProfesseur?id=<%= professeur.idProfesseur %>">Modifier</a> |
            <a href="supprimerProfesseur?id=<%= professeur.idProfesseur %>" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce professeur ?')">Supprimer</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="8">Aucun professeur trouvé.</td>
    </tr>
    <% } %>
    </tbody>
</table>
<footer>
    <p>© 2024 - Gestion Scolaire</p>
</footer>
</body>
</html>
