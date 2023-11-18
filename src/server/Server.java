/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package server;

import client.ClientInterface;
import server.connection.Connection;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private LocalTime serverTime;
    private final List<Connection> connectionList = new ArrayList<>();

    public Server(final LocalTime serverTime, final List<Connection> connectionList) {
        setServerTime(serverTime);
        setConnectionList(connectionList);
    }

    protected List<ClientInterface> getClients() {
        return getConnectionList().stream()
                .map(this::getRmiRegistry)
                .map(this::getLookup)
                .toList();
    }

    protected List<LocalTime> getTimeFromClients() {
            return getClients().stream()
                    .map(this::getClientTime)
                    .toList();
    }

    protected Registry getRmiRegistry(final Connection connection) {
        try {
            return LocateRegistry.getRegistry(connection.getIpAddress(), connection.getPort());
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected ClientInterface getLookup(final Registry registry) {
        try {
            return (ClientInterface) registry.lookup("ClientInterface");
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected LocalTime getClientTime(final ClientInterface clientInterface) {
        try {
            return clientInterface.getClientTime();
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected Integer getTimeDifference(final LocalTime clientTime) {
        return Integer.parseInt(String.valueOf(getServerTime()
                .until(clientTime, ChronoUnit.MINUTES))) * -1;
    }

    protected void sendTimeDifferences(final Integer differencesAverage, final DateTimeFormatter dateTimeFormatter) {
        try {
            System.out.println("##### Starting to send differences to each client... #####\n");
            for (final ClientInterface clientInterface : getClients()) {
                clientInterface.adjustClientTime(getServerTime(), differencesAverage);
            }
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setServerTime(final LocalTime serverTime) {
        this.serverTime = serverTime;
    }

    public LocalTime getServerTime() {
        return serverTime;
    }

    private void setConnectionList(final List<Connection> connectionList) {
        this.connectionList.addAll(connectionList);
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

}
