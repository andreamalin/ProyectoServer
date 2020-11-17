import java.sql.*;
/**
 *
 */
public class DataBase {

    // Variables relacionadas al la base de datos
    protected Connection dataBase;
    protected PreparedStatement prepared;
    protected ResultSet result;

    // Variables de acceso a la base de datos
    protected final String url, username, password;

    /**
     * Constructor de la base de datos
     * @param url la dirección de la base de datos
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     */
    public DataBase(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Realiza la conexión de la base de datos MySQL
     */
    protected void getConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.dataBase = DriverManager.getConnection(this.url, this.username, this.password);

        }catch (Exception ignored){ }

    }

}
