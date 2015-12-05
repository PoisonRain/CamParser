import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class application {

	static int port = 8150;
	static InetAddress ipaddress;
	static TcpListener listener;
	static TcpClient speaker;
	
	public static Scanner sc;
	
	public static void main(String[] args) {
		FileOutputStream out = null;
		try {
			SimpleDateFormat logformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			File logfile = new File(logformat.format(System.currentTimeMillis())+".txt");
			out = new FileOutputStream(logfile);
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
				String direction = sm.getDirection(sc.next().trim());
				System.out.println(direction);
				out.write((direction+"\n").getBytes());
				if(TcpListener.phoneAddress != null) {
					TcpClient toPhone = new TcpClient(TcpListener.phoneAddress, TcpClient.phonePort);
					toPhone.sendLetter(direction);
					toPhone.close();
				}
			}			
		}
		catch (Exception e) {
			//e.printStackTrace();
			try {
				listener.Stop();
				listener.interrupt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				out.close();
				System.out.println("Closed output file.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
