/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain {

    public static void main(String[] args) {
        startClientProcessFlow();
    }

    protected static void startClientProcessFlow() {
        try {
            final ClientTimeInterface serverTimeInterface = new ClientTimeInterfaceImpl();
            final Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ClientTimeInterfaceImpl", serverTimeInterface);
        } catch (final Exception exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

}
