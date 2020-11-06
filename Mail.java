public class Mail {

    // Atributos del mail
    private final String id;
    private String author, server, matter, body;

    public Mail(String id){
        this.id = id;
    }

    // GETTERS DE ATRIBUTOS
    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getServer() {
        return server;
    }

    public String getMatter() {
        return matter;
    }

    public String getBody() {
        return body;
    }

    // SETTERS DE ATRIBUTOS
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toString(){
        return author + "@" + server + " " + matter + " " + body;
    }
}
