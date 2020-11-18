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
    public ServerIp getServer(String serverName){
        ServerIp temp = null;

        try{

            // Obteniendo todos los mails
            getConnection();

            // Mandando el query para obtener los ids de los mails relacionados con el usuario
            prepared = this.dataBase.prepareStatement("SELECT * FROM servers WHERE serverName = ?");
            prepared.setString(1, serverName);
            result = prepared.executeQuery();

            // Creando los mails con sus ids
            if (result.next()){

                // Llenando la informacion de cada server
                temp = new ServerIp(result.getString("idservers"));
                temp.setServerName(serverName);
                temp.setIp(result.getString("serverIp"));

            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return temp;
    }

    /**
     * Se encarga de agregar un nuevo server a la base de datos
     * @param newServer un objeto tipo server
     */
    public void addServerIP(ServerIp newServer){
        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("INSERT INTO servers (serverName, serverIp)" +
                    " VALUES(?,?)");
            prepared.setString(1, newServer.getServerName());
            prepared.setString(2, newServer.getIp());

            prepared.executeUpdate();

            this.dataBase.close();

        }catch (Exception ignored){ }

    }

}
