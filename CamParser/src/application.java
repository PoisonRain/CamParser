import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;


public class application {

	static int port = 8150;
	static InetAddress ipaddress;
	static TcpListener listener;
	static TcpClient speaker;
	
	public static Scanner sc;
	
	public static void main(String[] args) {
		try {
			sc = new Scanner(System.in);
			//ipaddress = InetAddress.getLocalHost();
			ipaddress = InetAddress.getByName("144.39.172.173");
			 
			listener = new TcpListener(ipaddress, port);
			//speaker = new TcpClient(ipaddress, port);
			listener.start();
			int i=0;
			
			StateMachine sm = new StateMachine();
			Path p = sm.findPath(sm.nodes.get(0), sm.nodes.get(sm.nodes.size()-1));
			//System.out.println(p.toString());
			for(State s : p.states) {
				//System.out.println(p.getDirection(s.label));
			}
			
			while(true) {
				//LISTEN
				//Thread.sleep(1000);
				//speaker.send(("Hello my child." + i++).getBytes());
				//speaker.restart();
				System.out.println(sm.getDirection(sc.next().trim()));
			}
			
			
		}
		catch (Exception e) {
			System.out.println("I died.");
			e.printStackTrace();
			try {
				listener.Stop();
				listener.interrupt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}
	
	public static void processMessage(String message) {
		
	}

}
