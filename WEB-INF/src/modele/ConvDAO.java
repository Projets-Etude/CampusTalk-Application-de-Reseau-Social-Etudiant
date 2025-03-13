package modele;

import controler.Conv;
import controler.ConvDuo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

public class ConvDAO {
    private DS ds;

    public ConvDAO(){
        ds = new DS();
    }

    public void create(Conv conv){
        String query="INSERT INTO Conversation values (?,?,?,?,?,?)";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, conv.getCno());
            ps.setString(2, conv.getTitre());
            ps.setBoolean(3, conv.isPrivee());
            ps.setBoolean(4, conv.isDuo());
            ps.setString(5, conv.getOwner());
            ps.setDate(6, Date.valueOf(conv.getDate()));
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public void createDuoConversation(Conv conv, String user1, String user2) {
        String insertDuoQuery = "INSERT INTO ConversationDuo (CID, User1, User2) VALUES (?, ?, ?)";
    
        try (Connection con = ds.getConnection()) {
            this.create(conv);
    
            try (PreparedStatement psDuo = con.prepareStatement(insertDuoQuery)) {
                psDuo.setString(1, conv.getCno());
                psDuo.setString(2, user1);
                psDuo.setString(3, user2);
                psDuo.executeUpdate();
            }
    
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public String findDuoConversationID(String user1, String user2) {
        String query = "SELECT CID FROM ConversationDuo " +
                       "WHERE (User1 = ? AND User2 = ?) OR (User1 = ? AND User2 = ?)";
    
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            // Paramétrer les utilisateurs
            ps.setString(1, user1);
            ps.setString(2, user2);
            ps.setString(3, user2);
            ps.setString(4, user1);
    
            ResultSet rs = ps.executeQuery();
    
            if (rs.next()) {
                return rs.getString("CID");
            }
    
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    
        return null;
    }
    

    public List<Conv> findAll() {
        List<Conv> convs = new ArrayList<>();
        String query = "SELECT * FROM Conversation";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Conv conv = new Conv(
                        rs.getString("cid"),
                        rs.getString("titre"),
                        rs.getBoolean("privee"),
                        rs.getBoolean("duo"),
                        rs.getString("owner"),
                        rs.getDate("datecreation").toLocalDate()
                );
                convs.add(conv);
            }

            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }

        return convs;
    }

    public List<ConvDuo> findAllDuo() {
        List<ConvDuo> convDuoList = new ArrayList<>();
        String query = "SELECT cd.CID, cd.User1, cd.User2 " +
                    "FROM ConversationDuo as cd " +
                    "INNER JOIN Conversation c ON c.CID = cd.CID " +
                    "WHERE c.Duo = TRUE";

        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cid = rs.getString("CID");
                String upseudo1 = rs.getString("User1");
                String upseudo2 = rs.getString("User2");

                ConvDuo convDuo = new ConvDuo(cid, upseudo1, upseudo2);

                convDuoList.add(convDuo);
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

        return convDuoList;
    }

    

    public Conv findByID(String id){
        Conv conv = null;
        String query="SELECT * FROM Conversation WHERE cid = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                conv = new Conv(rs.getString("cid"), rs.getString("titre"), rs.getBoolean("privee"), rs.getBoolean("duo"), rs.getString("owner"), rs.getDate("datecreation").toLocalDate());
            }
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
        return conv;
    }

    public List<Conv> findByOwner(String owner) {
        List<Conv> convs = new ArrayList<>();
        String query = "SELECT * FROM Conversation WHERE Owner = ?";
    
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, owner);
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                Conv conv = new Conv(
                        rs.getString("CID"),
                        rs.getString("Titre"),
                        rs.getBoolean("Privee"),
                        rs.getBoolean("Duo"),
                        rs.getString("Owner"),
                        rs.getDate("DateCreation").toLocalDate()
                );
                convs.add(conv);
            }
    
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        Collections.reverse(convs);
        return convs;
    }

    @SuppressWarnings("unused")
    public void deleteById(String id){
        Conv conv = null;
        String query="Delete * FROM Conversation WHERE cno = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            int rs = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
