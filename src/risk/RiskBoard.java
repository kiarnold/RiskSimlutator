package risk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RiskBoard {
	private List<Territory> territories;
	
	public RiskBoard(){ territories = new ArrayList<Territory>();}

	public void setup(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			// read in to set up
			while(br.ready()){
				String input = br.readLine();
				// regions
				if (input.contains("Region: ")){
					// Store territories inside a region object?
					this.setupRegions(br);
				}else if(input.contains("Routes: ")){
					// routes
					this.setupRoutes(br);
				}
			}
			
		} catch (FileNotFoundException e) {
			// Fix this to handle better?
			System.out.println("File not found: ");
			e.printStackTrace();
		} catch (IOException e){
			// Fix this to handle better?
			System.out.println("File read error: ");
			e.printStackTrace();
		}
	}

	private void setupRoutes(BufferedReader br) throws IOException {
		String input = br.readLine();
		
		while(!input.equals("")){
			//TODO implement
			break;
		}
	}

	private void setupRegions(BufferedReader br) throws IOException {
		while(br.ready()){
			String input = br.readLine();
			if(input.equals("")) return;
			else territories.add(new Territory(input));
		}
	}

	public List<Territory> getTerritories() {return territories;}

	public void addTroops(String territory, int num) {
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				terra.setTroops(terra.getTroops() + num);
			}
		}
	}

	public int getTroops(String territory) {
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				return terra.getTroops();
			}
		}
		return 0;
	}

	public void removeTroops(String territory, int num) {
		for(Territory terra : territories){
			if (terra.getName().equals(territory)){
				int troops = terra.getTroops() - num;
				if(troops > 0){
					terra.setTroops(troops);
				} else {
					terra.setTroops(0);
				}
			}
		}
	}

}
