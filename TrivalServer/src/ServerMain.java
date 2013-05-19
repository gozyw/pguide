import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Accept user requests
 * @author zfz
 *
 */
public class ServerMain {
	private ServerSocket serverSocket;
	
	/**
	 * Start the boss server
	 */
	public void startServer(){
		try {
			serverSocket = new ServerSocket(6000);
			System.out.println("Server Start Successfully!");
			while (true) {
				listen();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * listen to the request
	 */
	private void listen(){
		try{
			Socket socket = serverSocket.accept();
			System.out.println("A new client");
			if(socket != null){
				new ClientThread(socket).start();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args)throws Exception{
		new ServerMain().startServer();
	}
}
