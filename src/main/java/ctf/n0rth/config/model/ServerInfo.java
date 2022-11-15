package ctf.n0rth.config.model;

public class ServerInfo {
    private String serverName;
    private String countOfPlayers;
    private String timer;
    private String serverStatus;

    public ServerInfo(String serverName, String countOfPlayers, String timer, String serverStatus) {
        this.serverName = serverName;
        this.countOfPlayers = countOfPlayers;
        this.timer = timer;
        this.serverStatus = serverStatus;
    }

    public ServerInfo() {
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getCountOfPlayers() {
        return countOfPlayers;
    }

    public void setCountOfPlayers(String countOfPlayers) {
        this.countOfPlayers = countOfPlayers;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }
}
