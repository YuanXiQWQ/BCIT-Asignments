import java.io.*;
import java.net.*;

public class HttpOverTcp2 {
    public static void main(String[] args) throws IOException {
        try (
            Socket kkSocket = new Socket("localhost", 8081);
            
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
            System.out.println(kkSocket.getReceiveBufferSize());
            
            String fromServer;
            String fromUser;
		//out.println("GET /tomcat.png HTTP/1.0\n\r\n\r");
                out.println("c:\\temp");
                  //out.println("GET /midp/hits HTTP/1.0\n\r\n\r");
            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);
            }
        } catch (Exception e) {
            System.err.println("Some Error ");
            System.exit(1);
        } 
    }
}
