package ctf.n0rth.config;

import ctf.n0rth.config.model.Server;
import org.bukkit.configuration.ConfigurationSection;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CTFServersConfigHelper {
    private static final String SERVERS_PARAMETER_NAME = "servers";
    private static final String SERVER_NAME = "name";
    private static final String SERVER_COLOR = "color";
    private static final String SERVER_ADDRESS = "address";


    private static List<Server> servers = null;

    public static List<Server> getServers() {
        if (servers != null) {
            return servers;
        }

        List<Server> servers = new ArrayList<>();

        ConfigurationSection configurationSection = CTFConfig.
                configurationSection(SERVERS_PARAMETER_NAME);
        for (String key : configurationSection.getKeys(false)) {
            ConfigurationSection memorySection =
                    configurationSection.getConfigurationSection(key);
            Map<String, Object> values = memorySection.getValues(false);

            String serverName = (String) values.get(SERVER_NAME);
            String serverColor = (String) values.get(SERVER_COLOR);
            String fullAddress = (String) values.get(SERVER_ADDRESS);
            String[] addressValues = fullAddress.split(":");
            String address = addressValues[0];
            int port = Integer.parseInt(addressValues[1]);
            Server server;
            try {
                server = new Server(serverName, serverColor, Inet4Address.getByName(address), port);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            servers.add(server);
        }
        CTFServersConfigHelper.servers = servers;
        return servers;
    }

    private CTFServersConfigHelper() {

    }

}
