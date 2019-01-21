
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

import static java.lang.Integer.parseInt;

public class IPchecker {

    public static String ip;
    public static int shortmask;

    public static void main(String[] args) throws IOException {

        setAdresses();
ip = "192.168.32.4";
shortmask = 19;
        SubnetUtils subutil = new SubnetUtils(ip + "/" + shortmask);

        SubnetUtils.SubnetInfo subinfo = subutil.getInfo();
        String broadc = subinfo.getBroadcastAddress();
        String netad = subinfo.getNetworkAddress();
        String[] ads = subinfo.getAllAddresses();
        for(String ad: ads){
            System.out.println(ad);
        }

    }


    public static void setAdresses() throws SocketException, UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        ip = localHost.getHostAddress();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
        shortmask = (int) networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
    }

}