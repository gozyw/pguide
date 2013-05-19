import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScenerysInfo {
	public Map<String, Scenery> sceneries = new HashMap<String, Scenery>();
	private static ScenerysInfo scenerysInfo;
	
	private ScenerysInfo()throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(new File("data/scenery.txt")));
		String item = null;
		sceneries.clear();
		while((item = reader.readLine()) != null){
			String[] tmpArray = item.split(" ");
			if(tmpArray.length != 8){
				System.out.println("The format of scenery is wrong" + item);
			}
			String id = tmpArray[0];
			String name = tmpArray[1];
			double latitude = Double.parseDouble(tmpArray[2]);
			double longtitude = Double.parseDouble(tmpArray[3]);
			double price = Double.parseDouble(tmpArray[4]);
			int score = Integer.parseInt(tmpArray[5]);
			int number = Integer.parseInt(tmpArray[6]);
			int time = Integer.parseInt(tmpArray[7]);
			Scenery scenery = new Scenery();
			scenery.setId(id);
			scenery.setName(name);
			scenery.setLatitude(latitude);
			scenery.setLongitude(longtitude);
			scenery.setPrice(price);
			scenery.setScore(score);
			scenery.setNumber(number);
			scenery.setTime(time);
			
			List<String> comments = new ArrayList<String>();
			//get comments info
			try{
				BufferedReader commentsReader = new BufferedReader(new FileReader(new File("data/"+id+".txt")));
				String comment = null;
				while((comment = commentsReader.readLine()) != null){
					comments.add(comment);
				}
				
			}
			catch(IOException e){
				System.out.println("No comments for " + id);
			}
			
			if(comments.size()>0){
				String description = comments.get(0);
				scenery.setDescription(description);
				comments.remove(0);
			}
			
			scenery.setComments(comments);
			sceneries.put(id, scenery);
		}
	}
	
	/**
	 * Return the single sceneryInfo instance
	 * @return
	 */
	public static ScenerysInfo getInstance(){
		if(scenerysInfo == null){
			try{
				scenerysInfo = new ScenerysInfo();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return scenerysInfo;
	}
	
	public static Map<String, Scenery> getSceneries(){
		return getInstance().sceneries;
	}
	
	
	public static Scenery getScenery(String id){
		return getInstance().sceneries.get(id);
	}
}
