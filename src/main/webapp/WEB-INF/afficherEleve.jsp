<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h1>Confirmation des informations</h1>
<p>Veuillez vérifier les informations avant de les enregistrer :</p>

<ul>
    <li><strong>Nom :</strong> <%= request.getAttribute("nom") %></li>
    <li><strong>Prénom :</strong> <%= request.getAttribute("prenom") %></li>
    <li><strong>Téléphone :</strong> <%= request.getAttribute("telephone") %></li>
    <li><strong>Email :</strong> <%= request.getAttribute("email") %></li>
    <li><strong>Mot de Passe :</strong> <%= request.getAttribute("mot_de_passe") %></li>
    <li><strong>Numéro Adresse :</strong> <%= request.getAttribute("numero_adresse") %></li>
    <li><strong>Adresse :</strong> <%= request.getAttribute("adresse") %></li>
    <li><strong>Code Postal :</strong> <%= request.getAttribute("code_postal") %></li>
    <li><strong>Commune :</strong> <%= request.getAttribute("commune") %></li>
    <li><strong>Date de Naissance :</strong> <%= request.getAttribute("date_naissance") %></li>
    <li><strong>Classe :</strong> <%= request.getAttribute("classe") %></li>
    <li><strong>Spécialité :</strong> <%= request.getAttribute("specialite") %></li>
</ul>

<form action="enregistrer_eleve" method="post">
    <input type="hidden" name="nom" value="<%= request.getAttribute("nom") %>">
    <input type="hidden" name="prenom" value="<%= request.getAttribute("prenom") %>">
    <input type="hidden" name="telephone" value="<%= request.getAttribute("telephone") %>">
    <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">
    <input type="hidden" name="mot_de_passe" value="<%= request.getAttribute("mot_de_passe") %>">
    <input type="hidden" name="numero_adresse" value="<%= request.getAttribute("numero_adresse") %>">
    <input type="hidden" name="adresse" value="<%= request.getAttribute("adresse") %>">
    <input type="hidden" name="code_postal" value="<%= request.getAttribute("code_postal") %>">
    <input type="hidden" name="commune" value="<%= request.getAttribute("commune") %>">
    <input type="hidden" name="date_naissance" value="<%= request.getAttribute("date_naissance") %>">
    <input type="hidden" name="classe" value="<%= request.getAttribute("classe") %>">
    <input type="hidden" name="specialite" value="<%= request.getAttribute("specialite") %>">


    <input type="hidden" name="action" value="enregistrer">
    <button type="submit">Confirmer</button>
</form>


