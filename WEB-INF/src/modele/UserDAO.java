package modele;

import controler.Message;
import controler.User;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class UserDAO {
    private DS ds;

    public UserDAO(){
        ds = new DS();
    }

    public User create(String UPseudo, String email, String prenom, String nom, String anniv, String description, String mdp){
        String query = "insert into utilisateur values (?,?,?,?,'"+LocalDate.now()+"',?,null,?)";
        try(Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, UPseudo);
            ps.setString(2, email);
            ps.setString(3, prenom);
            ps.setString(4, nom);
            ps.setDate(5, Date.valueOf(LocalDate.parse(anniv)));
            ps.setString(6, mdp);
            int index = ps.executeUpdate();
            con.close();
            if(index == 1){
                return new User(UPseudo, email, prenom, nom, LocalDate.now(), LocalDate.parse(anniv), description, mdp);
            }
            con.close();
            return null;
        }
        
        catch(SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
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

    public User findByIDandPWD(String pseudo, String mdp){
        User user = null;
        String query = "select * from utilisateur where UPseudo = ? and Mdp = ?";
        try (Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, pseudo);
            ps.setString(2, mdp);
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

    public User updateUser(String pseudo, String email, String prenom, String nom, String anniv, String description, String mdp, String ancienPseudo){
        UserDAO userDAO = new UserDAO();
        String query = "update utilisateur set UPseudo = ? , Email = ? , Prenom = ? , Nom = ? , Anniversaire = ? , Description = ? , Mdp = ?  where UPseudo = ? ";

        try(Connection con = ds.getConnection(); PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, pseudo);
            ps.setString(2, email);
            ps.setString(3, prenom);
            ps.setString(4, nom);
            ps.setDate(5, Date.valueOf(LocalDate.parse(anniv)));
            ps.setString(6, description);
            ps.setString(7, mdp);
            ps.setString(8, ancienPseudo);
            
            System.out.println(ps.toString());
            int index = ps.executeUpdate();
            
            
            con.close();

            if(index == 1){
                return userDAO.findByID(pseudo);

            }else{   
                return null;
            }
            

        }
        catch(SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        return null;
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
