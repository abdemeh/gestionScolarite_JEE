<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion Professeur</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<header>
    <h1>Connexion Professeur</h1>
</header>


<form action="listeResultats" method="post">
        <label for="id_enseignant">ID Enseignant :</label>
    <input type="text" id="id_enseignant" name="id_enseignant" required><br><br>

        <label for="mot_de_passe">Mot de Passe :</label>
    <input type="password" name="mot_de_passe" id="mot_de_passe" required /><br />

    <input type="submit" value="Soumettre" />
</form>

    <% if (request.getAttribute("message") != null) { %>
    <p style="color: red;"><%= request.getAttribute("message") %></p>
    <% } %>

<footer>
    <p>© 2024 - Gestion Scolaire</p>
</footer>
</body>
</html>
