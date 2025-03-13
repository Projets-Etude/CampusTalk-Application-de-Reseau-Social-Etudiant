package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikePosteDAO {

    private DS ds;

    public LikePosteDAO() {
        ds = new DS();
    }

    // Ajouter un like
    public void addLike(String posteID, String uPseudo) {
        String query = "INSERT INTO LikePoste (PosteID, UPseudo) VALUES (?, ?)";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, posteID);
            ps.setString(2, uPseudo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur SQL (addLike) : " + e.getMessage());
        }
    }

    // Supprimer un like
    public void removeLike(String posteID, String uPseudo) {
        String query = "DELETE FROM LikePoste WHERE PosteID = ? AND UPseudo = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, posteID);
            ps.setString(2, uPseudo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur SQL (removeLike) : " + e.getMessage());
        }
    }

    // Vérifier si un utilisateur a liké un post
    public boolean hasLiked(String posteID, String uPseudo) {
        String query = "SELECT COUNT(*) FROM LikePoste WHERE PosteID = ? AND UPseudo = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, posteID);
            ps.setString(2, uPseudo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL (hasLiked) : " + e.getMessage());
        }
        return false;
    }

    // Récupérer le nombre de likes d'un post
    public int countLikes(String posteID) {
        String query = "SELECT COUNT(*) FROM LikePoste WHERE PosteID = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, posteID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL (countLikes) : " + e.getMessage());
        }
        return 0;
    }

    // Récupérer la liste des utilisateurs ayant liké un post
    public List<String> getUsersWhoLiked(String posteID) {
        List<String> users = new ArrayList<>();
        String query = "SELECT UPseudo FROM LikePoste WHERE PosteID = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, posteID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(rs.getString("UPseudo"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL (getUsersWhoLiked) : " + e.getMessage());
        }
        return users;
    }
}
