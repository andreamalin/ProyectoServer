import java.util.ArrayList; 

public class FunCliente{
	Protocolo protocolo = new Protocolo(); //Comunicacion con el server

	String user;
	String serverName;
	String password;

	public String LOGIN(String ingreso, String password){ //Recibe usuario@servidor
		user = ingreso.split("@")[0];
		serverName = ingreso.split("@")[1];
		password = password;
		//Se verifica que el server este en la db
		if (protocolo.checkServer(serverName).equals("true")){
			return "LOGIN " + user + " password"; 
		} else {
			return "SEND ERROR 104 " + ingreso; //Esto lo retorna el server si no existe
		}
	}

	public String ckeckAccount(){
		return protocolo.checkAccount(user, password);
	}

	public String CLIST(){
		//El server me manda el CLIST de todos
		//voy mostrando en consola uno por uno
		return "OK CLIST marta@server";
	}
}