import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DS {
    String driver;
    String url;
    String login;
    String mdp;

    DS(){
        Properties p = new Properties();
        try{
            p.load(new FileInputStream("/home/infoetu/thomas.guislin.etu/tomcat/apache-tomcat-10.1.31/webapps/vide/WEB-INF/classes/config.postgres.prop"));
        }
        catch(Exception e){e.getMessage();}
        this.driver = p.getProperty("driver");
        this.url = p.getProperty("url");
        this.login = p.getProperty("login");
        this.mdp = p.getProperty("password");

        try{
            Class.forName(driver);
        }
        catch(Exception e){System.out.println("DS constructeur : " + e.getMessage());}

    }

    public Connection getConnection(){
        try{
            Connection con = DriverManager.getConnection(url,login,mdp);
            return con;
        }
        catch(Exception e){System.out.println("Méthode getConnection DS : " + e.getMessage());}
        return null;
    }

    public String getLogin(){
        return login;
    }
}
