package train;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class main {
	static ArrayList<line> lines= new ArrayList<>();
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		read("D:\\homework\\软件工程\\地铁线路信息.txt");
		System.out.println(lines.get(0).getLineName());
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("1:查找该线路站点   2：查找某站到某站的最短距离  请输入操作序号:");
		int chance = scanner.nextInt();
		switch(chance) {
			case 1:{
				System.out.print("输入想查询的线路:");
				String search = scanner.next();
				for(line line2 : lines) {
					if(line2.getLineName().equals(search)) {
						for(int i = 1; i < line2.getStations().size(); i++	) {
							System.out.print(line2.getStations().get(i)+ " " );
						}
					}
				}
			}
			case 2:{
				
			}
		}
		
		
		
		
	}

	public static void read(String name) {
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(name));
			BufferedReader br = new BufferedReader(reader);
			HashMap<String,Station> map = new HashMap<>();
			String a = null;
			while((a = br.readLine()) != null) {
				line line1 = new line();
	
				String[] stations = a.split(" ");
				line1.setLineName(stations[0]);
				
				if (stations.length < 2){
                    System.out.print("本线路无可乘站");
                    return;
                }
				for(int i = 0; i < stations.length; i++) {
					line1.add(stations[i]);
				}
				lines.add(line1);
				for (int j = 1; j < stations.length - 1; j++) {
                    Station station1 = new Station();//当前line中的station
                    Station station2 = new Station();
                    if (map.containsKey(stations[j])) {//如果map中已经有该站点则把这个站点拿出来处理
                        station1 = map.get(stations[j]);
                        map.remove(stations[j]);
                    } else {
                        station1.setName(stations[j]);
                        station1.setVisited(false);
                    }

                    if (map.containsKey(stations[j + 1])) {//map中已经有该站后面的站点拿出来处理
                        station2 = map.get(stations[j + 1]);
                        map.remove(stations[j + 1]);
                    } else {
                        station2.setName(stations[j + 1]);
                        station2.setVisited(false);
                    }
                    if (!station1.getLine().contains(line1.getLineName()))//如果当前站未加入line中，则在line中当前站名
                        station1.AddStationLine(line1.getLineName());
                    if (!station2.getLine().contains(line1.getLineName()))//如果当前站未加入line中，则在line中当前站名
                        station2.AddStationLine(line1.getLineName());
                    if (!station1.getLinkStations().contains(station2))
                        station1.AddLinkStation(station2);
                    if (!station2.getLinkStations().contains(station1))
                        station2.AddLinkStation(station1);

                    station1.setPreStation(station1.getName());
                    station2.setPreStation(station2.getName());
                    map.put(stations[j], station1);//把station1重新放回map
                    map.put(stations[j + 1], station2);//把station2重新放回map
                    if (!line1.getStations().contains(station1.getName())) {
                    	line1.stationAdd(station1.getName());
                    }
                    if (!line1.getStations().contains(station2.getName())) {
                    	line1.stationAdd(station2.getName());
                    }
                }
                lines.add(line1);//把线路加入LineSet
            }
            br.close();		
		}catch(Exception e){
            e.printStackTrace();
        }
		
	}

	
}
