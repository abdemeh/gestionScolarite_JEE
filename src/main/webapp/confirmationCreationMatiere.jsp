<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation de Création</title>
</head>
<body>
<h1>Le cours a été créé avec succès !</h1>
<p><strong>Nom:</strong> <%= request.getAttribute("nom") %></p>
<p><strong>Types:</strong></p>
<ul>
    <%
        String[] types = (String[]) request.getAttribute("types");
        if (types != null) {
            for (String type : types) {
    %>
    <li><%= type %></li>
    <%
            }
        }
    %>
</ul>
<p><strong>Grade:</strong> <%= request.getAttribute("grade") %></p>
<p><strong>Filière:</strong> <%= request.getAttribute("filiere") %></p>
<a href="creer_cours.jsp">Créer un autre cours</a>
</body>
</html>

