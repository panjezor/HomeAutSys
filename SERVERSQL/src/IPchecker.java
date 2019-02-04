
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;




public class IPchecker{

public static String ip;
public static int shortmask;
    public static void main(String[] args) throws IOException {}
    
    
    public String[] getIPs() throws SocketException, UnknownHostException {

        setAdresses();
        String sub = getTargetSubnet();
        System.out.println(sub);
        shortmask = 32-shortmask;
        String yo = getIPv4LocalNetMask((int) shortmask);
        System.out.println(yo);
        
    //get the IP addresses
    SubnetUtils subnetInfo = new SubnetUtils(ip+"/"+shortmask);
    String[] IPAddresses = subnetInfo.getInfo().getAllAddresses();  
   return IPAddresses;


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
    public String getHostIP() throws UnknownHostException {
    	InetAddress localHost = InetAddress.getLocalHost();
    	StringBuilder SB = new StringBuilder();
		String local = localHost.getHostAddress();
		System.out.println(SB);
		for(char s : local.toCharArray()) {
		
			SB.append(s);
		}
		String s = new String(SB);
		SB = new StringBuilder();
	
		String[] ipHost = s.split("\\.");

		for(int i = 0; i<ipHost.length; i++) {
			String j = ipHost[i];
			System.out.println(j.length());
			switch(j.length()) {
			case 0:
				SB.append("000");
				break;
			case 1:
				SB.append("00");
				SB.append(j);
				break;
			case 2:
				SB.append("0");
				SB.append(j);
			case 3:
				SB.append(j);
				break;
			default:
				break;
			}
    	
		}
		System.out.println("Completed "+SB);
    	return new String(SB);
    }
}