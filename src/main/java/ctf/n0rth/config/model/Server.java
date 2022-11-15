package ctf.n0rth.config.model;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Objects;

public class Server {
    private String name;
    private String hexColor;
    private InetAddress inet4Address;
    private int port;
    private ServerStatus serverStatus;
    private ServerInfo serverInfo;


    public Server(String name, String hexColor, InetAddress inet4Address, int port) {
        this.name = name;
        this.hexColor = hexColor;
        this.inet4Address = inet4Address;
        this.port = port;
        this.serverInfo = new ServerInfo(name, null, null,
                ServerStatus.OFFLINE.name());
        serverStatus = ServerStatus.OFFLINE;
    }

    public Server() {
        this.serverInfo = new ServerInfo(null, null, null,
                ServerStatus.OFFLINE.name());
        serverStatus = ServerStatus.OFFLINE;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getInet4Address() {
        return inet4Address;
    }

    public void setInet4Address(Inet4Address inet4Address) {
        this.inet4Address = inet4Address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setInet4Address(InetAddress inet4Address) {
        this.inet4Address = inet4Address;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", inet4Address=" + inet4Address +
                ", port=" + port +
                ", serverStatus=" + serverStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return Objects.equals(name, server.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
