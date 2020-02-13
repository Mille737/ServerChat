import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static final int Port = 1234;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {

        try {
            //new serverSocket object with associated port(number)
            serverSocket = new ServerSocket(Port);
        }
        catch(IOException ioEx) {
            System.out.println("unreachable port");
            System.exit(1);
        }
        do {
            handleClient();
        } while (true);
    }

    private static void handleClient() {

        Socket link = null;
        try {
            // server waiting for connection from client
            link = serverSocket.accept();

            //wrap inputstream/outputstream with scanner/printWriter for use in consol application
            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            int messageNum = 0;
            //input
            String message = input.nextLine();

            while (!message.equals("***CLOSE***")) {
                System.out.println("Message received");
                messageNum ++;
                //output
                output.println("Message " + messageNum + " : " + message);
                input.nextLine();
            }
            //output
            output.println(messageNum + " messages received.");
        }
        catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally {
            try {
                System.out.println("\n Closing communication");
                //close communication when done
                link.close();
            }
            catch (IOException ioEx) {
                System.out.println("Unable to disconnect.");
                System.exit(1);
            }
        }
    }
}
