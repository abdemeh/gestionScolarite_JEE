<%@ page import="admin.liste.etudiant_professeur.modele.Etudiant" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/table-style.css">
</head>
<body>
<header>
    <h1>Liste des Étudiants</h1>
</header>

<table border="1" cellpadding="10">
    <thead>
    <tr>
        <th>ID Étudiant</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Adresse</th>
        <th>Email</th>
        <th>Contact</th>
        <th>Classe</th>
        <th>Filière</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
        if (etudiants != null) {
            for (Etudiant etudiant : etudiants) {

    %>
    <tr>
        <td><%= etudiant.getIdEtudiant() %></td>
        <td><%= etudiant.getNom() %></td>
        <td><%= etudiant.getPrenom() %></td>
        <td><%= etudiant.getAdresse() %></td>
        <td><%= etudiant.getEmail() %></td>
        <td><%= etudiant.getContact() %></td>
        <td><%= etudiant.getClasse() %></td>
        <td><%= etudiant.getFiliere() %></td>
        <td>

            <a href="supprimerEtudiant?id=<%= etudiant.getIdEtudiant() %>" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')">Supprimer</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="9">Aucun étudiant trouvé.</td>
    </tr>
    <% } %>
    </tbody>
</table>
<footer>
    <p>© 2024 - Gestion Scolaire</p>
</footer>
</body>
</html>
