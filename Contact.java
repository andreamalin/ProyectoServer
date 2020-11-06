public class Contact {

    public final String id;
    private String username, server;

    public Contact(String id){
        this.id = id;
    }

    // GETTERS DE ATRIBUTOS
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public String getServer() {
        return server;
    }


    // SETTERS DE ATRIBUTOS
    public void setUsername(String username) {
        this.username = username;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String toString(){
        return username + "@" + server;
    }

}
