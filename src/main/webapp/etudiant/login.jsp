<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion Étudiant</title>
</head>
<body>
<h2>Se connecter</h2>

<!-- Affichage du message d'erreur si disponible -->


<form action="loginController" method="post">
    <label for="id_etudiant">Identifiant Étudiant :</label>
    <input type="text" id="id_etudiant" name="id_etudiant" required><br><br>

    <label for="mot_de_passe">Mot de passe :</label>
    <input type="password" id="mot_de_passe" name="mot_de_passe" required><br><br>

    <input type="submit" value="Se connecter">
    <% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
</form>
</body>
</html>
