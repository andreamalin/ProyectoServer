import java.util.ArrayList;

public class Prueba {

    public static void main(String[] args){
        ServerDataBase db = Dao.getDataBase();
        User hola = db.getUserData("andrea");

        System.out.println(hola.toString());

        ArrayList<Contact> contacts =  db.getUserContacts("1");

        for (int i = 0; i < contacts.size(); i++) {
            if (!(i == (contacts.size()-1))){
                System.out.println(contacts.get(i).toString());
            }else{
                System.out.println(contacts.get(i).toString() + " *");
            }
        }

        ArrayList<Mail> mails =  db.getUserMails("1");

        for (int i = 0; i < mails.size(); i++) {
            if (!(i == (mails.size()-1))){
                System.out.println(mails.get(i).toString());
            }else{
                System.out.println(mails.get(i).toString() + " *");
            }
        }

        ArrayList<ServerIp> servers =  db.getServers();

        for (int i = 0; i < servers.size(); i++) {
            if (!(i == (servers.size()-1))){
                System.out.println(servers.get(i).toString());
            }else{
                System.out.println(servers.get(i).toString() + " *");
            }
        }

    }



}
