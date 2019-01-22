
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class IPchecker{

public static String ip;
public static double shortmask;
    public static void main(String[] args) throws IOException {

        setAdresses();
        String sub = getTargetSubnet(10);
        System.out.println(sub);
shortmask = 32-shortmask;
        String yo = getIPv4LocalNetMask((int) shortmask);
        System.out.println(yo);

//        for (int i=1;i<255;i++){
//            String host=hosts + "." + i;
//            if (InetAddress.getByName(host).isReachable(timeout)){
//                System.out.println(host + " is reachable");
//            }
//        }


    }
    public static void setAdresses() throws SocketException, UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        ip = localHost.getHostAddress();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
        shortmask = 32 - networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
    }
    public static String getTargetSubnet(){
        return getTargetSubnet(2);
    }
    public static String getTargetSubnet(double mask){
        String target;
        int bits;
        if (mask>8) {
            if (mask>16) {
                if (mask>24) {

                    bits = (int) Math.pow(2, mask-24) - 1;
                    bits = 255-bits;
                    target = bits+".0.0.0";

                } else {
                    System.out.println("mask 8-15");
                    bits = (int)Math.pow(2, mask-16) - 1;
                    bits = 255-bits;
                    target = "255."+bits+".0.0";

                }
            } else {

                bits =(int) Math.pow(2, mask-8) - 1;
                bits = 255-bits;
                target = "255.255."+bits+".0";

            }
        } else{
            System.out.println("mask 24-32");
            bits = (int)Math.pow(2, mask) - 1;
            bits = 255-bits;
            target = "255.255.255."+bits;
                }
        mask = 32-mask;
        System.out.println("Mask /"+(int)mask);
        return target;
    }
    public static String getIPv4LocalNetMask(int netPrefix) {

        try {
            // Since this is for IPv4, it's 32 bits, so set the sign value of
            // the int to "negative"...
            int shiftby = (1<<31);
            // For the number of bits of the prefix -1 (we already set the sign bit)
            for (int i=netPrefix-1; i>0; i--) {
                // Shift the sign right... Java makes the sign bit sticky on a shift...
                // So no need to "set it back up"...
                shiftby = (shiftby >> 1);
            }
            // Transform the resulting value in xxx.xxx.xxx.xxx format, like if
            /// it was a standard address...
            String maskString = Integer.toString((shiftby >> 24) & 255) + "." + Integer.toString((shiftby >> 16) & 255) + "." + Integer.toString((shiftby >> 8) & 255) + "." + Integer.toString(shiftby & 255);
            // Return the address thus created...
            return maskString;
        }
        catch(Exception e){e.printStackTrace();
        }
        // Something went wrong here...
        return null;
    }
}