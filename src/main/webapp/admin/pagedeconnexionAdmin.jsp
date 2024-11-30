<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion Administrateur</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.scss">
</head>
<body>
<header>
    <h1>Connexion Administrateur</h1>
</header>

<form action="connexionAdmin" method="post">

    <label for="id_admin">Identifiant :</label>
    <input type="text" id="id_admin" name="id_admin" required><br><br>

    <label for="mot_de_passe">Mot de Passe :</label>
    <input type="password" name="mot_de_passe" id="mot_de_passe" required /><br />

    <input type="submit" value="Se connecter" />
</form>

<% if (request.getAttribute("message") != null) { %>
<p style="color: red;"><%= request.getAttribute("message") %></p>
<% } %>

<footer>
    <p>© 2024 - Gestion Scolaire</p>
</footer>
</body>
</html>
