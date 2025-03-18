package modele;

import controler.Conv;
import controler.ConvDuo;
import controler.Message;

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
        String insertParticipationQuery = "INSERT INTO Participation (UPseudo, CID, DateEntree) VALUES (?, ?, CURRENT_DATE)";
    
        try (Connection con = ds.getConnection()) {
            this.create(conv);
    
            try (PreparedStatement psDuo = con.prepareStatement(insertDuoQuery)) {
                psDuo.setString(1, conv.getCno());
                psDuo.setString(2, user1);
                psDuo.setString(3, user2);
                psDuo.executeUpdate();
            }

            try (PreparedStatement psPart = con.prepareStatement(insertParticipationQuery)) {
                psPart.setString(1, user1);
                psPart.setString(2, conv.getCno());
                psPart.executeUpdate();
            }


            try (PreparedStatement psPart = con.prepareStatement(insertParticipationQuery)) {
                psPart.setString(1, user2);
                psPart.setString(2, conv.getCno());
                psPart.executeUpdate();
            }
    
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }


    }

    public String findDuoConversationID(String user1, String user2) {
        String query = "SELECT CID FROM ConversationDuo " +
                       "WHERE (User1 = ? AND User2 = ?) OR (User1 = ? AND User2 = ?)";
    
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {

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

    public void deleteDuoConversation(String upseudo1, String upseudo2) {
        String queryDeleteDuo = "DELETE FROM ConversationDuo WHERE (User1 = ? AND User2 = ?) OR (User1 = ? AND User2 = ?)";
        String queryDeleteConversation = "DELETE FROM Conversation WHERE CID IN (SELECT CID FROM ConversationDuo WHERE (User1 = ? AND User2 = ?) OR (User1 = ? AND User2 = ?))";
    
        try (Connection con = ds.getConnection()) {
            try (PreparedStatement psDuo = con.prepareStatement(queryDeleteDuo)) {
                psDuo.setString(1, upseudo1);
                psDuo.setString(2, upseudo2);
                psDuo.setString(3, upseudo2);  
                psDuo.setString(4, upseudo1); 
                psDuo.executeUpdate();
            }
            
            try (PreparedStatement psConv = con.prepareStatement(queryDeleteConversation)) {
                psConv.setString(1, upseudo1);
                psConv.setString(2, upseudo2);
                psConv.setString(3, upseudo2);  
                psConv.setString(4, upseudo1);  
                psConv.executeUpdate();
            }

            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL lors de la suppression de la conversation duo : " + e.getMessage());
        }
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
        String query="Delete FROM Conversation WHERE cid = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            int rs = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public void createGroupConversation(Conv conv, String creator, List<String> users) {
        String insertConvQuery = "INSERT INTO Conversation (CID, Titre, Privee, Duo, Owner, DateCreation) VALUES (?, ?, ?, ?, ?, ?)";
        String insertParticipationQuery = "INSERT INTO Participation (UPseudo, CID, DateEntree) VALUES (?, ?, CURRENT_DATE)";

        try (Connection con = ds.getConnection()) {
            try (PreparedStatement psConv = con.prepareStatement(insertConvQuery)) {
                psConv.setString(1, conv.getCno());
                psConv.setString(2, conv.getTitre());
                psConv.setBoolean(3, conv.isPrivee());
                psConv.setBoolean(4, conv.isDuo());
                psConv.setString(5, creator);
                psConv.setDate(6, Date.valueOf(conv.getDate()));
                psConv.executeUpdate();
            }

            try (PreparedStatement psPart = con.prepareStatement(insertParticipationQuery)) {
                for (String user : users) {
                    psPart.setString(1, user);
                    psPart.setString(2, conv.getCno());
                    psPart.executeUpdate();
                }
            }

            try (PreparedStatement psPart = con.prepareStatement(insertParticipationQuery)) {
                psPart.setString(1, creator);
                psPart.setString(2, conv.getCno());
                psPart.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la création de la conversation de groupe : " + e.getMessage());
        }
    }

    public List<Message> findMessagesByConversationId(String cid) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM Message WHERE CID = ?";
    
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cid);
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                Message message = new Message(
                    rs.getString("MID"),
                    rs.getString("Contenue"),
                    rs.getDate("DateEnvoie").toLocalDate(),
                    rs.getString("Auteur"),
                    rs.getBytes("Image")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    
        return messages;
    }
    

    public void updateConversation(String convId, String newName, Boolean newPrivacy) {
        String query = "UPDATE Conversation SET titre = ?, privee = ? WHERE cid = ?";
        try (Connection con = ds.getConnection();PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setBoolean(2, newPrivacy);
            stmt.setString(3, convId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
