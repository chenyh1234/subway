package train;

import java.util.ArrayList;
import java.util.List;

public class Station {
	private String name;
	private List<String> line = new ArrayList<String>();
	private List<Station> linkStations = new ArrayList<Station>();
	private boolean visited;
    private String lastedStation;
    private int distance = 0;
    private boolean change;
	public String getName() {
		return name;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public String getLastedStation() {
		return lastedStation;
	}
	public void setLastedStation(String lastedStation) {
		this.lastedStation = lastedStation;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public boolean isChange() {
		return change;
	}
	public void setChange(boolean change) {
		this.change = change;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getLine() {
		return line;
	}
	public void setLine(List<String> line) {
		this.line = line;
	}
	public List<Station> getLinkStations() {
		return linkStations;
	}
	public void setLinkStations(List<Station> linkStations) {
		this.linkStations = linkStations;
	}
	public void AddStationLine(String name){
        line.add(name);
    }

    public void AddLinkStation(Station sta){
        linkStations.add(sta);
    }
    public void setPreStation(String lastedStation) {
        this.lastedStation = lastedStation;
    }
	
}
