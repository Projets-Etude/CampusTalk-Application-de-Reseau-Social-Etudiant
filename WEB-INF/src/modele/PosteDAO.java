package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controler.Poste;

public class PosteDAO {

    private DS ds;

    public PosteDAO() {
        ds = new DS();
    }

    public void create(Poste p) {
        String query = "INSERT INTO Poste (PosteID, Contenu, DatePoste, VisibiliteRestreinte, UPseudo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, p.getPosteID());
            ps.setString(2, p.getContenu());
            ps.setDate(3, new java.sql.Date(p.getDatePoste().getTime()));
            ps.setBoolean(4, p.isVisibiliteRestreinte());
            ps.setString(5, p.getUPseudo());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    public List<Poste> findAll() {
        List<Poste> postes = new ArrayList<>();
        String query = "SELECT * FROM Poste";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Poste poste = new Poste(
                        rs.getString("PosteID"),
                        rs.getString("Contenu"),
                        rs.getDate("DatePoste"),
                        rs.getBoolean("VisibiliteRestreinte"),
                        rs.getString("UPseudo")
                );
                postes.add(poste);
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        Collections.reverse(postes);
        return postes;
    }

    public Poste findByID(String id) {
        Poste poste = null;
        String query = "SELECT * FROM Poste WHERE PosteID = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                poste = new Poste(
                        rs.getString("PosteID"),
                        rs.getString("Contenu"),
                        rs.getDate("DatePoste"),
                        rs.getBoolean("VisibiliteRestreinte"),
                        rs.getString("UPseudo")
                );
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return poste;
    }

    public void deleteById(String id) {
        String query = "DELETE FROM Poste WHERE PosteID = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}

