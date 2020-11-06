import java.util.ArrayList;

public class Prueba {

    public static void main(String[] args){
        ServerDataBase db = Dao.getDataBase();
        String[] hola = db.getUserData("brandon");

        //System.out.println(hola[1]);

        ArrayList<Mail> mails =  db.getUserMails("1");

        for (int i = 0; i < mails.size(); i++) {
            if (!(i == (mails.size()-1))){
                System.out.println(mails.get(i).toString());
            }else{
                System.out.println(mails.get(i).toString() + "*");
            }
        }

    }



}
