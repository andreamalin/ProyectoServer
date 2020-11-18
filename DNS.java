import java.net.ServerSocket;
public class DNS {

    public static void main(String[] args){
        DNSDataBase dataBase = Dao.getDNSDataBase();
        String message = "", messageAux = "", response = "";
        ServerIp temp;

        // Obitiene el nombre del server al cual se quiere buscar en el dns
        temp = dataBase.getServer(message);

        if(temp != null)
            response = temp.getIp();
        else
            response = "NOT FOUND";

        // Mandar response

        // Ahora obtiene obtiene todos los servers de quien se lo pidio
        // while message

        temp = dataBase.getServer(message);
        if(temp == null){
            temp = new ServerIp("0");
            temp.setServerName(message);
            temp.setIp(messageAux);
            dataBase.addServerIP(temp);
        }

    }

}
