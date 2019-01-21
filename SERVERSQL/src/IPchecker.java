
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class IPchecker{

public static String ip;
public static double shortmask;
    public static void main(String[] args) throws IOException {

        setAdresses();
        String sub = getTargetSubnet();
        System.out.println(sub);
        System.out.println(ip);
        String regex = "\\.";
        String[] maskbytes = sub.split(regex);
        String[] ipbytes = ip.split(regex);
        byte[] maskbyte = new byte[4];
        byte[] ipbyte = new byte[4];
        int[] networkbyte = new int[4];
        int[] broadcastbyte = new int[4];

        for(int a1 = 0; a1<=3; a1++){
            maskbyte[a1] = (byte)parseInt(maskbytes[a1]);
            ipbyte[a1] = (byte) parseInt(ipbytes[a1]);
            networkbyte[a1] =  (maskbyte[a1]&ipbyte[a1]);
            broadcastbyte[a1] = (ipbyte[a1]&((byte)255));
            String networkaddress = String.join(".",networkbyte[a1]).;
        }

        String networkaddress = String.join("\\.",networkbyte);

    }



        public static void setAdresses() throws SocketException, UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        ip = localHost.getHostAddress();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
        shortmask = 32 - networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
    }



    public static String getTargetSubnet(){
        return getTargetSubnet(shortmask);
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
            bits = (int)Math.pow(2, mask) - 1;
            bits = 255-bits;
            target = "255.255.255."+bits;
                }
        mask = 32-mask;
        System.out.println("Mask /"+(int)mask);
        return target;
    }


}