<%@ page import="java.util.List" %>
<%@ page import="springboot.modelesringboot.Etudiant" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <script src="<%= request.getContextPath() %>/js/app.js" defer></script>
</head>
<body>


<!-- springboot.Main Content -->
<main>
    <header>
        <h1>Liste des Étudiants</h1>
    </header>
    <div class="container">
        <table>
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
                List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiantList");
                if (etudiants != null && !etudiants.isEmpty()) {
                    for (Etudiant etudiant : etudiants) {
            %>
            <tr>
                <td><%= etudiant.getIdEtudiant() %></td>
                <td><%= etudiant.getUtilisateur().getNom() %></td>
                <td><%= etudiant.getUtilisateur().getPrenom() %></td>
                <td><%= etudiant.getUtilisateur().getAdresse() %></td>
                <td><%= etudiant.getUtilisateur().getEmail() %></td>
                <td><%= etudiant.getContact() %></td>
                <td><%= etudiant.getClasse() %></td>
                <td><%= etudiant.getFiliere() %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/supprimerEtudiant?id=<%= etudiant.getIdEtudiant() %>"
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')">Supprimer</a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="9">Aucun étudiant trouvé.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
    <footer>
        <p>© 2024 - Gestion Scolaire</p>
    </footer>
</main>
</body>
</html>
