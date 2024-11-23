<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Créer un Cours</title>
</head>
<body>
<h1>Créer un Cours</h1>
<form action="creer_matiere" method="post">
    Nom: <input type="text" name="nom" required /><br />
    Type:
    <select name="type" multiple required>
        <option value="Mathematiques">Mathématiques</option>
        <option value="Physique">Physique</option>
        <option value="Chimie">Chimie</option>
        <option value="Biologie">Biologie</option>
        <option value="Humanites">Humanités</option>
        <option value="Informatique">Informatique</option>
        <option value="Autre">Autre</option>
    </select><br />
    <p>Maintenez la touche Ctrl (ou Cmd sur Mac) pour sélectionner plusieurs options.</p>



    Grade:
    <select name="grade" required>
        <option value="Bac+1">Bac+1</option>
        <option value="Bac+2">Bac+2</option>
        <option value="Bac+3">Bac+3</option>
        <option value="Bac+4">Bac+4</option>
        <option value="Bac+5">Bac+5</option>
    </select><br />

    Filière:
    <select name="filiere" required>
        <option value="GI">GI (Génie Informatique)</option>
        <option value="GM">GM (Génie Mécanique)</option>
        <option value="GC">GC (Génie Civil)</option>
        <option value="BTC">BTC (Bâtiment et Travaux Publics)</option>
    </select><br />

    <input type="submit" value="Créer" />
</form>
</body>
</html>
