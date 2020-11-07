import java.util.ArrayList; 

public class Protocolo{
	private String user;
	private String serverName;
	private String password;
	private static boolean serverExists = false;

	//Funcion para cliente
	public String checkServer(String ingreso, String password){ //Recibe usuario@servidor
		this.user = ingreso.split("@")[0];
		this.serverName = ingreso.split("@")[1];
		this.password = password;
		
		return "checkServer"; //Se revisa si existe el server
	}
	//Funcion para que la DB en servidor sepa a cual server se busca acceder
	public String getServer(){
		return serverName;
	}
	//Funcion para el servidor, la db devuelve un bool
	public String setServer(boolean serverExists){
		if(serverExists){
			return ""; //Si no hay error es porque existe 
		} else{
			return "SEND ERROR 104 " + user + "@" + serverName; //Esto lo retorna el server si no existe
		}
	}
	//Funcion para cliente
	public String LOGIN(){
		return "LOGIN " + user + " password"; //Se pide hacer login
	}
	//Funcion para que la DB en servidor sepa a cual usuario se busca acceder
	public String getUser(){
		return user;
	}
	//Funcion para que la DB en servidor sepa cual es la contrasena del usuario a acceder
	public String getPassword(){
		return password;
	}
	//Funcion para el servidor, la db devuelve un bool
	public String setUser(boolean userExists){
		if(userExists){
			return ""; //Si no hay error es porque existe la cuenta
		} else{
			return "LOGIN ERROR 101"; //Esto lo retorna el server si no existe la cuenta
		}
	}
	//Funcion para el servidor, la db devuelve un bool
	public String setPassword(boolean passwordExists){
		if(passwordExists){
			return "OK LOGIN"; //Si no hay error, es porque ya ingreso el usuario
		} else{
			return "LOGIN ERROR 102"; //Esto lo retorna el server si no es correcta la contrasena
		}
	}
}