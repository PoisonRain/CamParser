import java.io.IOException;
import java.net.*;

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
	
	public byte[] getMessage() throws IOException {
		byte[] message = new byte[4096];
		clientSocket.getInputStream().read(message);
		//Extensions.debug("TcpListener heard message.", 2);
		return message;
	}
}