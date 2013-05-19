import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SceneryDistance {
	private Map<String,Integer> distance = new HashMap<String, Integer>();
	
	private static SceneryDistance sceneryDistance;
	
	
	private SceneryDistance() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/distance.txt")));
		distance.clear();
		
		String line = null;
		int count = 1;
		while((line = reader.readLine()) != null){
			String[] tmpArray = line.split(" ");
			for(int i=1; i<=tmpArray.length; i++){
				String key = count+"-"+i;
				int value = Integer.parseInt(tmpArray[i-1]);
				distance.put(key, value);
			}
			count++;
		}
	}
	
	
	
	public static SceneryDistance getInstance(){
		if(sceneryDistance == null){
			try {
				sceneryDistance = new SceneryDistance();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sceneryDistance;
	}
	
	
	/**
	 * Get the distance of two nodes.
	 * @param idPair
	 * @return
	 */
	public static Integer getDistance(String idPair){
		return getInstance().distance.get(idPair);
	}
	
	
	public static void main(String[] args)throws Exception{
		System.out.println(getInstance().distance);
	}
}
