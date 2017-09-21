import java.util.ArrayList;

public class Pet extends Friend{
	
	private ArrayList<Person> owners;
	
	public Pet(String name, Image image){
		super(name,image);
		this.owners = new ArrayList<Person>();
	}
	
	public void setOwners(ArrayList<Person> owners){
		this.owners = owners;
	}
}	
