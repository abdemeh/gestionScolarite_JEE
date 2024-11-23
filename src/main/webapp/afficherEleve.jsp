<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Informations de l'élève</title>
</head>
<body>
<h1>Informations de l'élève créé</h1>
<p><strong>Nom:</strong> <%= request.getAttribute("nom") %></p>
<p><strong>Prénom:</strong> <%= request.getAttribute("prenom") %></p>
<p><strong>Téléphone:</strong> <%= request.getAttribute("telephone") %></p>
<p><strong>Email:</strong> <%= request.getAttribute("email") %></p>
<p><strong>Numéro Adresse:</strong> <%= request.getAttribute("numero_adresse") %></p>
<p><strong>Adresse:</strong> <%= request.getAttribute("adresse") %></p>
<p><strong>Code Postal:</strong> <%= request.getAttribute("code_postal") %></p>
</body>
</html>
