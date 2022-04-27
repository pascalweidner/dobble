package Server;

import java.util.concurrent.TimeUnit;

public class CheckServer extends Thread{

    @Override
    public void run() {
        while(true) {
            try {
                TimeUnit.MINUTES.sleep(1);
                for (String lobbyCode : Server.lobbys.keySet()) {
                    long elapsedTime = System.currentTimeMillis() - Server.lobbys.get(lobbyCode).getTime();
                    if(elapsedTime >= TimeUnit.HOURS.toMillis(1)) {
                        Server.lobbys.remove(lobbyCode);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
