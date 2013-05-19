import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * The worker thread for a request
 * @author zfz
 *
 */
public class ClientThread extends Thread{
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	public ClientThread(){}
	/**
	 * 
	 * @param socket specify the client socket
	 */
	public ClientThread(Socket socket){
		this.socket = socket;
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try{
			
			String line = reader.readLine();
			System.out.println(line);
			
			String tag = line.substring(0,line.indexOf(";"));
			if("A".equalsIgnoreCase(tag)){
				//calculate the path
				String tmp = line.substring(line.indexOf(";")+1, line.length());
				String[] tmpArrays = tmp.split("&");
				System.out.println("Length:" + tmpArrays.length);
				if(tmpArrays.length != 5){
					System.out.println("The argument for A is wrong");
				}
				String city = tmpArrays[0];
				String res = new SortSceneries().sort(tmpArrays[1], tmpArrays[2], tmpArrays[3], tmpArrays[4]);
				writer.write(res);
			}
			else if("B".equalsIgnoreCase(tag)){
				String id = line.substring(line.indexOf(";")+1);
				Scenery scenery = ScenerysInfo.getScenery(id);
				writer.write(scenery.toString());
			}
			else if("C".equalsIgnoreCase(tag)){
				String tmp = line.substring(line.indexOf(";")+1);
				String day = tmp.substring(0,tmp.indexOf("&"));
				String idsTmp = tmp.substring(line.indexOf("&")+1);
				String[] ids = idsTmp.split("&");
				//writer.write("5,6,7,8&2,3,4,5&2,6,7,8,9");
				String res = CalCulatePath.calculate(Integer.parseInt(day), ids);
				writer.write(res);
			}
			writer.flush();
			reader.close();
			writer.close();
			socket.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new ClientThread().run();
	}
}
