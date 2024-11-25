<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menu Administratif</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/menu-style.css">
</head>
<body>
<header>
    <h1>Bienvenue dans le Menu Administratif</h1>
</header>

<div class="menu-container">
    <h2>Que souhaitez-vous faire ?</h2>
    <ul>
        <li><a href="<%= request.getContextPath() %>/listeProfesseurs">Voir la liste des professeurs</a></li>
        <a href="<%= request.getContextPath() %>/listeEtudiants">Afficher la liste des étudiants</a>
        <li><a href="<%= request.getContextPath() %>/listeCours">Attribuer un cours à un professeur</a></li>
        <li><a href="<%= request.getContextPath() %>/listeInscriptions">Inscrire un élève à un cours</a></li>
    </ul>
</div>

<footer>
    <p>© 2024 - Gestion Scolaire</p>
</footer>
</body>
</html>
