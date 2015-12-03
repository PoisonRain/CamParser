import java.io.IOException;
import java.net.InetAddress;


public class application {

	static int port = 8150;
	static InetAddress ipaddress;
	static TcpListener listener;
	static TcpClient speaker;
	
	
	public static void main(String[] args) {
		try {
			ipaddress = InetAddress.getLocalHost();
			 
			listener = new TcpListener(ipaddress, port);
			speaker = new TcpClient(ipaddress, port);
			listener.start();
			//speaker.start();
			int i=0;
			
			StateMachine sm = new StateMachine();
			Path p = sm.findPath(sm.nodes.get(0), sm.nodes.get(sm.nodes.size()-1));
			System.out.println(p.toString());
			
			while(true) {
				//LISTEN
				Thread.sleep(1000);
				speaker.send(("Hello my child." + i++).getBytes());
				speaker.restart();
			}
			
			
		}
		catch (Exception e) {
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
