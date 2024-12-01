<%@ page import="java.util.List" %>
<%@ page import="modele.Etudiant" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Session verification
    String userRole = (String) session.getAttribute("user");
    if (userRole == null || !"admin".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return; // Ensure the rest of the page is not processed
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <script src="<%= request.getContextPath() %>/js/app.js" defer></script>
</head>
<body>
<!-- Sidebar Navigation -->
<nav id="sidebar">
    <ul>
        <li>
            <span class="logo"><img src="<%= request.getContextPath() %>/images/logo.png" height="36px" alt="myCY Scolarité"></span>
            <button onclick="toggleSidebar()" id="toggle-btn">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="#e8eaed">
                    <path d="m313-480 155 156q11 11 11.5 27.5T468-268q-11 11-28 11t-28-11L228-452q-6-6-8.5-13t-2.5-15q0-8 2.5-15t8.5-13l184-184q11-11 27.5-11.5T468-692q11 11 11 28t-11 28L313-480Zm264 0 155 156q11 11 11.5 27.5T732-268q-11 11-28 11t-28-11L492-452q-6-6-8.5-13t-2.5-15q0-8 2.5-15t8.5-13l184-184q11-11 27.5-11.5T732-692q11 11 11 28t-11 28L577-480Z"/>
                </svg>
            </button>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/admin/pageAdmin.jsp">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" width="24px" viewBox="0 0 576 512"><path d="M575.8 255.5c0 18-15 32.1-32 32.1l-32 0 .7 160.2c0 2.7-.2 5.4-.5 8.1l0 16.2c0 22.1-17.9 40-40 40l-16 0c-1.1 0-2.2 0-3.3-.1c-1.4 .1-2.8 .1-4.2 .1L416 512l-24 0c-22.1 0-40-17.9-40-40l0-24 0-64c0-17.7-14.3-32-32-32l-64 0c-17.7 0-32 14.3-32 32l0 64 0 24c0 22.1-17.9 40-40 40l-24 0-31.9 0c-1.5 0-3-.1-4.5-.2c-1.2 .1-2.4 .2-3.6 .2l-16 0c-22.1 0-40-17.9-40-40l0-112c0-.9 0-1.9 .1-2.8l0-69.7-32 0c-18 0-32-14-32-32.1c0-9 3-17 10-24L266.4 8c7-7 15-8 22-8s15 2 21 7L564.8 231.5c8 7 12 15 11 24z"/></svg>
                <span>Accueil</span>
            </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/listeProfesseurs">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" width="24px" viewBox="0 0 448 512"><path d="M96 128a128 128 0 1 0 256 0A128 128 0 1 0 96 128zm94.5 200.2l18.6 31L175.8 483.1l-36-146.9c-2-8.1-9.8-13.4-17.9-11.3C51.9 342.4 0 405.8 0 481.3c0 17 13.8 30.7 30.7 30.7l131.7 0c0 0 0 0 .1 0l5.5 0 112 0 5.5 0c0 0 0 0 .1 0l131.7 0c17 0 30.7-13.8 30.7-30.7c0-75.5-51.9-138.9-121.9-156.4c-8.1-2-15.9 3.3-17.9 11.3l-36 146.9L238.9 359.2l18.6-31c6.4-10.7-1.3-24.2-13.7-24.2L224 304l-19.7 0c-12.4 0-20.1 13.6-13.7 24.2z"/></svg>
                <span>Liste professeurs</span>
            </a>
        </li>
        <li class="active"><a href="<%= request.getContextPath() %>/listeEtudiants">
            <svg xmlns="http://www.w3.org/2000/svg" height="24px" width="24px" viewBox="0 0 640 512"><path d="M144 0a80 80 0 1 1 0 160A80 80 0 1 1 144 0zM512 0a80 80 0 1 1 0 160A80 80 0 1 1 512 0zM0 298.7C0 239.8 47.8 192 106.7 192l42.7 0c15.9 0 31 3.5 44.6 9.7c-1.3 7.2-1.9 14.7-1.9 22.3c0 38.2 16.8 72.5 43.3 96c-.2 0-.4 0-.7 0L21.3 320C9.6 320 0 310.4 0 298.7zM405.3 320c-.2 0-.4 0-.7 0c26.6-23.5 43.3-57.8 43.3-96c0-7.6-.7-15-1.9-22.3c13.6-6.3 28.7-9.7 44.6-9.7l42.7 0C592.2 192 640 239.8 640 298.7c0 11.8-9.6 21.3-21.3 21.3l-213.3 0zM224 224a96 96 0 1 1 192 0 96 96 0 1 1 -192 0zM128 485.3C128 411.7 187.7 352 261.3 352l117.3 0C452.3 352 512 411.7 512 485.3c0 14.7-11.9 26.7-26.7 26.7l-330.7 0c-14.7 0-26.7-11.9-26.7-26.7z"/></svg>
            <span>Liste étudiants</span>
        </a>
        </li>
        <li><a href="<%= request.getContextPath() %>/listeCours">
            <svg xmlns="http://www.w3.org/2000/svg" height="24px" width="24px" viewBox="0 0 448 512"><path d="M96 0C43 0 0 43 0 96L0 416c0 53 43 96 96 96l288 0 32 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l0-64c17.7 0 32-14.3 32-32l0-320c0-17.7-14.3-32-32-32L384 0 96 0zm0 384l256 0 0 64L96 448c-17.7 0-32-14.3-32-32s14.3-32 32-32zm32-240c0-8.8 7.2-16 16-16l192 0c8.8 0 16 7.2 16 16s-7.2 16-16 16l-192 0c-8.8 0-16-7.2-16-16zm16 48l192 0c8.8 0 16 7.2 16 16s-7.2 16-16 16l-192 0c-8.8 0-16-7.2-16-16s7.2-16 16-16z"/></svg>            <span>Attribuer cours</span>
        </a>
        </li>
        <li><a href="<%= request.getContextPath() %>/listeInscriptions">
            <svg xmlns="http://www.w3.org/2000/svg" height="24px" width="24px" viewBox="0 0 448 512"><path d="M128 0c13.3 0 24 10.7 24 24l0 40 144 0 0-40c0-13.3 10.7-24 24-24s24 10.7 24 24l0 40 40 0c35.3 0 64 28.7 64 64l0 16 0 48 0 256c0 35.3-28.7 64-64 64L64 512c-35.3 0-64-28.7-64-64L0 192l0-48 0-16C0 92.7 28.7 64 64 64l40 0 0-40c0-13.3 10.7-24 24-24zM400 192L48 192l0 256c0 8.8 7.2 16 16 16l320 0c8.8 0 16-7.2 16-16l0-256zM329 297L217 409c-9.4 9.4-24.6 9.4-33.9 0l-64-64c-9.4-9.4-9.4-24.6 0-33.9s24.6-9.4 33.9 0l47 47 95-95c9.4-9.4 24.6-9.4 33.9 0s9.4 24.6 0 33.9z"/></svg>            <span>Inscrire étudiant</span>
        </a>
        </li>
        <li>
            <a href="<%= request.getContextPath() %>/logout">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" width="24px" viewBox="0 0 512 512"><path d="M288 32c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 224c0 17.7 14.3 32 32 32s32-14.3 32-32l0-224zM143.5 120.6c13.6-11.3 15.4-31.5 4.1-45.1s-31.5-15.4-45.1-4.1C49.7 115.4 16 181.8 16 256c0 132.5 107.5 240 240 240s240-107.5 240-240c0-74.2-33.8-140.6-86.6-184.6c-13.6-11.3-33.8-9.4-45.1 4.1s-9.4 33.8 4.1 45.1c38.9 32.3 63.5 81 63.5 135.4c0 97.2-78.8 176-176 176s-176-78.8-176-176c0-54.4 24.7-103.1 63.5-135.4z"/></svg>
                <span>Se déconnecter</span>
            </a>
        </li>
    </ul>
</nav>

<!-- Main Content -->
<main>
    <header>
        <h1>Liste des Étudiants</h1>
    </header>

    <!-- Students Table -->
    <div class="container">
        <!-- Search Bar -->
        <div class="search-bar">
            <form action="<%= request.getContextPath() %>/listeEtudiants" method="get">
                <input style="width: 88%;" type="text" name="searchQuery" placeholder="Rechercher par Nom, Prénom, Adresse, Email, Classe ou Filière" value="<%= request.getParameter("searchQuery") != null ? request.getParameter("searchQuery") : "" %>">
                <button type="submit">Rechercher</button>
            </form>
        </div>
        <table>
            <thead>
            <tr>
                <th>ID Étudiant</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Adresse</th>
                <th>Email</th>
                <th>Contact</th>
                <th>Classe</th>
                <th>Filière</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiantList");
                String searchQuery = request.getParameter("searchQuery");

                if (etudiants != null && !etudiants.isEmpty()) {
                    boolean hasResults = false; // Flag to check if there are matching results
                    for (Etudiant etudiant : etudiants) {
                        boolean matchesSearch = searchQuery == null || searchQuery.isEmpty() ||
                                etudiant.getUtilisateur().getNom().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                etudiant.getUtilisateur().getPrenom().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                etudiant.getUtilisateur().getAdresse().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                etudiant.getUtilisateur().getEmail().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                etudiant.getClasse().toString().toLowerCase().contains(searchQuery.toLowerCase()) ||
                                etudiant.getFiliere().toString().toLowerCase().contains(searchQuery.toLowerCase());

                        if (matchesSearch) {
                            hasResults = true;
            %>
            <tr>
                <td><%= etudiant.getIdEtudiant() %></td>
                <td><%= etudiant.getUtilisateur().getNom() %></td>
                <td><%= etudiant.getUtilisateur().getPrenom() %></td>
                <td><%= etudiant.getUtilisateur().getAdresse() %></td>
                <td><%= etudiant.getUtilisateur().getEmail() %></td>
                <td><%= etudiant.getContact() %></td>
                <td><%= etudiant.getClasse() %></td>
                <td><%= etudiant.getFiliere() %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/supprimerEtudiant?id=<%= etudiant.getIdEtudiant() %>"
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?')">Supprimer</a>
                </td>
            </tr>
            <%
                    }
                }

                if (!hasResults) {
            %>
            <tr>
                <td colspan="9">Aucun étudiant ne correspond à votre recherche.</td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="9">Aucun étudiant trouvé.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <form action="<%= request.getContextPath() %>/telechargerEtudiantsCSV" method="get">
            <button type="submit">Télécharger la liste des étudiants (CSV)</button>
        </form>
    </div>
    <footer>
        <p>© 2024 - Gestion Scolaire</p>
    </footer>
</main>

</body>
</html>
