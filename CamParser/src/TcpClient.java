import java.io.*;
import java.net.*;

public class TcpClient {

	public Socket socket;
	
	TcpClient(InetAddress ip, int Port) throws IOException {
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
	
}