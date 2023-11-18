/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package client;

import commons.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientMain {

    public static void main(String[] args) {
        startClientProcessFlow();
    }

    protected static void startClientProcessFlow() {
        try {
            System.out.println("##### Starting clients... #####\n");
            final List<ClientInterface> clientInterfacesList = new ArrayList<>();
            createRegistryWithClient(Constants.FIRST_CLIENT_PORT, new ClientInterfaceImpl(), Arrays.asList(17, 30, 0), clientInterfacesList);
            createRegistryWithClient(Constants.SECOND_CLIENT_PORT, new ClientInterfaceImpl(), Arrays.asList(16, 45, 0), clientInterfacesList);
            clientInterfacesList.forEach(clientInterface -> System.out.println("Client " + clientInterface + " ready to receive requests."));
        } catch (final Exception exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    protected static void createRegistryWithClient(final Integer clientPort, final ClientInterface clientInterface,
                                                   final List<Integer> clientTimeList, final List<ClientInterface> clientInterfaceList) {
        try {
            final Registry registry = LocateRegistry.createRegistry(clientPort);
            clientInterface.setClientTime(LocalTime.of(clientTimeList.get(0), clientTimeList.get(1), clientTimeList.get(2)));
            clientInterfaceList.add(clientInterface);
            registry.rebind(ClientInterface.class.getSimpleName(), clientInterface);
        } catch (final Exception exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

}
