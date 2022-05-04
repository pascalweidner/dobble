package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TEst {
    public static void main(String[] args) {
        try {
            /*
            Socket client = new Socket("localhost", 8000);
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF("Join Lobby hpUILRoZ Test2");
            DataInputStream input = new DataInputStream(client.getInputStream());
            System.out.println(input.readUTF());

            Socket client2 = new Socket("localhost", 8000);
            DataOutputStream output2 = new DataOutputStream(client2.getOutputStream());
            output2.writeUTF("Start Game hpUILRoZ 1");
            DataInputStream input2 = new DataInputStream(client2.getInputStream());
            System.out.println(input2.readUTF());

             */
            Socket client2 = new Socket("localhost", 8000);
            DataOutputStream output2 = new DataOutputStream(client2.getOutputStream());
            output2.writeUTF("Match Card hpUILRoZ 1 53");
            DataInputStream input = new DataInputStream(client2.getInputStream());
            System.out.println(input.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
