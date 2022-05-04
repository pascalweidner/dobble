package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Test2 {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 8000);
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF("Create Lobby Test2");
            DataInputStream input = new DataInputStream(client.getInputStream());
            System.out.println(input.readUTF());
            while(true) {
                input = new DataInputStream(client.getInputStream());
                System.out.println(input.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
