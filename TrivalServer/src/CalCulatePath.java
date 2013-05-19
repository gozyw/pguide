import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class is used to calculate the path of 
 * user
 * @author zfz
 *
 */
public class CalCulatePath {
	public static String calculate(int days, String[] ids){
		Map<String,Integer> resultMap = new HashMap<String, Integer>();
		
		int hours = days * 24;
		Map<String, Scenery> sceneries = ScenerysInfo.getSceneries();
		for(int i=0; i<ids.length; i++){
			for(int j=0; j<ids.length; j++){
				if(i==j){
					continue;
				}
			}
		}
		
		List<String> idList = new ArrayList<String>();
		for(int i=0; i<ids.length; i++){
			idList.add(ids[i]);
		}
		
		
		//i means the number of sceneries on this path
		for(int i=1; i<=ids.length; i++){
			for(int base=0; base<ids.length-i;base++){
				int time = ScenerysInfo.getScenery(""+ids[base]).getTime();
				for(int j=base+1; j<=i+base; j++){
					if(ids[j].compareTo(ids[j-1])>0){
						time += SceneryDistance.getDistance(ids[j]+"-"+ids[j-1]);
					}
					else if(ids[j].compareTo(ids[j-1])<0){
						time += SceneryDistance.getDistance(ids[j-1]+"-"+ids[j]);
					}
					else{
						continue;
					}
					time += ScenerysInfo.getScenery(ids[j]).getTime();
				}
				if(time <= hours){
					StringBuffer buffer = new StringBuffer();
					for(int j=base; j<=i+base; j++){
						buffer.append(ids[j]).append(",");
					}
					String key = buffer.substring(0, buffer.length()-1);
					resultMap.put(key, time);
				}
			}
		}
		//System.out.println(resultMap);
		
		List<Map.Entry<String, Integer>> resultList = new ArrayList<Map.Entry<String,Integer>>();
		resultList.addAll(resultMap.entrySet());
		Collections.sort(resultList, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		
		System.out.println(resultList);
		StringBuffer res = new StringBuffer();
		for(int i=0; i<resultList.size();i++){
			res.append(resultList.get(i).getKey());
			if(i<2 && i+1<resultList.size()){
				res.append("&");
			}
			
			if(i>=2){
				break;
			}
		}
		return res.toString();
	}
	
	
	public static void main(String[] args)throws Exception{
		String[] ids = new String[]{"1","2","7","6","9","3"};
		System.out.println(CalCulatePath.calculate(1, ids));
	}
}
