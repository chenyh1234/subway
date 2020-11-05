package train1;

import java.io.*;
import java.util.*;

import train.line;

public class subway {

    public static HashMap<String,station> map = new HashMap<>();//方便查找站点信息
    public static List<line> LineSet= new ArrayList<>();//方便查找路线

    public static void read(String name) {//读入线路数据
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(name));
            BufferedReader br = new BufferedReader(reader);
            String read = null;

            while((read = br.readLine()) != null) {  //直到读到空行位置
                line line = new line();//当前存的line

                String[] stations = read.split(" ");//读入的line对应的所有station，并通过空格分割成字符串数组
                line.setLineName(stations[0]);//修改其线路名

                for (int j = 1; j < stations.length - 1; j++) {
                    station station1 = new station();//当前line中的station
                    station station2 = new station();
                    if (map.containsKey(stations[j])) {//如果map中已存在该站点
                        station1 = map.get(stations[j]);
                        map.remove(stations[j]);
                    } else { 							//否则创造一个站点，并初始化，用于开始阶段
                        station1.setStationName(stations[j]);
                        station1.setVisited(false);
                    }

                    if (map.containsKey(stations[j + 1])) {//如果map中已存在该站点的后一站
                        station2 = map.get(stations[j + 1]);
                        map.remove(stations[j + 1]);
                    } else { 								//否则创造一个站点，并初始化，用于开始阶段
                        station2.setStationName(stations[j + 1]);
                        station2.setVisited(false);
                    }
                    if (!station1.getLine().contains(line.getLineName()))//如果当前站未加入line中，则在line中当前站名
                        station1.AddStationLine(line.getLineName());
                    if (!station2.getLine().contains(line.getLineName()))//如果当前站未加入line中，则在line中当前站名
                        station2.AddStationLine(line.getLineName());
                    if (!station1.getLinkStations().contains(station2))
                        station1.AddLinkStation(station2);
                    if (!station2.getLinkStations().contains(station1))
                        station2.AddLinkStation(station1);

                    station1.setPreStation(station1.getStationName());
                    station2.setPreStation(station2.getStationName());
                    map.put(stations[j], station1);//把station1重新放回map
                    map.put(stations[j + 1], station2);//把station2重新放回map
                    if (!line.getStations().contains(station1.getStationName())) {
                        line.stationAdd(station1.getStationName());
                    }
                    if (!line.getStations().contains(station2.getStationName())) {
                        line.stationAdd(station2.getStationName());
                    }
                }
                LineSet.add(line);//把线路加入总的LineSet中，用于输出
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void bfs(String start, String end){
        for (String a :map.keySet()){  //将每个节点进行初始化操作
            map.get(a).setVisited(false);
            map.get(a).setDistance(0);
        }
        station now = new station();
        Queue<String> queue = new LinkedList<>();//使用队列，实现bfs算法

        now = map.get(start);
        queue.add(start);
        while(!queue.isEmpty()){
            String nowName = queue.remove();
            map.get(nowName).setVisited(true);
            if (now.getStationName().equals(end)){
                break;
            }
            for (station station1 :map.get(nowName).getLinkStations()){
                if(!map.get(station1.getStationName()).isVisited()){//未访问过的临近站点
                    map.get(station1.getStationName()).setPreStation(nowName);//为preStation赋值
                    map.get(station1.getStationName()).setDistance(map.get(nowName).getDistance()+1);//临近站的距离为本站距离加1
                    queue.offer(station1.getStationName());
                }
            }
        }
    }

    public static void PrintPath(String start,String end){
        List<String> path = new ArrayList<>();
        Stack<String> printline = new Stack<>();
        int num = 1;//第几站
        int cnt = 0;//换乘次数
        String str = end;
        while(!str.equals(start)){
            path.add(str); 
            printline.push(str);
            str = map.get(str).getPreStation();
        }
        path.add(str);//把start放入path
        printline.push(str);
        for (int i=1;i<path.size()-1;i++){
            if (map.get(path.get(i)).getLine().size() == 1){
                continue;
            }
            String temp1 = null;
            String temp2 = null;
            //每三战判断一次是否有换乘站
            for (String str1 : map.get(path.get(i)).getLine()){//本站与前一站的共同拥有的线路存在temp1中
                boolean flag = false;
                for (String str2 :map.get(path.get(i-1)).getLine()){
                    if (str1.equals(str2)){
                        temp1 = temp1 + str1;
                        flag = !flag;
                        break;
                    }
                }
                if (flag) break;
            }
            for (String str1 : map.get(path.get(i)).getLine()){//后一站与本站的共同拥有的线路存在temp2中
            	boolean flag = false;
                for (String str2 :map.get(path.get(i+1)).getLine()){
                    if (str1.equals(str2)){
                        temp2 = temp2 + str1;
                        flag = !flag;
                        break;
                    }
                }
                if (flag) break;
            }
            if (!temp1.equals(temp2))//若temp1和temp2两线路不同则本站为转乘站
                map.get(path.get(i)).setchange(true);                                                                                                                                
        }//判断path中的换乘站
        System.out.println("共"+path.size()+"站");
        while(!printline.empty()){
            String printStation = printline.pop();
            if(num == 1){
                for (String strnow : map.get(printStation).getLine()){
                    for (String nextStation : map.get(path.get(path.size()-num-1)).getLine()){
                        if (strnow.equals(nextStation)) {
                            System.out.println("当前线为："+strnow);
                            
                        }
                    }
                }
            }
            if (map.get(printStation).ischange()){
                String nowline ="";
                for (String strnow : map.get(printStation).getLine()){
                    //path.get(path.size()-numStation)为换乘站下一站 两站共有的线就是换乘线
                    for (String nextStation : map.get(path.get(path.size()-num-1)).getLine()){
                        if (strnow.equals(nextStation))
                            nowline = nowline + strnow;
                    }
                }
                cnt++;
                System.out.println("");
                System.out.println("转线为" + nowline);
            }
            System.out.print(printStation + " ");
            num++;
        }
    }

    public static void main(String[] ards){

        read("D:\\homework\\软件工程\\地铁线路信息.txt"); // 调用读取文件函数
        Scanner scanner = new Scanner(System.in);
		System.out.print("1:查找该线路站点   2：查找某站到某站的最短距离  请输入操作序号:");
		int chance = scanner.nextInt();
        switch(chance) {
		case 1:{
			System.out.print("输入想查询的线路:");
			String search = scanner.next();
			for(line line2 : LineSet) {
				if(line2.getLineName().equals(search)) {
					for(int i = 1; i < line2.getStations().size(); i++	) {
						System.out.print(line2.getStations().get(i)+ " " );
					}
				}
			}
			break;
		}
		case 2:{
			System.out.print("请输入起点站：");
            String start = scanner.next();
            System.out.print("请输入终点站：");
            String end = scanner.next();
            
            if (!map.containsKey(start)){//判断起点站是否存在
            	System.out.println("起点站不存在");
            	break;
            }
            if (!map.containsKey(end)){//判断终点站是否存在
            	System.out.println("终点站不存在");
            	break;
            }
            if (start.equals(end)){//判断起点站和终点站是否相同
                System.out.print("起点站与终点站相同 本站为" + end + "不需要乘坐地铁");
                break;
            }
            bfs(start,end);
            PrintPath(start,end);
            break;
		}
		default:{
			System.out.println("请重新选择");
			break;
		}
	}
        return;
    }
}