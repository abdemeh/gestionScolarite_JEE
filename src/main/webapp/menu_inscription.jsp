<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menu d'Inscription</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<header>
    <h1>Bienvenue dans le Menu d'Inscription</h1>
</header>

<div class="menu-container" style="text-align: center; margin-top: 50px;">
    <h2>Que souhaitez-vous faire ?</h2>
    <ul style="list-style-type: none; padding: 0;">
        <li style="margin-bottom: 20px;">
            <a href="inscription_eleve.jsp" style="text-decoration: none; font-size: 18px;">S'inscrire en tant qu'Élève</a>
        </li>
        <li style="margin-bottom: 20px;">
            <a href="reinscription.jsp" style="text-decoration: none; font-size: 18px;">Se Réinscrire en tant qu'Élève</a>
        </li>
        <li style="margin-bottom: 20px;">
            <a href="inscription_enseignant.jsp" style="text-decoration: none; font-size: 18px;">S'inscrire en tant qu'Enseignant</a>
        </li>
    </ul>
</div>

<footer style="text-align: center; margin-top: 50px;">
    <p>© 2024 - Gestion des Inscriptions</p>
</footer>
</body>
</html>
