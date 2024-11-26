<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Notes</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/form-style.css">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #f4f4f4;
        }

        input[type="number"] {
            width: 70px;
        }

        button {
            margin-top: 15px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<header>
    <h1>Gestion des Notes</h1>
</header>

<div>
    <!-- Afficher un message de succès ou d'erreur -->
    <%
        String message = (String) request.getAttribute("message");
        if (message != null && !message.isEmpty()) {
    %>
    <div style="color: green; font-weight: bold; margin-bottom: 20px;">
        <%= message %>
    </div>
    <%
        }
    %>

    <!-- Tableau des étudiants avec les notes -->
    <form action="modifierNotes" method="post">
        <table>
            <thead>
            <tr>
                <th>ID Étudiant</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Nom du Cours</th>
                <th>Note</th>
            </tr>
            </thead>
            <tbody>
            <%
                // Liste des résultats à afficher
                List<Map<String, String>> resultats = (List<Map<String, String>>) request.getAttribute("resultats");
                if (resultats != null && !resultats.isEmpty()) {
                    for (Map<String, String> resultat : resultats) {
            %>
            <tr>
                <td><%= resultat.get("id_etudiant") %></td>
                <td><%= resultat.get("nom") %></td>
                <td><%= resultat.get("prenom") %></td>
                <td><%= resultat.get("nom_cours") %></td>
                <td>
                    <input type="hidden" name="id_resultat" value="<%= resultat.get("id_resultat") %>" />
                    <input type="number" name="note_<%= resultat.get("id_resultat") %>" value="<%= resultat.get("note") %>" step="0.01" />
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5">Aucun résultat trouvé pour ce professeur.</td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <button type="submit">Enregistrer les Modifications</button>
    </form>
</div>

<footer>
    <p>© 2024 - Gestion des Notes</p>
</footer>
</body>
</html>
