package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controler.Conv;
import controler.Participation;
import controler.User;

public class ParticipationDAO {

    private DS ds;

    public ParticipationDAO(){
        ds = new DS();
    }

    public void create(Participation p){
        String query="INSERT INTO Participation values" +
                "(?,?);";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, p.getUtilisateur().getUPseudo());
            ps.setString(2, p.getConversation().getCno());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public List<Conv> findAll() {
        List<Conv> convs = new ArrayList<>();
        String query = "SELECT * FROM conv";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Conv conv = new Conv(
                        rs.getString("cno"),
                        rs.getString("titre"),
                        rs.getBoolean("privee"),
                        rs.getBoolean("duo"),
                        rs.getString("owner"),
                        rs.getDate("date").toLocalDate()
                );
                convs.add(conv);
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

        return convs;
    }

    public Conv findByID(String id){
        Conv conv = null;
        String query="SELECT * FROM conv WHERE cno = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                conv = new Conv(rs.getString("conv"), rs.getString("titre"), rs.getBoolean("privee"), rs.getBoolean("duo"), rs.getString("owner"), rs.getDate("date").toLocalDate());
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return conv;
    }

    @SuppressWarnings("unused")
    public void deleteById(String id){
        Conv conv = null;
        String query="Delete * FROM conv WHERE cno = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            int rs = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public List<User> findParticipantsByConversationId(String conversationId) {
        List<User> participants = new ArrayList<>();
        String query = "SELECT u.UPseudo, u.Email, u.Prenom, u.Nom, u.DateInscription, u.Anniversaire, u.Description, u.Mdp, u.Photo " +
                "FROM Participation p " +
                "JOIN Utilisateur u ON p.UPseudo = u.UPseudo " +
                "WHERE p.CID = ?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, conversationId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getString("UPseudo"),
                        rs.getString("Email"),
                        rs.getString("Prenom"),
                        rs.getString("Nom"),
                        rs.getDate("DateInscription").toLocalDate(),
                        rs.getDate("Anniversaire") != null ? rs.getDate("Anniversaire").toLocalDate() : null,
                        rs.getString("Description"),
                        rs.getString("Mdp"),
                        rs.getBytes("Photo")
                );
                participants.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

        return participants;
    }

    public boolean isParticipating(String conversationID, User user){
        List<User> users = this.findParticipantsByConversationId(conversationID);
        return users.contains(user);
    }


}
