package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import controler.Abonnement;

public class AbonnementDAO {

    private DS ds;

    public AbonnementDAO(){
        ds = new DS();
    }

    // Créer un abonnement
    public void create(Abonnement abonnement){
        String query = "INSERT INTO Abonnement (AbonnementID, Utilisateur1, Utilisateur2, DateAbonnement) VALUES (?, ?, ?, ?)";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, abonnement.getAbonnementID());
            ps.setString(2, abonnement.getUtilisateur1());
            ps.setString(3, abonnement.getUtilisateur2());
            ps.setDate(4, Date.valueOf(abonnement.getDateAbonnement()));
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    // Récupérer tous les abonnements
    public List<Abonnement> findAll() {
        List<Abonnement> abonnements = new ArrayList<>();
        String query = "SELECT * FROM Abonnement";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Abonnement abonnement = new Abonnement(
                        rs.getString("AbonnementID"),
                        rs.getString("Utilisateur1"),
                        rs.getString("Utilisateur2"),
                        rs.getDate("DateAbonnement").toLocalDate()
                );
                abonnements.add(abonnement);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return abonnements;
    }

    // Trouver un abonnement par ID
    public Abonnement findByID(String abonnementID){
        Abonnement abonnement = null;
        String query = "SELECT * FROM Abonnement WHERE AbonnementID = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, abonnementID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                abonnement = new Abonnement(
                        rs.getString("AbonnementID"),
                        rs.getString("Utilisateur1"),
                        rs.getString("Utilisateur2"),
                        rs.getDate("DateAbonnement").toLocalDate()
                );
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return abonnement;
    }

    // Supprimer un abonnement par ID
    public void deleteByID(String abonnementID){
        String query = "DELETE FROM Abonnement WHERE AbonnementID = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, abonnementID);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        
    }

    // Vérifier si un abonnement existe entre deux utilisateurs
    public boolean exists(String utilisateur1, String utilisateur2) {
        String query = "SELECT COUNT(*) FROM Abonnement WHERE (Utilisateur1 = ? AND Utilisateur2 = ?) OR (Utilisateur1 = ? AND Utilisateur2 = ?)";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, utilisateur1);
            ps.setString(2, utilisateur2);
            ps.setString(3, utilisateur2);
            ps.setString(4, utilisateur1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return false;
    }

    // Méthode pour récupérer tous les abonnements d'un utilisateur (par pseudo)
    public List<Abonnement> findByUserPseudo(String pseudo) {
        List<Abonnement> abonnements = new ArrayList<>();
        String query = "SELECT * FROM Abonnement WHERE Utilisateur1 = ? OR Utilisateur2 = ?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, pseudo);
            ps.setString(2, pseudo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Abonnement abonnement = new Abonnement(
                        rs.getString("AbonnementID"),
                        rs.getString("Utilisateur1"),
                        rs.getString("Utilisateur2"),
                        rs.getDate("DateAbonnement").toLocalDate()
                );
                abonnements.add(abonnement);
            }
            con.close();

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return abonnements;
    }

    public Abonnement findByUsers(String utilisateur1, String utilisateur2) {
        Abonnement abonnement = null;
        String query = "SELECT * FROM Abonnement WHERE (Utilisateur1 = ? AND Utilisateur2 = ?) OR (Utilisateur1 = ? AND Utilisateur2 = ?)";
    
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
    
            ps.setString(1, utilisateur1);
            ps.setString(2, utilisateur2);
            ps.setString(3, utilisateur2);
            ps.setString(4, utilisateur1);
    
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                abonnement = new Abonnement(
                        rs.getString("AbonnementID"),
                        rs.getString("Utilisateur1"),
                        rs.getString("Utilisateur2"),
                        rs.getDate("DateAbonnement").toLocalDate()
                );
            }
    
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    
        return abonnement;
    }
}

