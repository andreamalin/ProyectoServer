import java.io.*;
/**
 *
 */
public class Dao {

    // Main server
    private static ServerDataBase dataBase = null;

    // DNS
    private static DNSDataBase dataBaseDNS = null;

    // Other
    private static OtherDataBase dataBaseO = null;

    /**
     * Se encarga de instanciar la base de datos del server
     * @return una referencia de la base de datos
     */
    public static ServerDataBase getServerDataBase(){

        // Obtiene la referencia si no hay errores
        if(dataBase == null){
            try {
                String[] aux = readFile("DB_info.txt");

                if(!aux[1].equalsIgnoreCase("ERROR"))
                    dataBase = new ServerDataBase(aux[0], aux[1], aux[2]);

            } catch (Exception e){
                dataBase = null;
            }
        }

        return dataBase;
    }

    /**
     * Se encarga de instanciar la base de datos del dns
     * @return una referencia de la base de datos
     */
    public static DNSDataBase getDNSDataBase(){

        // Obtiene la referencia si no hay errores
        if(dataBase == null){
            try {
                String[] aux = readFile("DNS_info.txt");

                if(!aux[1].equalsIgnoreCase("ERROR"))
                    dataBaseDNS = new DNSDataBase(aux[0], aux[1], aux[2]);

            } catch (Exception e){
                dataBase = null;
            }
        }
        return dataBaseDNS;
    }

    /**
     * Se encarga de instanciar la base de datos del otro server
     * @return una referencia de la base de datos
     */
    public static OtherDataBase getOtherDataBase(){

        // Obtiene la referencia si no hay errores
        if(dataBase == null){
            try {
                String[] aux = readFile("Other_info.txt");

                if(!aux[1].equalsIgnoreCase("ERROR"))
                    dataBaseO = new OtherDataBase(aux[0], aux[1], aux[2]);

            } catch (Exception e){
                dataBase = null;
            }
        }
        return dataBaseO;
    }

    /**
     * Se encarga de leer el archivo con los datos de la base de datos, el archivo se debe de llamar
     * 'DB_info.txt', debe de contar con tres lineas.
     * La primera linea es el nombre del Schema de la base de datos. La segunda linea es el usuario
     * que administra esta base de datos, de preferencia que sea el 'root' por comdida. Para finalizar
     * se debe de colocar la contraseña del usuario.
     */
    private static String[] readFile(String fileName){
        String[] aux = new String[3];
        aux[1] = "ERROR";
        String url = "jdbc:mysql://localhost:3306/";
        try{
            FileReader file = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(file);
            for (int i = 0; i < 3; i++)
                aux[i] = buffer.readLine();

            aux[0] = url + aux[0] + "?useTimezone=true&serverTimezone=UTC";

        } catch (Exception ignored){  }

        return aux;
    }

}
