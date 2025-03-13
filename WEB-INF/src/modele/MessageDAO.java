package modele;

import controler.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class MessageDAO {

    private DS ds;

    public MessageDAO(){
        ds = new DS();
    }

    public Message findByID(String id){
        Message message = null;
        String query="SELECT * FROM message WHERE mno = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = new Message(rs.getString("mno"), rs.getString("contenue"), rs.getDate("date").toLocalDate(), rs.getString("auteur"));
            }
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
        return message;
    }
    
    public List<Message> findByCID(String cid) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM message WHERE CID = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cid);  // Le paramètre CID est passé ici
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Création d'un objet Message pour chaque ligne retournée par la requête
                Message message = new Message(
                    rs.getString("MID"),          // Identifiant du message
                    rs.getString("Contenue"),     // Contenu du message
                    rs.getDate("DateEnvoie").toLocalDate(),  // Date d'envoi du message
                    rs.getString("Auteur")       // Auteur du message
                );
                messages.add(message);  // Ajout du message à la liste
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return messages;  // Retourne la liste de messages trouvés
    }
    

    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM message";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(
                        rs.getString("mno"),
                        rs.getString("contenue"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("auteur")
                );
                messages.add(message);
            }
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }

        return messages;
    }

    @SuppressWarnings("unused")
    public void deleteById(String id){
        Message message = null;
        String query="Delete * FROM message WHERE mno = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            int rs = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public void create(Message message, String cid){
        String query="INSERT INTO message values (?,?,?,?,?)";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, message.getMno());
            ps.setString(2, message.getContenue());
            ps.setDate(3, Date.valueOf(message.getDate()));
            ps.setString(4, message.getAuteur());
            ps.setString(5,cid);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

    }

}
