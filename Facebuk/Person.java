import java.util.ArrayList;
import java.util.*;

public class Person extends Friend {
	
	private ArrayList<Pet> pets;
	private ArrayList<Possession> possessions;
	
	
	public Person(String name, Image image){
		super(name, image);
		this.pets = new ArrayList<Pet>();
		this.possessions = new ArrayList<Possession>();
	}
	
	public void setPossessions(ArrayList<Possession> possessions){
		this.possessions = possessions;
	}
	
	public void setPets(ArrayList<Pet> pets){
		this.pets = pets;
	}
	
	/*
	 * Finds the friend with whom the target person appears
	 * the most happy on average, over all the moments in which 
	 * they both share
	 */
	public Friend getFriendWithWhomIAmHappiest(){
		Friend friendWithWhomIAmHappiest = null;
		float happiest = 0;
		for(Friend friend: friends){
			int momentsShared = 0;
			float totalHappiness = 0;
			for(Moment moment: moments){
				if(moment.getParticipants().contains(friend)){
					momentsShared++;
					totalHappiness += moment.getSmileValues().get(moment.getParticipants().indexOf(this));
				}
			}
			final float averageHappiness = totalHappiness/momentsShared;
			if(averageHappiness > happiest){
				happiest = averageHappiness;
				friendWithWhomIAmHappiest = friend;
			}
		}
		return friendWithWhomIAmHappiest;
	}
	
	/*
	 * Finds the moment in which the average happiness of all
	 * participants is the highest
	 */
	public Moment getOverallHappiestMoment(){
		Moment happiestMoment = null;
		float happiest = 0;
		for(Moment moment: moments){
			float totalHappiness = 0;
			for(float smileValue: moment.getSmileValues()){
				totalHappiness += smileValue;
			}
			final float averageHappiness = totalHappiness/moment.getSmileValues().size();
			if(averageHappiness > happiest){
				happiest = averageHappiness;
				happiestMoment = moment;
			}
		}
		return happiestMoment;
	}
}
