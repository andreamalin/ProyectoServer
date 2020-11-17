import java.util.ArrayList;

public class DNSDataBase extends DataBase {

    /**
     * Constructor de la base de datos
     * @param url la dirección de la base de datos
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     */
    public DNSDataBase(String url, String username, String password){
        super(url, username, password);
    }

    /**
     * Se encarga de obtener todos los servidores que se encuentran en la base de datos
     * @return una lista de los servidores
     */
    public ArrayList<ServerIp> getServers(){
        ArrayList<ServerIp> servers = new ArrayList<>();
        ServerIp temp;

        try{

            // Obteniendo todos los mails
            getConnection();

            // Mandando el query para obtener los ids de los mails relacionados con el usuario
            prepared = this.dataBase.prepareStatement("SELECT * FROM servers");
            result = prepared.executeQuery();

            // Creando los mails con sus ids
            while (result.next()){

                // Llenando la informacion de cada server
                temp = new ServerIp(result.getString("idservers"));
                temp.setServerName(result.getString("serverName"));
                temp.setIp(result.getString("serverIp"));

                // Metiendolo a la estructura
                servers.add(temp);

            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return servers;
    }

    /**
     * Se encarga de agregar un nuevo server a la base de datos
     * @param newServer un objeto tipo server
     * @return 0 si hubo un error y 1 se se logro completar
     */
    public Integer addServerIP(ServerIp newServer){
        int result = 0;

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("INSERT INTO servers (serverName, serverIp)" +
                    " VALUES(?,?)");
            prepared.setString(1, newServer.getServerName());
            prepared.setString(2, newServer.getIp());

            result = prepared.executeUpdate();

            this.dataBase.close();

        }catch (Exception ignored){ }

        return result;
    }

}
