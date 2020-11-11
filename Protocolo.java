import java.util.ArrayList; 

public class Protocolo{
	private static String user;
	private static String serverName;
	private static String password;
	private static boolean serverExists = false;

	//Se colocan los datos del cliente desde el servidor
	public void setClient(String user, String serverName, String password){
		this.user = user;
		this.serverName = serverName;
		this.password = password;
	}
	//Funcion para cliente
	public String checkServer(String ingreso, String password){ //Recibe usuario@servidor
		try{
			this.user = ingreso.split("@")[0];
			this.serverName = ingreso.split("@")[1];
			this.password = password;
			
			return "checkServer"; //Se revisa si existe el server			
		} catch (Exception e) {
			return "";
		}

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
	//Funcion para que el cliente pida el CLIST al server
	public String CLIST(){
		return "CLIST " + user;
	}
	//Funcion para el cliente pida los GETNEWMAILS al server
	public String GETNEWMAILS(){
		return "GETNEWMAILS " + user;
	}
	//Funcion para avisar al server que se mandara un correo
	public String SENDMAIL(){
		return "SEND MAIL";
	}
	//Funcion para avisar al server que se ingresara un nuevo contacto
	public String NEWCONT(){
		return "NEWCONT";
	}
	//Funcion para avisar al server que se hara LOGOUT
	public String LOGOUT(){
		return "LOGOUT";
	}
	//Funcion para que el usuario le diga al cliente que hacer
	public String buscarFuncion(String funExists){
		if (funExists.equalsIgnoreCase("GETNEWMAILS")) {
			return GETNEWMAILS(); //Se manda a llamar GETNEWMAILS para mandarle la senal al server
		} else if (funExists.equalsIgnoreCase("SEND MAIL")) {
			return SENDMAIL(); //Se manda a llamar SENDMAIL para mandarle la senal al server
		} else if (funExists.equalsIgnoreCase("NEWCONT")) {
			return NEWCONT(); //Se manda a llamar NEWCONT para mandarle la senal al server
		} else if (funExists.equalsIgnoreCase("LOGOUT")) {
			return LOGOUT(); //Se manda a llamar LOGOUT para mandarle la senal al server
		} else {
			return null; //Si no se encuentra la funcion, no se realiza nada
		}
	}
}