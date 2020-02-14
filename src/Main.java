import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {


    private static ServerSocket serverSocket;
    private static final int Port = 1234;

    public static void main(String[] args) {

        try {
            //new serverSocket object with associated port(number)
            serverSocket = new ServerSocket(Port);
        }
        catch(IOException ioEx) {
            System.out.println("unreachable port");
            System.exit(1);
        }
        try {
            do {
                handleClient();
            } while (true);
        }
        catch (NoSuchElementException nseEx) {
            nseEx.printStackTrace();
        }
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
            String message;
            //input
            while (input.hasNextLine()) {
                message = input.nextLine();


                while (!message.equals("**CLOSE**")) {
                    System.out.println("Message received "+ message);
                    messageNum ++;
                    //output
                    output.println("Message " + messageNum + " : " + message);
                    message = input.nextLine();

                }
                //output
                output.println(messageNum + " messages received.");
            }}
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
