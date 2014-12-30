package risk;

import java.util.List;

public class Territory {
	private int troops;
	private String name;
	private List<String> connections;
	
	public Territory(String name){
		troops = 0;
		this.name = name;
	}

	public int getTroops() {
		return troops;
	}

	@Override
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	public void setTroops(int num) {
		this.troops = num;		
	}
	
}
