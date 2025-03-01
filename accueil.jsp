<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WhatsUp</title>
    <link rel="stylesheet" href="accueil.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@400;500;600&display=swap" rel="stylesheet">
    <%@ page import="modele.UserDAO, java.util.*, controler.User, modele.PosteDAO, controler.Poste, modele.AbonnementDAO, controler.Abonnement, modele.ConvDAO, controler.Conv" %>
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
    <%-- <div class="rain">
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
    </div> --%>
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

                for(Conv conv : listConv){
                    if(conv.isPrivee() && !user.getUPseudo().equals(conv.getOwner())){
                        continue;
                    }
                    out.println(conv.toString());
                }
            }
            %>
            </div>
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

                    for(Poste poste : listPoste){
                        if(abonnementDAO.exists(poste.getUPseudo(), user.getUPseudo()) && poste.isVisibiliteRestreinte()){
                            out.println(poste.toString());
                        }
                        if(!poste.getUPseudo().equals(user.getUPseudo()) && poste.isVisibiliteRestreinte()){
                            continue;
                        }else{
                            out.println(poste.toString());
                        }
                    }
                }
                %>
            </div>
        </div>
    </div>
</div>

    

</body>

</html>


