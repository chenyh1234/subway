package train1;

import java.util.ArrayList;
import java.util.List;

public class line {

    private String LineName;  //����
    private List<String> stations= new ArrayList<String>();   //����·������վ

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public void stationAdd(String name){
        stations.add(name);
    }
}