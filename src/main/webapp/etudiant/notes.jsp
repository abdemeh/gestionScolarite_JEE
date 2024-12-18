<%@ page import="java.util.List" %>
<%@ page import="modele.Note" %>
<%@ page import="modele.InscriptionCours" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="dao.EtudiantDAO" %>
<%@ page import="modele.Etudiant" %>
<%
    // Session verification
    String userRole = (String) session.getAttribute("user");
    if (userRole == null || !"etudiant".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return; // Ensure the rest of the page is not processed
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Notes</title>
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
        <li class="active">
            <a href="loginController">
                <svg xmlns="http://www.w3.org/2000/svg" height="24px" width="24px" viewBox="0 0 576 512"><path d="M575.8 255.5c0 18-15 32.1-32 32.1l-32 0 .7 160.2c0 2.7-.2 5.4-.5 8.1l0 16.2c0 22.1-17.9 40-40 40l-16 0c-1.1 0-2.2 0-3.3-.1c-1.4 .1-2.8 .1-4.2 .1L416 512l-24 0c-22.1 0-40-17.9-40-40l0-24 0-64c0-17.7-14.3-32-32-32l-64 0c-17.7 0-32 14.3-32 32l0 64 0 24c0 22.1-17.9 40-40 40l-24 0-31.9 0c-1.5 0-3-.1-4.5-.2c-1.2 .1-2.4 .2-3.6 .2l-16 0c-22.1 0-40-17.9-40-40l0-112c0-.9 0-1.9 .1-2.8l0-69.7-32 0c-18 0-32-14-32-32.1c0-9 3-17 10-24L266.4 8c7-7 15-8 22-8s15 2 21 7L564.8 231.5c8 7 12 15 11 24z"/></svg>
                <span>Accueil</span>
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
    <header class="page-header">
        <%
            Etudiant e= new EtudiantDAO().getEtudiantById(Integer.valueOf(session.getAttribute("id_etudiant").toString()));
            String etudiantFullName = e.getUtilisateur().getNom()+" "+e.getUtilisateur().getPrenom();
            // Si le nom n'existe pas dans la session, afficher un message par défaut
            if (etudiantFullName == null || etudiantFullName.isEmpty()) {
                etudiantFullName = "étudiant"; // Valeur par défaut
            }
        %>
        <h1>Bienvenue <%= etudiantFullName %>!</h1>
    </header>

    <!-- Course Registration Table -->
    <div class="container calendar-container">
        <h2>Calendrier des Cours</h2>
        <div class="calendar">
            <%
                // Prepare date formatter
                DateTimeFormatter frenchDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                // Retrieve courses and display them in a calendar layout
                List<InscriptionCours> inscriptionCours = (List<InscriptionCours>) request.getAttribute("inscriptionCours");

                if (inscriptionCours != null && !inscriptionCours.isEmpty()) {
                    for (InscriptionCours inscription : inscriptionCours) {
                        LocalDateTime debutCours = inscription.getDebutCours();
                        LocalDateTime finCours = inscription.getFinCours();
            %>
            <div class="calendar-item">
                <div class="course-date">
                    <span><%= debutCours.format(frenchDateTimeFormatter) %> &#8594; <%= finCours.format(frenchDateTimeFormatter) %></span>
                </div>
                <div class="course-details">
                    <h3><%= inscription.getCours().getNomCours() %></h3>
                    <p><strong>Enseignant:</strong> <%= inscription.getCours().getEnseignant().getUtilisateur().getNom() %> <%= inscription.getCours().getEnseignant().getUtilisateur().getPrenom() %></p>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p>Aucune information de cours disponible.</p>
            <%
                }
            %>
        </div>
    </div>

    <!-- Notes Table -->
    <div class="container">
        <h2>Notes</h2>
        <table>
            <thead>
            <tr>
                <th>Nom du Cours</th>
                <th>Note</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Note> notes = (List<Note>) request.getAttribute("notes");
                java.math.BigDecimal totalNotes = java.math.BigDecimal.ZERO; // Initialize BigDecimal for total
                int noteCount = 0;

                if (notes != null && !notes.isEmpty()) {
                    for (Note note : notes) {
                        totalNotes = totalNotes.add(note.getNote()); // Use BigDecimal's add method
                        noteCount++;
            %>
            <tr>
                <td><%= note.getCours().getNomCours() %></td>
                <td><%= note.getNote() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="2">Aucune note disponible.</td>
            </tr>
            <%
                }
                // Calculate average
                java.math.BigDecimal average = (noteCount > 0) ? totalNotes.divide(java.math.BigDecimal.valueOf(noteCount), 2, java.math.RoundingMode.HALF_UP) : java.math.BigDecimal.ZERO;
            %>
            </tbody>
        </table>

        <%-- Display the average below the table --%>
        <%
            if (noteCount > 0) {
        %>
        <h2>Résultat :</h2>
        <div class="average-result">
            <p><strong>Moyenne des notes :</strong> <%= average.toString() %>/20</p>
        </div>
        <%
            }
        %>
        <form action="<%= request.getContextPath() %>/telechargerReleveNotes" method="get">
            <button type="submit" class="download-button">Télécharger relevé de notes</button>
        </form>
    </div>

    </div>
</main>

<!-- Footer -->
<footer>
    <p>© 2024 - Gestion Scolaire</p>
</footer>
</body>
</html>
