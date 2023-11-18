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
        final long clientNanoTime = getClientTime().toNanoOfDay();
        final long localTimeDifference = serverTime.toNanoOfDay() - clientNanoTime;
        final long adjustedTime = localTimeDifference * -1 + differencesAverage + clientNanoTime;
        setClientTime(LocalTime.ofNanoOfDay(adjustedTime));
        System.out.println("Time updated to: " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.ofNanoOfDay(adjustedTime)));
    }

}
