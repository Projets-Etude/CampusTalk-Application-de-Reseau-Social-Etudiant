package controler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DS {
    private String driver = "org.postgresql.Driver"; // Le driver de PostgreSQL
    private String url = "jdbc:postgresql://psqlserv/but2";
    private String login = "hugodebuyseretu";
    private String password = "moi";

    // Constructeur qui charge le driver
    public DS() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du driver : " + e.getMessage());
        }
    }

    // Méthode pour obtenir une connexion à la base de données
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, login, password);
    }
}
