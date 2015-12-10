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
			ipaddress = InetAddress.getByName("144.39.197.47");
			 
			listener = new TcpListener(ipaddress, port);
			listener.start();	
			
			StateMachine sm = new StateMachine();
			Path p = sm.findPath(sm.nodes.get(0), sm.nodes.get(sm.nodes.size()-1));
			System.out.println(p.toString());
//			for(State s : p.states) {
//				System.out.println(p.getDirection(s.label));
//			}
			
//			For testing
//			System.out.println(HttpPoster.postImage("letter-g.jpg"));
			
			while(true) {
				// If there is an image in the queue, send it to tesseract.
				// Await the response and then send it to the phone via TCP.
				// If there are no images, sleep the main thread.
				
				if(!listener.imageFileQueueIsEmpty()){
					System.out.println("imagefile queue is not empty");
					try {
						String filepath = listener.dequeueImageFile();
						String response = HttpPoster.postImage(filepath);
						//Queue the response to be sent back to the Android phone.
						String direction = sm.getDirection(response.trim());
						System.out.println("Found direction: " + direction);
						
						if(direction != null) {
							out.write((filepath + " | Recognized State: " + response.trim() + " | Direction: " + direction+"\n").getBytes());
							
							if(TcpListener.phoneAddress != null) {
								TcpClient toPhone = new TcpClient(TcpListener.phoneAddress, TcpClient.phonePort);
								toPhone.sendLetter(direction);
								toPhone.close();
							}
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("queue is empty, sleeping");
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}			
		} 
		catch (Exception e) {
			e.printStackTrace();
			try {
				listener.Stop();
				listener.interrupt();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		finally {
			try {
				out.close();
				System.out.println("Closed output file.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
