import javax.print.attribute.standard.MediaSize;

public class Prueba {

    public static void main(String[] args){
        ServerGUI gui = new ServerGUI();

        DNSDataBase db = Dao.getDNSDataBase();
        OtherDataBase db1 = Dao.getOtherDataBase();

        System.out.println("hola");

    }
}
