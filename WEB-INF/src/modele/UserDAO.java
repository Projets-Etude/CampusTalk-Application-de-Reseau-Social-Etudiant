package modele;

import controler.Message;
import controler.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class UserDAO {
    private DS ds;

    public UserDAO(){
        ds = new DS();
    }

    public void create(User user){
        String query="INSERT INTO user values" +
                "(?,?,?,?,?,?,?,?);";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, user.getUPseudo());
            ps.setString(2, user.getEmail());
            ps.setDate(3, Date.valueOf(user.getPrenom()));
            ps.setString(4, user.getNom());
            ps.setString(5, String.valueOf(user.getDateInscritption()));
            ps.setString(6, String.valueOf(user.getAnniversaire()));
            ps.setString(7, user.getDescription());
            ps.setString(8, user.getMdp());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public User findByID(String id){
        User user = null;
        String query="SELECT * FROM utilisateur WHERE UPseudo = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("UPseudo"), rs.getString("email"), rs.getString("prenom"), rs.getString("nom"), rs.getDate("dateInscription").toLocalDate(), rs.getDate("anniversaire").toLocalDate(), rs.getString("description"), rs.getString("mdp"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            return null;

        }
        return user;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getString("UPseudo"),
                        rs.getString("email"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getDate("dateInscritption").toLocalDate(),
                        rs.getDate("anniversaire").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("mdp")
                );
                users.add(user);
            }
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
        return users;
    }

    public void deleteById(String id){
        User user = null;
        String query="Delete * FROM user WHERE UPseudo = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            int rs = ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
