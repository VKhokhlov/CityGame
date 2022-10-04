import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try (Socket clientSocket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                String response = in.readLine();
                System.out.println(String.format("Current city = %s, input new city (end - exit):", response));
                String input = scanner.nextLine();

                if (input.equals("end")) {
                    break;
                }

                out.println(input);
                response = in.readLine();
                System.out.println(response);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}