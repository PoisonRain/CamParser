import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpPoster {
	public static final String tesseractAddress1 = "http://129.123.7.140:10000/OcrProcessorServlet/OcrServlet";
	public static final String tesseractAddress2 = "http://129.123.7.222:8080/OcrProcessorServlet/OcrServlet";
	
	public static String postImage(String filename) throws IOException {	    
	    //Http Client for the sending the request
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(tesseractAddress1);

        //Create a Multipart entity to attach image
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        //add image to the multipart entity. The name of the file should be "photo"
        builder.addBinaryBody("photo", new File(filename));

        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        //send the post request
        HttpResponse response = httpClient.execute(httpPost);

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
        } catch (IOException e) {
                e.printStackTrace();
        } catch (Exception e) {
                e.printStackTrace();
        }

        System.out.println("Tesseract-OCR response: " + sb.toString());
	    
	    return sb.toString().toUpperCase();
	}
}