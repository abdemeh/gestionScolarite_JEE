<%@ page import="admin.liste.etudiant_professeur.nonhibernate.modele.Professeur" %>
<%@ page import="java.util.List" %>
<%@ page import="modele.Enseignant" %>
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
        List<Enseignant> professeurs = (List<Enseignant>) request.getAttribute("professeurs");
        if (professeurs != null) {
            for (Enseignant professeur : professeurs) {
    %>
    <tr>
        <td><%= professeur.getIdEnseignant() %></td>
        <td><%= professeur.getUtilisateur().getNom() %></td>
        <td><%= professeur.getUtilisateur().getPrenom() %></td>
        <td><%= professeur.getUtilisateur().getAdresse() %></td>
        <td><%= professeur.getUtilisateur().getEmail() %></td>
        <td><%= professeur.getContact() %></td>
        <td><%= professeur.getSpecialite() %></td>
        <td>
            <a href="supprimerProfesseur?id=<%= professeur.getIdEnseignant() %>" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce professeur ?')">Supprimer</a>
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
