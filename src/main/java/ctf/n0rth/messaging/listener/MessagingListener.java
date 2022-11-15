package ctf.n0rth.messaging.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctf.n0rth.config.model.Server;
import ctf.n0rth.config.model.ServerInfo;
import ctf.n0rth.config.model.ServerStatus;
import ctf.n0rth.gui.GUIManager;
import redis.clients.jedis.JedisPubSub;

import static ctf.n0rth.messaging.channels.MessagingChannels.CHANNEL_INPUT_NAME;


public class MessagingListener extends JedisPubSub {

    private GUIManager guiManager;

    public MessagingListener(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public void onMessage(String channel, String message) {

        if (!channel.equals(CHANNEL_INPUT_NAME)) return;

        Server server = getServerFromJSON(message);
        guiManager.updateServer(server);

    }

    private Server getServerFromJSON(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        Server server;
        try {
            ServerInfo serverInfo = objectMapper.readValue(message, ServerInfo.class);
            server = guiManager.getServerByName(serverInfo.getServerName());
            server.setServerStatus(ServerStatus.valueOf(serverInfo.getServerStatus()));
            server.setServerInfo(serverInfo);
        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
        return server;
    }
}
