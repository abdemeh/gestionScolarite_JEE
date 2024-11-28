<%@ page import="java.util.List" %>
<%@ page import="accespagetudiant.Note" %>
<%@ page import="accespagetudiant.Horaire_cours" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Notes</title>
</head>
<body>
<h2>Liste des Notes</h2>

<table border="1">
    <tr>
        <th>Nom du Cours</th>
        <th>Note</th>
    </tr>
    <%
        // Récupérer la liste des notes passée par la servlet
        List<Note> notes = (List<Note>) request.getAttribute("notes");

        // Vérifier si la liste n'est pas vide
        if (notes != null) {
            // Parcourir la liste des notes et afficher chaque ligne dans le tableau
            for (Note note : notes) {
    %>
    <tr>
        <td><%= note.getCoursNom() %></td>
        <td><%= note.getNote() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
<table border="1">
    <tr>
        <th>Nom du Cours</th>
        <th>Date</th>
    </tr>
    <%
        // Récupérer la liste des notes passée par la servlet
        List<Horaire_cours> date_cours = (List<Horaire_cours>) request.getAttribute("date_cours");

        // Vérifier si la liste n'est pas vide
        if (date_cours != null) {
            // Parcourir la liste des notes et afficher chaque ligne dans le tableau
            for (Horaire_cours horaire_cours : date_cours) {
    %>
    <tr>
        <td><%= horaire_cours.getCoursNom() %></td>
        <td><%= horaire_cours.getDate() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
