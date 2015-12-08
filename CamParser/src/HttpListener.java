import java.io.BufferedReader;
import java.io.InputStreamReader;


// HttpListener receives the response from the Tesseract server
// The responses are queued into the TCPClient class so that they can be sent
//   to the Android device.

public class HttpListener extends Thread {
	private boolean isListening = true;
	private TcpClient tcpClient = null;
	
	public HttpListener(TcpClient client){
		tcpClient = client;
	}
	
	@Override
	public void run(){
		while(isListening) {
			listen();
		}
	}
	
	public void stopListening() {
		isListening = false;
	}
	
	public void listen() {
		// This is straight from the email.
		//read the response
        entity = response.getEntity();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                            								entity.getContent()), 65728);
            String line = null;

            while ((line = reader.readLine()) != null) {
                    sb.append(line);
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
	}
}
