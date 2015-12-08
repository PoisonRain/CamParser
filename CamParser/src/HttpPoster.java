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
	public static final String tesseractAddress2 = "";
	
	//http://stackoverflow.com/questions/17173435/send-image-file-using-java-http-post-connections
	public static String postImage(String filename) throws IOException {
//	    HttpClient httpclient = new DefaultHttpClient();
//	    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//
//	    HttpPost httppost = new HttpPost(tesseractAddress1);
//	    File file = new File("./" + filename);
//
//	    MultipartEntity mpEntity = new MultipartEntity();
//	    ContentBody cbFile = new FileBody(file, "image/jpeg");
//	    mpEntity.addPart("userfile", cbFile);
//
//
//	    httppost.setEntity(mpEntity);
//	    System.out.println("executing request " + httppost.getRequestLine());
//	    HttpResponse response = httpclient.execute(httppost);
//	    HttpEntity resEntity = response.getEntity();
//
//	    System.out.println(response.getStatusLine());	    
//	    if (resEntity != null) {
//	      System.out.println(EntityUtils.toString(resEntity));
//	    }
//	    if (resEntity != null) {
//	      resEntity.consumeContent();
//	    }
//
//	    httpclient.getConnectionManager().shutdown();    
	    
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
	    
	    return sb.toString();
	}
}