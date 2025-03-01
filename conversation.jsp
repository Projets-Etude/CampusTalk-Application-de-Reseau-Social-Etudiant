<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conversation</title>
    <link rel="stylesheet" href="accueil.css">
    <link rel="stylesheet" href="conversation.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;500;600&display=swap" rel="stylesheet">
    <%@ page import="modele.UserDAO, java.util.*, controler.User, modele.PosteDAO, controler.Poste, modele.AbonnementDAO, controler.Abonnement, modele.ConvDAO, controler.Conv, modele.MessageDAO, controler.Message, java.time.format.DateTimeFormatter, java.time.LocalDateTime" %>
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
            ConvDAO convDAO = new ConvDAO();
            Conv conversation = convDAO.findByID(convId);
        
        %>
    <header class="navbar">
        <div class="logo">CampusTalk</div>
        <nav>
            <label for="modal-accueil"><a href ="accueil.jsp">Accueil</a></label>
        </nav>
    </header>

    <div class="conversation-container">
        <header class="conversation-header">
        <%
            out.println("<h1>" + conversation.getTitre() + "</h1>");
        %>
        </header>
        <% 
        if(user != null){
            
            if (conversation != null) {
                out.println("<div style=\"position:fixed;\">Conversation : " + conversation.getTitre() + "</div>");
                
                MessageDAO messageDAO = new MessageDAO();
                List<Message> messages = messageDAO.findByCID(convId);
                for (Message message : messages) {
                    // On vérifie si l'auteur du message est l'utilisateur connecté
                    String positionClass = message.getAuteur().equals(user.getUPseudo()) ? "right" : "left";
                    String bubbleClass = message.getAuteur().equals(user.getUPseudo()) ? "blue" : "";

                    String formattedDate = message.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    out.println("<div class='message-container " + positionClass + "'>");
                    out.println("<div class='message-bubble " + bubbleClass + "'>" + message.getContenue() + "</div>");
                    out.println("<span class='message-author'>" + message.getAuteur() + "</span>");
                    out.println("<span class='message-date'>" + formattedDate + "</span>");
                    out.println("</div>");
                }
            } else {
                out.println("Conversation non trouvée.");
            }
        }
        %>
        <form action="/sae-s4.a02.1/createMessage" method="post" class="message-form">
            <input type="hidden" name="cid" value="<%= convId %>">
            <textarea name="contenu" placeholder="Écrire un message..." required></textarea>
            <button type="submit">Envoyer</button>
        </form>

    </div>
</body>
</html>
