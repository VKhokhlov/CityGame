import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8080;
    private static final String firstCity = "???";
    private static String currentCity = firstCity;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started!");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    out.println(currentCity);
                    String input = in.readLine();

                    if (input == null) {
                        continue;
                    }

                    System.out.println(String.format("New connection, port = %d, city = %s", clientSocket.getPort(), input));

                    if (checkCity(input)) {
                        currentCity = input;
                        out.println("OK");
                    } else {
                        out.println("NOT OK");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkCity(String city) {
        if (city == null) {
            return false;
        }

        String tmp = city.trim().toLowerCase();

        if (currentCity.equals(firstCity)) {
            return true;
        }

        String firstLetter = tmp.substring(0, 1);
        String currLastLetter = currentCity.substring(currentCity.length() - 1);

        return firstLetter.equals(currLastLetter);
    }
}
