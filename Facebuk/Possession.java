
public class Possession extends Profile {
	
	private float price;
	private Person owner;
	
	public Possession (String name, Image image, float price){
		super(name, image);
		this.price = price;
	}
	
	public void setOwner(Person owner){
		this.owner = owner;
	}
}
