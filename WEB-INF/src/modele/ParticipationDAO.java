package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controler.Conv;
import controler.Participation;

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
    
}
