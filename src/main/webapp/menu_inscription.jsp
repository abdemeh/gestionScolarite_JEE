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
        <!-- Inscription des élèves -->
        <li style="margin-bottom: 20px;">
            <a href="creer_eleve" style="text-decoration: none; font-size: 18px;">S'inscrire en tant qu'Élève</a>
        </li>
        <li style="margin-bottom: 20px;">
            <a href="reinscription.jsp" style="text-decoration: none; font-size: 18px;">Se Réinscrire en tant qu'Élève</a>
        </li>
        <li style="margin-bottom: 20px;">
            <a href="creer_enseignant" style="text-decoration: none; font-size: 18px;">S'inscrire en tant qu'Enseignant</a>
        </li>
        <!-- Connexion Professeur -->
        <li style="margin-bottom: 20px;">
            <a href="listeResultats" style="text-decoration: none; font-size: 18px;">Se Connecter en tant que Professeur</a>
        </li>
        <!-- Connexion Administrateur -->
        <li style="margin-bottom: 20px;">
            <a href="connexionAdmin" style="text-decoration: none; font-size: 18px;">Se Connecter en tant qu'Administrateur</a>
        </li>
    </ul>
</div>

<footer style="text-align: center; margin-top: 50px;">
    <p>© 2024 - Gestion des Inscriptions</p>
</footer>
</body>
</html>
