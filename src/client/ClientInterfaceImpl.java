/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ClientInterfaceImpl extends UnicastRemoteObject implements ClientInterface {

    private LocalTime clientTime;

    public ClientInterfaceImpl() throws RemoteException {}

    @Override
    public LocalTime getClientTime() throws RemoteException {
        return clientTime;
    }

    @Override
    public void setClientTime(final LocalTime clientTime) throws RemoteException {
        this.clientTime = clientTime;
    }

    @Override
    public void adjustClientTime(final LocalTime serverTime, final Integer differencesAverage) throws RemoteException {
        final LocalTime clientTime = getClientTime();
        final Integer localTimeDifference = Integer.parseInt(String.valueOf(serverTime
                .until(clientTime, ChronoUnit.MINUTES))) * -1;
        final LocalTime adjustedTime = clientTime.plusMinutes(localTimeDifference + differencesAverage);
        setClientTime(adjustedTime);
        System.out.println("Time updated to: " + DateTimeFormatter.ofPattern("HH:mm:ss").format(adjustedTime));
    }

}
