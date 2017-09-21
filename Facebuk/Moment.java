import java.util.ArrayList;

public class Moment extends Profile{
	
	private ArrayList<Person> participants;
	private ArrayList<Float> smileValues;
	
	public Moment(String name, Image image, ArrayList participants, ArrayList smileValues){
		super(name, image);
		this.participants = participants;
		this.smileValues = smileValues;
	}

	public ArrayList<Person> getParticipants(){
		return participants;
	}
	
	public ArrayList<Float> getSmileValues(){
		return smileValues;
	}
}
