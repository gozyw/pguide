import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SortSceneries {
	public String sort(String popular, String popular2, String price, String score){
		Map<String, Scenery> sceneiries = ScenerysInfo.getSceneries();
		Map<String, Double> sceneriesScoreMap = new HashMap<String, Double>();
		List<Map.Entry<String, Double>> sceneriesScoreList = new ArrayList<Map.Entry<String,Double>>();
		
		int isPopular = 0;
		int isPrice = 0;
		int isScore = 0;
		
		if("1".equals(popular) || "1".equals(popular2)){
			//consider the number information of the scenery
			isPopular = 1;
		}
		
		if("1".equals(price)){
			//consider the number of price of the scenery
			isPrice = 300;
		}
		
		
		if("1".equals(score)){
			//consider the score of the scenery
			isScore = 1;
		}
		
		//if all is 0, then use the number of this scenery
		for(String idString : sceneiries.keySet()){
			Scenery scenery = sceneiries.get(idString);
			double result = scenery.getNumber()*isPopular + scenery.getPrice()*isPrice + scenery.getScore()*isScore;
			if(result == 0){
				result = scenery.getNumber();
			}
			sceneriesScoreMap.put(idString, result);
		}
		
		sceneriesScoreList.addAll(sceneriesScoreMap.entrySet());
		Collections.sort(sceneriesScoreList, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double>b){
				return a.getValue().compareTo(b.getValue());
			}
		});
		
		
		System.out.println("The score map: " + sceneriesScoreList);
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<sceneriesScoreList.size(); i++){
			buffer.append(sceneriesScoreList.get(i).getKey());
			if(i+1<sceneriesScoreList.size()){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
	
	public static void main(String[] args)throws Exception{
		String result = null;
		result = new SortSceneries().sort("1", "1", "1", "1");
		System.out.println(result);
		
		result = new SortSceneries().sort("1", "1", "1", "0");
		System.out.println(result);
		
		result = new SortSceneries().sort("1", "1", "0", "1");
		System.out.println(result);
		
		result = new SortSceneries().sort("1", "1", "0", "0");
		System.out.println(result);
		
		result = new SortSceneries().sort("0", "0", "1", "1");
		System.out.println(result);
		
		result = new SortSceneries().sort("0", "0", "1", "0");
		System.out.println(result);
		
		result = new SortSceneries().sort("0", "0", "0", "1");
		System.out.println(result);
		
		result = new SortSceneries().sort("0", "0", "0", "0");
		System.out.println(result);
	}
}
