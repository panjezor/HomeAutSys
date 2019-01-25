import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;

public class CmdTest {
    public static void main(String[] args) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "arp -a");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        String mac = "42-a8-98-46-59-82";
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            if (line.contains(mac)) {
                double a = 1 + 1;
            }
            // do line stuff

        }
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("System IP Address : " +
                (localhost.getHostAddress()).trim());


    }
}