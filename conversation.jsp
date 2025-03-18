<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conversation</title>
    <link rel="stylesheet" href="accueil.css">
    <link rel="stylesheet" href="conversation.css?version=51">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;500;600&display=swap" rel="stylesheet">
    <%@ page import="java.util.*, java.time.format.DateTimeFormatter, java.time.LocalDateTime" %>
    <%@ page import="modele.*" %>
    <%@ page import="controler.*" %>
</head>
<body>
    <%
            session = request.getSession(true);
            User login = (User) session.getAttribute("login");

            if(login == null){
                response.sendRedirect("/sae-s4.a02.1/login.html");
            }
            UserDAO userDAO = new UserDAO();
            User user = login;
            String convId = request.getParameter("id");
            if(convId == null) {
                out.println("Aucune conversation sélectionnée.");
                return;
            }
            ParticipationDAO participationDAO = new ParticipationDAO();
            ConvDAO convDAO = new ConvDAO();
            Conv conversation = convDAO.findByID(convId);
            if(conversation.isPrivee() && !participationDAO.isParticipating(convId, user)){
                response.sendRedirect("accueil.jsp");
            }
        
        %>
    <header class="navbar">
        <div class="logo">CampusTalk</div>
        <nav>
            <label for="modal-config">Configuration</label>
            <label for="modal-accueil"><a href ="accueil.jsp">Accueil</a></label>
        </nav>
    </header>

    <input type="radio" id="close-modal" name="modal">

    <input type="radio" id="modal-config" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2>Configuration</h2>

            <form method="post" class="profilForm" action="/sae-s4.a02.1/ModifConv">
                <input type="hidden" name="convId" value="<%= convId %>">

                <% if(user != null) { %>
                <label for="nomConv">Nom de la conversation (Actuel : <%= conversation.getTitre() %>)</label>
                <input type="text" id="nomConv" name="nomConv" placeholder="Entrez un nom"
                       value="<%= conversation.getTitre() %>" class="form-control input-large">

                <label for="privee">Conversation privée ? (Actuel : <%= conversation.isPrivee() ? "Oui" : "Non" %>)</label>
                <select id="privee" name="privee" class="form-control">
                    <option value="true" <%= conversation.isPrivee() ? "selected" : "" %>>Oui</option>
                    <option value="false" <%= !conversation.isPrivee() ? "selected" : "" %>>Non</option>
                </select>

                <label>Participants :</label>
                <div class="participants-list">
                    <%
                        AbonnementDAO abonnementDAO = new AbonnementDAO();
                        List<Abonnement> listAbonnement = abonnementDAO.findByUserPseudo(user.getUPseudo());
                        List<User> participants = participationDAO.findParticipantsByConversationId(convId);

                        for (Abonnement abo : listAbonnement) {
                            String pseudo = abo.getUtilisateur1().equals(user.getUPseudo()) ? abo.getUtilisateur2() : abo.getUtilisateur1();
                            boolean isParticipant = participants.stream().anyMatch(p -> p.getUPseudo().equals(pseudo));
                    %>
                    <div class="participant-item">
                        <input type="checkbox" name="participants" value="<%= pseudo %>"
                            <%= isParticipant ? "checked" : "" %> class="form-check-input">
                        <label class="form-check-label"><%= pseudo %></label>
                    </div>
                    <% } %>
                </div>
                <% } %>
                <button type="submit" class="btn btn-primary">Enregistrer</button>
            </form>
        </div>
    </div>


    <div class="conversation-main">
        <div class="conversation-box">
            <%
                out.println("<h1> Conversation : " + conversation.getTitre() + "</h1>");
            %>
            <div class="conversation-container">
                <header class="conversation-header">
                </header>
                <%
                if(user != null){

                    if (conversation != null) {
                        MessageDAO messageDAO = new MessageDAO();
                        List<Message> messages = messageDAO.findByCID(convId);
                        for (Message message : messages) {
                            // On vérifie si l'auteur du message est l'utilisateur connecté
                            String positionClass = message.getAuteur().equals(user.getUPseudo()) ? "right" : "left";
                            String bubbleClass = message.getAuteur().equals(user.getUPseudo()) ? "blue" : "";

                            String imageBase64 = message.getImageBase64();
                            String imageHtml = imageBase64.isEmpty() ? "" : "<img class='message-image' src='" + imageBase64 + "' alt='Image du message'>";

                            String formattedDate = message.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                            UserDAO uDAO = new UserDAO();
                            User curUser = uDAO.findByID(message.getAuteur());

                            String photoPath = ImageUtils.getProfileImagePath(curUser);

                            out.println("<div class='message-container " + positionClass + "'>");
                            if (positionClass.equals("left")) {
                                out.println("<div class='message-info'>");
                                out.println("<img class='profil-image' src=\"" + photoPath + "\" alt=\"" + curUser.getUPseudo() + " Avatar\">");
                                out.println("<span class='message-author'>" + message.getAuteur() + "</span>");
                                out.println("<span class='message-date'>" + formattedDate + "</span>");
                                out.println("</div>");
                                out.println("<div class='message-bubble " + bubbleClass + "'>" + message.getContenue() + imageHtml + "</div>");
                            } else {
                                out.println("<div class='message-bubble " + bubbleClass + "'>" + message.getContenue() + imageHtml + "</div>");
                                out.println("<div class='message-info'>");
                                out.println("<img class='profil-image' src=\"" + photoPath + "\" alt=\"" + curUser.getUPseudo() + " Avatar\">");
                                out.println("<span class='message-author'>" + message.getAuteur() + "</span>");
                                out.println("<span class='message-date'>" + formattedDate + "</span>");
                                out.println("</div>");
                            }
                            out.println("</div>");
                        }
                    } else {
                        out.println("Conversation non trouvée.");
                    }
                }
                %>
            </div>
            <form action="/sae-s4.a02.1/createMessage" method="post" enctype="multipart/form-data" class="message-form d-flex flex-column gap-2">
                <input type="hidden" name="cid" value="<%= convId %>">

                <textarea name="contenu" class="form-control" placeholder="Écrire un message..." rows="2"></textarea>
                <div class="file-container">
                    <input type="file" name="image" accept="image/*" id="imageInput" class="form-control">
                    <label for="imageInput" class="custom-file-label">+</label>
                    <span id="file-name"></span>
                    <button type="submit" class="btn btn-primary w-100">➤</button>
                </div>

            </form>



        </div>
        <aside class="right">
            <h1>Participants</h1>
            <div class="container-fluid">
                <div class="row justify-content-center">
                    <%
                        ParticipationDAO pDAO = new ParticipationDAO();
                        String owner = (convDAO.findByID(request.getParameter("id"))).getOwner();
                        List<User> users = pDAO.findParticipantsByConversationId(request.getParameter("id"));
                        for(User curUser : users){
                            String photoPath;
                            if (curUser.getPhoto() != null) {
                                photoPath = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(curUser.getPhoto());
                            } else {
                                photoPath = "res/avatar.png";
                            }
                    %>
                    <div class="col-12">
                        <div class="member-profile d-flex align-items-center">
                            <img class="profil-image" src="<%= photoPath %>" alt="<%= curUser.getUPseudo() %> Avatar">
                            <h3>
                                <% if(curUser.getUPseudo().equals(owner)) { %>
                                👑 <%= curUser.getUPseudo() %>
                                <% } else { %>
                                <%= curUser.getUPseudo() %>
                                <% } %>
                            </h3>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </aside>

    </div>
</body>
</html>
