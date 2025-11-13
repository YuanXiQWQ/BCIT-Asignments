import java.net.*;
import java.io.*;

public class DirServerThread extends Thread {
    private Socket socket = null;

    public DirServerThread(Socket socket)
    {
        super("DirServerThread");
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            String inputLine, outputLine;
            String result = null;
            inputLine = in.readLine();
            System.out.println(inputLine);
            File dir = new File(inputLine);
            String[] chld = dir.list();
            if(chld == null)
            {
                out.println("Specified directory does not exist or is not a directory.");
                System.exit(0);
            } else
            {
                for(int i = 0; i < chld.length; i++)
                {
                    String fileName = chld[i];
                    result += fileName + "\n";
                }
            }
            out.println(result);
            out.close();
            in.close();
            socket.close();

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
