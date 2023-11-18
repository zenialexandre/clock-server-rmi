/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package server;

import commons.Constants;
import org.jetbrains.annotations.NotNull;
import server.connection.Connection;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServerMain {

    public static void main(String[] args) {
        startServerProcessFlow();
    }

    protected static void startServerProcessFlow() {
        System.out.println("##### Starting server... #####\n");
        final Server server = new Server(LocalTime.of(17, 0, 0, 0), getConnectionsList());
        final List<LocalTime> clientSchedules = new ArrayList<>(server.getTimeFromClients());
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        displayInitialSchedules(server, clientSchedules, dateTimeFormatter);
        executeBerkeleyProcess(server, clientSchedules, dateTimeFormatter);
    }

    protected static List<Connection> getConnectionsList() {
        return Arrays.asList(
                new Connection(Constants.SERVER_IP_ADDRESS, Constants.FIRST_CLIENT_PORT),
                new Connection(Constants.SERVER_IP_ADDRESS, Constants.SECOND_CLIENT_PORT)
        );
    }

    protected static void displayInitialSchedules(@NotNull final Server server, @NotNull final List<LocalTime> clientSchedules,
                                                  @NotNull final DateTimeFormatter dateTimeFormatter) {
        System.out.println("Server time: " + dateTimeFormatter.format(server.getServerTime()) + "\n");
        System.out.println("Time from clients: \n" + clientSchedules.stream()
                .map(dateTimeFormatter::format).collect(Collectors.joining("\n")));
    }

    protected static void executeBerkeleyProcess(@NotNull final Server server, @NotNull final List<LocalTime> clientSchedules,
                                                 @NotNull  final DateTimeFormatter dateTimeFormatter) {
        System.out.println("\n##### Starting to verify the time from clients... #####\n");
        clientSchedules.add(server.getServerTime());
        final int differencesSum = clientSchedules.stream()
                .map(server::getTimeDifference)
                .mapToInt(Integer::intValue)
                .sum();
        final int differencesAverage = Math.abs(differencesSum / clientSchedules.size());
        System.out.println("Differences average: " + differencesAverage + "\n");
        server.sendTimeDifferences(differencesAverage, dateTimeFormatter);
    }

}
