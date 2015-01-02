package risk;

import java.util.List;

public class Territory {
	private int troops;
	private String name;
	private List<Territory> connections;
	private String faction;
	
	/**
	 * Constructor builds the object with name initalized, 
	 * troops set to 0, and an empty connections list.
	 * 
	 * @param name	the name of the territory.
	 **/
	public Territory(String name){
		troops = 0;
		this.name = name; 
		connections = new ArrayList<Territory>();
		faction = "None";
	}

	// Getters and Setters
	public int getTroops() { return troops; }

	public String getName() { return name; }

	public void setTroops(int num) { this.troops = num; }
	
	public void setFaction(String faction) { this.faction = faction; }
	
	public String getFaction() { return faction; }
	
	public void addConnections(Territory terra){ connections.add(terra); }
	
	public List<Territory> getConnections() { return connections; }
	
	// toString
	@Override
	public String toString(){ return name; }
	
}
