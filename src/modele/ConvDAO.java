package modele;

import controler.Conv;
import controler.Message;
import controler.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class ConvDAO {
    private DS ds;

    public ConvDAO(){
        ds = new DS();
    }

    public void create(Conv conv){
        String query="INSERT INTO conv values" +
                "(?,?,?,?,?);";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, conv.getCno());
            ps.setString(2, conv.getTitre());
            ps.setBoolean(3, conv.isPrivee());
            ps.setString(4, conv.getOwner());
            ps.setString(5, String.valueOf(conv.getDate()));
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
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
                        rs.getString("owner"),
                        rs.getDate("date").toLocalDate()
                );
                convs.add(conv);
            }

            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
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
                conv = new Conv(rs.getString("conv"), rs.getString("titre"), rs.getBoolean("privee"), rs.getString("owner"), rs.getDate("date").toLocalDate());
            }
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
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
            out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
