public class User {

    public final String id;

    private String username, password, status, server;

    public User(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getStatus() {
        return status;
    }

    public String getServer() {
        return server;
    }

    // SETTERS DE LOS ATRIBUTOS
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String toString(){
        return username + "@" +status;
    }

}
