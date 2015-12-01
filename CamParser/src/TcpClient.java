import java.io.*;
import java.net.*;

public class TcpClient {

	public Socket socket;
	public InetAddress address;
	public int port;
	
	TcpClient(InetAddress ip, int Port) throws IOException {
		address = ip;
		port = Port;
		socket = new Socket(ip, Port);
	}
	
	public void start() throws IOException {
		socket.bind(socket.getRemoteSocketAddress());
		socket.connect(socket.getRemoteSocketAddress());
	}
	
	public void send(byte[] b) throws IOException {
		//System.out.println("TCP: "+Extensions.hexStr(b));
		socket.getOutputStream().write(b);
	}
	
	public void close() throws IOException {
		socket.close();
	}
	
	public void restart() throws IOException {
		socket.close();
		socket = new Socket(address, port);
	}
	
}