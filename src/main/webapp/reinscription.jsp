<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mise à Jour Étudiant</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/form-style.css">
</head>
<body>
<header class="page-header">
    <h1>Formulaire de Mise à Jour des Informations Étudiant</h1>
</header>

<%
    // Récupération du message d'erreur ou de succès
    String message = (String) request.getAttribute("message");
    if (message != null && !message.isEmpty()) {
%>
<div style="color: red; font-weight: bold;">
    <%= message %>
</div>
<%
    }
%>

<form action="mettreAJourEtudiant" method="post">
    <label for="id_etudiant">Identifiant Étudiant :</label>
    <input type="number" name="id_etudiant" id="id_etudiant" required /><br />

    <label for="numero_adresse">Numéro Adresse :</label>
    <input type="number" name="numero_adresse" id="numero_adresse" required /><br />

    <label for="adresse">Adresse :</label>
    <input type="text" name="adresse" id="adresse" required /><br />

    <label for="code_postal">Code Postal :</label>
    <input type="number" name="code_postal" id="code_postal" required /><br />

    <label for="commune">Commune :</label>
    <input type="text" name="commune" id="commune" required /><br />

    <label for="email">Email :</label>
    <input type="email" name="email" id="email" required /><br />

    <label for="contact">Contact :</label>
    <input type="text" name="contact" id="contact" required /><br />

    <label for="classe">Classe :</label>
    <select name="classe" id="classe" required>
        <option value="ING1">ING 1</option>
        <option value="ING2">ING 2</option>
        <option value="ING3">ING 3</option>
    </select><br />

    <label for="filiere">Spécialité :</label>
    <select name="filiere" id="filiere" required>
        <option value="GI">GI</option>
        <option value="GM">GM</option>
        <option value="GC">GC</option>
    </select><br />
    <input type="submit" value="Mettre à Jour" />
</form>
</body>
</html>
