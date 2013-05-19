import java.util.List;



public class Scenery {
	private String id;
	private String name;
	private double latitude;
	private double longitude;
	private double price;
	private int score;
	private int number;
	private int time;
	private String description;
	private List<String> comments;
	
	public Scenery(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(id).append("&");
		buffer.append(name).append("&");
		buffer.append(latitude).append("&");
		buffer.append(longitude).append("&");
		buffer.append(price).append("&");
		buffer.append(score).append("&");
		buffer.append(number).append("&");
		buffer.append(time).append("&");
		buffer.append(description);
		for(int i=0; i<comments.size(); i++){
			buffer.append("&").append(comments.get(i));
		}
		return buffer.toString();
	}
}
