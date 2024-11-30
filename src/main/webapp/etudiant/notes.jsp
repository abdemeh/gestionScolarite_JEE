<%@ page import="java.util.List" %>
<%@ page import="accespagetudiant.autre.Note" %>
<%@ page import="accespagetudiant.autre.Horaire_cours" %>
<%@ page import="modele.InscriptionCours" %>
<%@ page import="jakarta.persistence.criteria.CriteriaBuilder" %>
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
        List<modele.Note> notes = (List<modele.Note>) request.getAttribute("notes");

        // Vérifier si la liste n'est pas vide
        if (notes != null) {
            // Parcourir la liste des notes et afficher chaque ligne dans le tableau
            for (modele.Note note : notes) {
    %>
    <tr>
        <td><%= note.getCours().getNomCours()%></td>
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
        <th>Enseignant</th>
        <th>Date debut</th>
        <th>Date fin</th>
    </tr>
    <%
        List<InscriptionCours> inscriptionCours= (List<InscriptionCours>) request.getAttribute("inscriptionCours");
        // Récupérer la liste des notes passée par la servlet
        for (InscriptionCours inscription:inscriptionCours) {

        // Vérifier si la liste n'est pas vide

            // Parcourir la liste des notes et afficher chaque ligne dans le tableau

    %>
    <tr>

        <td><%= inscription.getCours().getNomCours() %></td>
        <td><%= inscription.getCours().getEnseignant().getUtilisateur().getNom()%>  <%=inscription.getCours().getEnseignant().getUtilisateur().getPrenom() %></td>
        <td><%= inscription.getDebutCours() %></td>
        <td><%= inscription.getFinCours() %></td>
    </tr>
    <%

        }
    %>
</table>
</body>
</html>
