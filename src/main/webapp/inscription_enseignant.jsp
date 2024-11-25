<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inscription Élève</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/form-style.css">
</head>
<body>

<!-- Titre principal avec une classe spécifique -->
<header class="page-header">
    <h1>Bienvenue sur le formulaire d'inscription des professeurs</h1>
</header>

    <%
    // Récupération du message d'erreur
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
<div style="color: red; font-weight: bold;">
    <%= errorMessage %>
</div>
    <%
    }
%>
<form action="creer_enseignant" method="post">
    <label for="nom">Nom:</label>
    <input type="text" name="nom" id="nom" required /><br />

    <label for="prenom">Prénom:</label>
    <input type="text" name="prenom" id="prenom" required /><br />

    <label for="telephone">Téléphone:</label>
    <input type="text" name="telephone" id="telephone" required /><br />

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required /><br />

    <label for="mot_de_passe">Mot de Passe:</label>
    <input type="password" name="mot_de_passe" id="mot_de_passe" required /><br />

    <label for="date_naissance">Date de Naissance:</label>
    <input type="date" name="date_naissance" id="date_naissance" required /><br />

    <label for="numero_adresse">Numéro Adresse:</label>
    <input type="number" name="numero_adresse" id="numero_adresse" required /><br />

    <label for="adresse">Adresse:</label>
    <input type="text" name="adresse" id="adresse" required /><br />

    <label for="commune">Commune:</label>
    <input type="text" name="commune" id="commune" required /><br />

    <label for="code_postal">Code Postal:</label>
    <input type="number" name="code_postal" id="code_postal" required /><br />

    <label for="specialite">Spécialité:</label>
    <select name="specialite" id="specialite" required>
        <option value="Mathematique">Mathématique</option>
        <option value="Informatique">Informatique</option>
        <option value="Physique">Physique</option>
        <option value="Chimie">Chimie</option>
        <option value="Humanité">Humanité</option>
    </select><br />

    <input type="submit" value="Soumettre" />
</form>
