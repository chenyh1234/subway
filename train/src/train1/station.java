package train1;

import java.util.ArrayList;
import java.util.List;


public class station {

    private String StationName;  //վ����
    private List<String> Line = new ArrayList<String>();  //������·������վ�ж�����
    private List<station> LinkStations= new ArrayList<station>();  //��֮���ڵ�վ��
    private boolean visited;//�Ƿ���ʹ���վ��
    private String preStation;//��վ֮ǰ���ʵ�վ��
    private int distance=0;//��վ�������վ��վ��
    private boolean change;//�Ƿ񻻳� Ĭ��Ϊfalse ���ڽ��

    public boolean ischange() {
        return change;
    }

    public void setchange(boolean change) {
        this.change = change;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void AddStationLine(String name){
        Line.add(name);
    }

    public void AddLinkStation(station sta){
        LinkStations.add(sta);
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public List<String> getLine() {
        return Line;
    }

    public void setLine(List<String> line) {
        Line = line;
    }

    public List<station> getLinkStations() {
        return LinkStations;
    }

    public void setLinkStations(List<station> linkStations) {
        LinkStations = linkStations;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getPreStation() {
        return preStation;
    }

    public void setPreStation(String preStation) {
        this.preStation = preStation;
    }
}