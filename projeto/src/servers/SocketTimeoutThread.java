package servers;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketTimeoutThread extends Thread {

    private final ServerSocket s;

    public SocketTimeoutThread(ServerSocket s) {
        this.s = s;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + 10000;
        while (true) {
            if (System.currentTimeMillis() > endTime) {
                try {
                    s.close();
                    break;
                } catch (IOException ex) {
                    break;
                }
            }
        }
    }
}
