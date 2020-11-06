import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 */
public class ServerDataBase {

    // Variables relacionadas al la base de datos
    private Connection dataBase;
    private PreparedStatement prepared;
    private ResultSet result;

    // Variables de acceso a la base de datos
    private final String url, username, password;

    public ServerDataBase(String url, String username, String password){
        this.url = "jdbc:mysql://localhost:3306/ProyectoServer?useTimezone=true&serverTimezone=UTC";
        this.username = "root";
        this.password = "A2020C#_mP#tq";
    }

    /**
     * Se le manda el user que se esta buscando el base de datos
     * @param username el nombre del usuario
     * @return 'ERROR' si no lo encuentra o la contraseña si lo encuentra
     */
    public String getUserPassword(String username){
        String password = "ERROR";

        try{
            getConection();
            prepared = this.dataBase.prepareStatement("SELECT * FROM users WHERE username = ?");
            prepared.setString(1, username);

            result = prepared.executeQuery();

            if(result.next()){
                password = result.getString("password");
            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return password;
    }

    /**
     * Realiza la conexión de la base de datos MySQL
     */
    private void getConection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.dataBase = DriverManager.getConnection(this.url, this.username, this.password);

        }catch (Exception ignored){ }

    }

}
