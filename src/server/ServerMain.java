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

    protected static Integer differencesAverage;

    public static void main(String[] args) {
        startServerProcessFlow();
    }

    protected static void startServerProcessFlow() {
        System.out.println("##### Starting server... #####\n");
        final Server server = new Server(LocalTime.of(17, 0, 0), getConnectionsList());
        final List<LocalTime> clientSchedules = new ArrayList<>(server.getTimeFromClients());
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        displayInitialSchedules(server, clientSchedules, dateTimeFormatter);
        executeBerkeleyProcess(server, clientSchedules);
        displayFinalSchedules(server, dateTimeFormatter);
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

    protected static void executeBerkeleyProcess(@NotNull final Server server, @NotNull final List<LocalTime> clientSchedules) {
        System.out.println("\n##### Starting to verify the time from clients... #####\n");
        clientSchedules.add(server.getServerTime());
        final List<Integer> timeDifferences = clientSchedules.stream().map(server::getTimeDifference).toList();
        final int differencesSum = timeDifferences.stream().mapToInt(Integer::intValue).sum();
        differencesAverage = Math.abs(Math.abs(differencesSum) / clientSchedules.size());
        System.out.println("Differences average: " + differencesAverage + "\n");
        server.sendTimeDifferences(differencesAverage);
        server.adjustServerTime(differencesAverage);
    }

    protected static void displayFinalSchedules(final Server server, final DateTimeFormatter dateTimeFormatter) {
        System.out.println("\nFinal time from each node: \n");
        System.out.println("Server time: " + dateTimeFormatter.format(server.getServerTime()) + "\n");
        server.getClients().forEach(client -> {
            try {
                System.out.println("Client time: " + dateTimeFormatter.format(client.getClientTime()));
            } catch (final Exception exception) {
                throw new RuntimeException(exception);
            }
        });
    }

}
