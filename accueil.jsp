<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WhatsUp</title>
    <link rel="stylesheet" href="accueil.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;500;600&display=swap" rel="stylesheet">
    <%@ page import="modele.UserDAO, java.util.*, controler.User" %>
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
       
    %>
    <div class="rain">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
    </div>
    <header class="navbar">
        <div class="logo">WhatsUp</div>
        <nav>
            <label for="modal-accueil">Accueil</label>
            <label for="modal-messages">Messages</label>
            <label for="modal-abonnement">Abonnement</label>
            <label for="modal-profil">Profil</label>
        </nav>
    </header>


    <input type="radio" id="close-modal" name="modal">


    <input type="radio" id="modal-messages" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2>Messages</h2>
            <p>Consultez vos messages ici.</p>
        </div>
    </div>

    <input type="radio" id="modal-abonnement" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2>Abonnement</h2>
            <p>Gérez votre abonnement.</p>
        </div>
    </div>

    <input type="radio" id="modal-profil" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2>Profil</h2>
            <form class="profilForm" action="/sae-s4.a02.1/modification">
            <%
                if(user != null){
                out.println("<label for=\"pseudo\">Pseudo (Actuel : "+ user.getUPseudo() +")</label>");
                out.println("<input type=\"text\" id=\"pseudo\" name=\"pseudo\" placeholder=\"Entrez votre Pseudo\">");
                out.println("<label for=\"email\">Email (Actuel : "+ user.getEmail() +")</label>");
                out.println("<input type=\"email\" id=\"email\" name=\"email\" placeholder=\"Entrez votre Email\">");
                out.println("<label for=\"password\">Mot de passe (Actuel : "+ user.getMdp() +")</label>");
                out.println("<input type=\"password\" id=\"mdp\" name=\"mdp\" placeholder=\"Nouveau Mot De Passe\">");
                out.println("<label for=\"prenom\">Prénom (Actuel : "+ user.getPrenom() +")</label>");
                out.println("<input type=\"text\" id=\"nom\" name=\"nom\" placeholder=\"Entrez votre Prenom\">");
                out.println("<label for=\"nom\">Nom (Actuel : "+ user.getNom() +")</label>");
                out.println("<input type=\"text\" id=\"prenom\" name=\"prenom\" placeholder=\"Entrez votre Nom\">");
                out.println("<label for=\"anniv\">Anniversaire (Actuelle : "+ user.getAnniversaire().toString() +")</label>");
                out.println("<input type=\"date\" id=\"anniv\" name=\"anniv\" placeholder=\"Nouvelle Date de Naissance\">");
                out.println("<label for=\"description\">Description</label>");
                out.println("<textarea type=\"text\" id=\"description\" name=\"description\" rows=\"5\" placeholder=\"Entrez votre description\"> </textarea>");
                }
                
            %>

                <button type="submit">Enregistrer</button>
            </form>
        </div>
    </div>


    <p>test</p>

    

</body>

</html>


