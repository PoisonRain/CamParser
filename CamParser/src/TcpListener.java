import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TcpListener extends Thread {
	public InetAddress IPAddress;
	public int Port;
	
	public ServerSocket socket;
	public Socket clientSocket;
	
	public TcpListener(InetAddress ip, int port) throws Exception {
		IPAddress = ip;
		Port = port;
		socket = new ServerSocket(port, 100, ip);
	}
	
	public void Listen() throws IOException, InterruptedException {
		while(true) {
			clientSocket = socket.accept();
//			tcpsocket.HandleTCPClientComm(clientSocket);
			
			//PUT HANDLER CODE HERE
			//System.out.println(new String(getMessage()));
			getMessage();

		}
	}
	
	public void run() {
		try {
			Listen();
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void Stop() throws IOException {
		try {
			socket.close();
		}
		catch (SocketException e) {
			//already closed
		}
	}
	
	
	static InetAddress phoneAddress;
	static int imageIndex = 0;
	static List<String> imageFiles = new ArrayList<String>();
	public byte[] getMessage() throws IOException {
		byte[] message = new byte[4096];
		DataInputStream input = new DataInputStream(clientSocket.getInputStream());
		phoneAddress = clientSocket.getInetAddress();
		int fileSize = input.readInt();
		System.out.println("Length of message: " + fileSize);
		
		String filename = "image"+ imageIndex++ +".jpg";
		imageFiles.add(filename);
		FileOutputStream fos = new FileOutputStream(filename);
		
		
		int count;
		try {
			while ((count = input.read(message)) > 0)
			{
				fos.write(message, 0, count);
				System.out.println("Wrote " + count + " bytes.");
			}
		}
		catch (Exception e) {
			System.out.println("Broke, with Exception: " + e.toString());
			e.printStackTrace();
		}
		finally {
			fos.close();
		}
		System.out.println("Wrote entire file.");
		
		return message;
	}
}