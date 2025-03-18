<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WhatsUp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="accueil.css?version=51">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;500;600&display=swap" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <%@ page import="modele.UserDAO, java.util.*,modele.ParticipationDAO, modele.PosteDAO, modele.AbonnementDAO, modele.ConvDAO, modele.LikePosteDAO" %>
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

    %>
    <header class="navbar navbar-expand-lg" style="background-color: #3D8D7A; padding: 10px;">
        <div class="container-fluid">
            <a class="navbar-brand text-white fw-bold" href="#">CampusTalk</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <label class="nav-link text-white" for="modal-messages">Conversations</label>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="/sae-s4.a02.1/conversations">API</a>
                    </li>
                    <li class="nav-item">
                        <label class="nav-link text-white" for="modal-profil">Profil</label>
                    </li>
                    <li class="nav-item">
                        <form action="Logout" method="post" class="d-inline" style="display: inline;">
                            <button type="submit" class="btn" style="background-color: #e74c3c; color: white; border: none; padding: 8px 16px; border-radius: 5px;">Déconnexion</button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </header>

    <input type="radio" id="close-modal" name="modal">


    <input type="radio" id="modal-messages" name="modal">
    <div class="modal">
        <div class="modal-content">
            <button class="close-btn" onclick="document.getElementById('close-modal').checked = true;">&times;</button>
            <h2 class="modal-title">Conversations</h2>
            <div class="div-conversations">
                <% if(user != null){
                    ConvDAO convDAO = new ConvDAO();
                    List<Conv> listConv = convDAO.findAll();
                    List<ConvDuo> listConvDuo = convDAO.findAllDuo();
                    AbonnementDAO abonnementDAO = new AbonnementDAO();
                    ParticipationDAO participationDAO = new ParticipationDAO();

                    for(ConvDuo convDuo: listConvDuo){
                        if(user.getUPseudo().equals(convDuo.getUPseudo1()) || user.getUPseudo().equals(convDuo.getUPseudo2())){
                            out.println("<div class='conversation-card duo'>" + convDuo.toString() + "</div>");
                        }
                    }
                    for(Conv conv : listConv){
                        if(abonnementDAO.exists(conv.getOwner(),user.getUPseudo()) && conv.isPrivee() && !conv.isDuo() && participationDAO.isParticipating(conv.getCno(),user)){
                            out.println("<div class='conversation-card private'>" + conv.toString() + "</div>");
                        }
                        if((conv.isPrivee() && !user.getUPseudo().equals(conv.getOwner())) || conv.isDuo()){
                            continue;
                        }
                        if(!conv.isDuo()){
                            out.println("<div class='conversation-card public'>" + conv.toString() + "</div>");
                        }
                    }
                } %>
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
                <input type="text" id="conv-name" name="conv-name" required class="form-control">

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
                    <% }
                    } %>
                </div>

                <label for="conv-private">
                    <input type="checkbox" id="conv-private" name="conv-private">
                    Conversation privée
                </label>

                <button type="submit" class="btn btn-primary">Créer</button>
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
                        out.println("<input type=\"text\" id=\"pseudo\" name=\"pseudo\" placeholder=\"Entrez votre Pseudo\" class=\"form-control\">");
                        out.println("<label for=\"email\">Email (Actuel : "+ user.getEmail() +")</label>");
                        out.println("<input type=\"email\" id=\"email\" name=\"email\" placeholder=\"Entrez votre Email\" class=\"form-control\">");
                        out.println("<label for=\"password\">Mot de passe (Actuel : "+ user.getMdp() +")</label>");
                        out.println("<input type=\"password\" id=\"mdp\" name=\"mdp\" placeholder=\"Nouveau Mot De Passe\" class=\"form-control\">");
                        out.println("<label for=\"prenom\">Prénom (Actuel : "+ user.getPrenom() +")</label>");
                        out.println("<input type=\"text\" id=\"prenom\" name=\"prenom\" placeholder=\"Entrez votre Prenom\" class=\"form-control\">");
                        out.println("<label for=\"nom\">Nom (Actuel : "+ user.getNom() +")</label>");
                        out.println("<input type=\"text\" id=\"nom\" name=\"nom\" placeholder=\"Entrez votre Nom\" class=\"form-control\">");
                        out.println("<label for=\"anniv\">Anniversaire (Actuelle : "+ user.getAnniversaire().toString() +")</label>");
                        out.println("<input type=\"date\" id=\"anniv\" name=\"anniv\" placeholder=\"Nouvelle Date de Naissance\" class=\"form-control\">");
                        out.println("<label for=\"description\">Description</label>");
                        out.println("<textarea type=\"text\" id=\"description\" name=\"description\" rows=\"5\" placeholder=\"Entrez votre description\" class=\"form-control\"> </textarea>");

                        String photoPath = ImageUtils.getProfileImagePath(user);

                        out.println("<label>Photo de Profil Actuelle :</label>");
                        out.println("<img src='" + photoPath + "' alt='Photo de Profil' style='max-width: 200px; display:block;'>");

                        out.println("<label for=\"photo\">Modifier l'image de profil :</label>");
                        out.println("<input type=\"file\" id=\"photo\" name=\"photo\" accept=\"image/*\" class=\"form-control\">");

                        out.println("<label>\n" +
                                "        <input type=\"checkbox\" name=\"supprimerPhoto\" value=\"true\"> Supprimer ma photo de profil\n" +
                                "    </label>");
                    }
                %>
                <button type="submit" class="btn btn-success">Enregistrer</button>
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
                                boolean isFollowing = abonnementDAO.exists(poste.getUPseudo(), user.getUPseudo());
                                boolean isCurrentUser = poste.getUPseudo().equals(user.getUPseudo());
                                boolean canFollow = !isCurrentUser && !isFollowing;

                                if(poste.isVisibiliteRestreinte() && !isFollowing &&!isCurrentUser) {
                                    continue;
                                }

                                out.println(poste.getPosteHTML(user.getUPseudo(), likePosteDAO, isFollowing, isCurrentUser, canFollow));
                            }
                        }
                    %>
                </div>
            </div>

            <form action="/sae-s4.a02.1/createPoste" method="post" class="message-form">
                <div class="form-group">
                    <textarea name="contenu" placeholder="Écrire un post..." required class="form-control"></textarea>
                </div>

                <div class="form-check">
                    <input type="checkbox" id="prive" name="prive" class="form-check-input">
                    <label for="prive" class="form-check-label">Privé</label>
                </div>

                <button type="submit" class="btn btn-primary">Envoyer</button>
            </form>
        </div>
    </div>

    </div>



</body>

</html>


