import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Receives the images coming from the Android
// and places the image filepath into a queue.
public class TcpListener extends Thread {
	public InetAddress IPAddress;
	public int Port;
	
	public ServerSocket socket;
	public Socket clientSocket;
	
	public SimpleDateFormat folderFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public String folder;

	private static LinkedList<String> imageFileQueue = new LinkedList<String>();
	
	public TcpListener(InetAddress ip, int port) throws Exception {
		IPAddress = ip;
		Port = port;
		socket = new ServerSocket(port, 100, ip);
		folder = folderFormat.format(System.currentTimeMillis());
	}
	
	public String dequeueImageFile() {
		return imageFileQueue.poll();
	}
	
	public boolean imageFileQueueIsEmpty() {
		return imageFileQueue.isEmpty();
	}
	
	private void queueImageFile(String file) {
		imageFileQueue.addLast(file);
	}
	
	public void Listen() throws IOException, InterruptedException {
		System.out.println("Now listening for images: " + IPAddress.getHostName());
		while(true) {
			clientSocket = socket.accept();
			try {
				getMessage();
			} catch (Exception e) {
				System.out.println("Failed to get message");
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		try {
			Listen();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void Stop() throws IOException {
		try {
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	
	static InetAddress phoneAddress = null;
	static int imageIndex = 0;
	
	public byte[] getMessage() throws IOException {
		byte[] message = new byte[4096];
		DataInputStream input = new DataInputStream(clientSocket.getInputStream());
		phoneAddress = clientSocket.getInetAddress();
		int fileSize = input.readInt();
		System.out.println("Receiving image of length: " + fileSize);
		
		File f = new File(folder);
		f.mkdir();
		
		String filename = this.folder + "/" + "image"+ imageIndex++ +".jpg";
		queueImageFile(filename);
		FileOutputStream fos = new FileOutputStream(filename);
		
		int count;
		try {
			while ((count = input.read(message)) > 0)
			{
				fos.write(message, 0, count);
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