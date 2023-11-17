/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientTimeInterfaceImpl extends UnicastRemoteObject implements ClientTimeInterface {

    private LocalDateTime time;

    public ClientTimeInterfaceImpl() throws RemoteException {}

    @Override
    public LocalDateTime getTime() throws RemoteException {
        return time;
    }

    @Override
    public void setTime(final LocalDateTime time) throws RemoteException {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Time updated: " + dateTimeFormatter.format(time));
        this.time = time;
    }

}
