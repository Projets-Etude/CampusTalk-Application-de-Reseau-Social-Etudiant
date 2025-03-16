<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WhatsUp</title>
    <link rel="stylesheet" type="text/css" href="accueil.css?version=51">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;500;600&display=swap" rel="stylesheet">
    <%@ page import="modele.UserDAO, java.util.*, controler.User,controler.ConvDuo, modele.PosteDAO, controler.Poste, modele.AbonnementDAO, controler.Abonnement, modele.ConvDAO, controler.Conv, modele.LikePosteDAO" %>
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
    <header class="navbar">
        <div class="logo">CampusTalk</div>
        <nav>
            <label for="modal-messages">Conversations</label>
            <label for="modal-profil">Profil</label>
        </nav>
    </header>


    <input type="radio" id="close-modal" name="modal">


    <input type="radio" id="modal-messages" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2>Conversations</h2>
            <div class="div-conversations">
            <%
            if(user != null){
                ConvDAO convDAO = new ConvDAO();
                List<Conv> listConv = convDAO.findAll();
                List<ConvDuo> listConvDuo = convDAO.findAllDuo();
                AbonnementDAO abonnementDAO = new AbonnementDAO();

                for(ConvDuo convDuo: listConvDuo){
                    if(user.getUPseudo().equals(convDuo.getUPseudo1()) || user.getUPseudo().equals(convDuo.getUPseudo2())){
                        out.println(convDuo.toString());
                    }
                }
                for(Conv conv : listConv){
                    if(abonnementDAO.exists(conv.getOwner(),user.getUPseudo()) && conv.isPrivee() && !conv.isDuo()){
                        out.println(conv.toString());
                    }
                    if((conv.isPrivee() && !user.getUPseudo().equals(conv.getOwner())) || conv.isDuo()){
                        continue;
                    }
                    if(!conv.isDuo()){
                        out.println(conv.toString());
                    }
                    
                }
            }
            %>
            </div>
            <label for="modal-create-conv" class="create-new-conv">Créer une conversation</label>
        </div>
    </div>

    <input type="radio" id="modal-create-conv" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2>Créer une conversation</h2>
            <form action="/sae-s4.a02.1/createConversation" method="get" class="conversation-creation">
                <label for="conv-name">Nom de la conversation</label>
                <input type="text" id="conv-name" name="conv-name" required>

                <label for="conv-name">Personnes à ajouter</label>
                <div class="user-list">
                    <%
                        if (user != null) {
                            AbonnementDAO abonnementDAO = new AbonnementDAO();
                            List<Abonnement> listAbonnement = abonnementDAO.findByUserPseudo(user.getUPseudo());

                            for (Abonnement abo : listAbonnement) {
                                String pseudo = abo.getUtilisateur1().equals(user.getUPseudo()) ? abo.getUtilisateur2() : abo.getUtilisateur1();
                    %>
                    <label>
                        <input type="checkbox" name="abonnes" value="<%= pseudo %>"> <%= pseudo %>
                    </label><br>
                    <%
                            }
                        }
                    %>
                </div>

                <label for="conv-private">
                    <input type="checkbox" id="conv-private" name="conv-private">
                    Conversation privée
                </label>

                <button type="submit">Créer</button>
            </form>
        </div>
    </div>

    <input type="radio" id="modal-profil" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2>Profil</h2>
            <form method="post" class="profilForm" action="/sae-s4.a02.1/modification" enctype="multipart/form-data">
            <%
                if(user != null){
                out.println("<label for=\"pseudo\">Pseudo (Actuel : "+ user.getUPseudo() +")</label>");
                out.println("<input type=\"text\" id=\"pseudo\" name=\"pseudo\" placeholder=\"Entrez votre Pseudo\">");
                out.println("<label for=\"email\">Email (Actuel : "+ user.getEmail() +")</label>");
                out.println("<input type=\"email\" id=\"email\" name=\"email\" placeholder=\"Entrez votre Email\">");
                out.println("<label for=\"password\">Mot de passe (Actuel : "+ user.getMdp() +")</label>");
                out.println("<input type=\"password\" id=\"mdp\" name=\"mdp\" placeholder=\"Nouveau Mot De Passe\">");
                out.println("<label for=\"prenom\">Prénom (Actuel : "+ user.getPrenom() +")</label>");
                out.println("<input type=\"text\" id=\"nom\" name=\"prenom\" placeholder=\"Entrez votre Prenom\">");
                out.println("<label for=\"nom\">Nom (Actuel : "+ user.getNom() +")</label>");
                out.println("<input type=\"text\" id=\"prenom\" name=\"nom\" placeholder=\"Entrez votre Nom\">");
                out.println("<label for=\"anniv\">Anniversaire (Actuelle : "+ user.getAnniversaire().toString() +")</label>");
                out.println("<input type=\"date\" id=\"anniv\" name=\"anniv\" placeholder=\"Nouvelle Date de Naissance\">");
                out.println("<label for=\"description\">Description</label>");
                out.println("<textarea type=\"text\" id=\"description\" name=\"description\" rows=\"5\" placeholder=\"Entrez votre description\"> </textarea>");
                if (user.getPhoto() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(user.getPhoto());
                    out.println("<label>Photo de Profil Actuelle :</label>");
                    out.println("<img src='data:image/png;base64," + base64Image + "' alt='Photo de Profil' style='max-width: 200px; display:block;'>");
                }

                out.println("<label for=\"photo\">Modifier l'image de profil :</label>");
                out.println("<input type=\"file\" id=\"photo\" name=\"photo\" accept=\"image/*\">");
            }
            %>
                <button type="submit">Enregistrer</button>
            </form>
        </div>
    </div>


    <div class="conteneur-centrer">
    <div class="div-actualites">
        <header class="welcome">
            <%
            if(user != null){
                out.println("<div>Bienvenue "+user.getUPseudo() +" !</div>");
            }
            %>
        </header>

        <div class="content-container">
            <div class="abonnement">
                <header class="titreListeAbo">
                    <div>Liste Abonnements</div>
                </header>

                <%
                    if(user != null){
                        AbonnementDAO abonnementDAO = new AbonnementDAO();
                        List<Abonnement> listAbonnement = abonnementDAO.findByUserPseudo(user.getUPseudo());
                        for(Abonnement abo : listAbonnement){
                            out.println(abo.toString(user.getUPseudo()));
                        }
                    }
                    
                    %>
                
            </div>
            <div class="fil">
                <header class="titreFilActu">
                    <div>Fil d'actualites</div>
                </header>
                
                <%
                if(user != null){
                    PosteDAO posteDAO = new PosteDAO();
                    List<Poste> listPoste = posteDAO.findAll();
                    AbonnementDAO abonnementDAO = new AbonnementDAO();
                    LikePosteDAO likePosteDAO = new LikePosteDAO();
                    

                    for(Poste poste : listPoste){
                        boolean exists = abonnementDAO.exists(poste.getUPseudo(), user.getUPseudo());
                        if(exists && poste.isVisibiliteRestreinte()){
                            //BOUTON SUIVI
                            out.println(poste.getPosteSuivi(user.getUPseudo(),likePosteDAO));
                        }
                        if(!poste.getUPseudo().equals(user.getUPseudo()) && poste.isVisibiliteRestreinte()) {
                            continue;
                        } else if(poste.getUPseudo().equals(user.getUPseudo())) {
                            // CURRENT UTILISATEUR DONC PAS BOUTON SUIVRE
                            out.println(poste.getPosteCurrentUser(user.getUPseudo(),likePosteDAO));
                        } else if(exists) {
                            // SUIVRE UN UTILISATEUR
                            out.println(poste.getPosteSuivre(user.getUPseudo(),likePosteDAO));
                        } else {
                            out.println(poste.getPosteSuivreElse(user.getUPseudo(),likePosteDAO));
                        }
                    }
                }
                %>
            </div>
        </div>

        <form action="/sae-s4.a02.1/createPoste" method="post" class="message-form">
            <textarea name="contenu" placeholder="Écrire un post..." required></textarea>
    
            <label for="prive">
                <input type="checkbox" id="prive" name="prive">
                Privé
            </label>

            <button type="submit">Envoyer</button>
        </form>

    </div>
</div>

    

</body>

</html>


