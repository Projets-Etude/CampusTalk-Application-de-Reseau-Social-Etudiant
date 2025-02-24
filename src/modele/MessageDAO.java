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

    public void create(Message message){
        String query="INSERT INTO joueur values" +
                "(?,?,?,?);";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, message.getMno());
            ps.setString(2, message.getContenue());
            ps.setDate(3, Date.valueOf(message.getDate()));
            ps.setString(4, message.getAuteur());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }

    }

}
