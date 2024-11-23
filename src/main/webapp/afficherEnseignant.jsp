<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire de saisie</title>
</head>
<body>
<%
    // Récupération du message d'erreur
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
<div>
    <%= errorMessage %>
</div>
<%
    }
%>

<form action="creer_eleve" method="post">
    Nom: <input type="text" name="nom" required /><br />
    Prénom: <input type="text" name="prenom" required /><br />
    Téléphone: <input type="text" name="telephone" required /><br />
    Email: <input type="email" name="email" required /><br />

    <!-- Champs alignés sur la même ligne -->
    <span>
        Numéro adresse: <input type="number" name="numero_adresse" required />
        Adresse: <input type="text" name="adresse" required />
    </span><br />

    Code Postal: <input type="number" name="code_postal" required /><br />
    <input type="submit" value="Soumettre" />
</form>
</body>
</html>
