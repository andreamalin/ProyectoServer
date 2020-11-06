public class ServerIp {

    public final String id;
    private String serverName, ip;

    public ServerIp(String id){
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String toString(){
        return serverName + "@" + ip;
    }

}
