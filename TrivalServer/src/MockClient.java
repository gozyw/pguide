import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Mock the behavior of a client
 * @author zfz
 *
 */
public class MockClient {
	public static void main(String[] args)throws Exception{
		URL url = new URL("http://localhost:6000?hello=A:北京$1$1$3$4");
		URLConnection connection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String tmp = null;
		
		while((tmp = reader.readLine()) != null){
			System.out.println(tmp);
		}
	}
}
