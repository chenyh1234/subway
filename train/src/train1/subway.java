package train1;

import java.io.*;
import java.util.*;

import train.line;

public class subway {

    public static HashMap<String,station> map = new HashMap<>();//�������վ����Ϣ
    public static List<line> LineSet= new ArrayList<>();//�������·��

    public static void read(String name) {//������·����
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(name));
            BufferedReader br = new BufferedReader(reader);
            String read = null;

            while((read = br.readLine()) != null) {  //ֱ����������λ��
                line line = new line();//��ǰ���line

                String[] stations = read.split(" ");//�����line��Ӧ������station����ͨ���ո�ָ���ַ�������
                line.setLineName(stations[0]);//�޸�����·��

                for (int j = 1; j < stations.length - 1; j++) {
                    station station1 = new station();//��ǰline�е�station
                    station station2 = new station();
                    if (map.containsKey(stations[j])) {//���map���Ѵ��ڸ�վ��
                        station1 = map.get(stations[j]);
                        map.remove(stations[j]);
                    } else { 							//������һ��վ�㣬����ʼ�������ڿ�ʼ�׶�
                        station1.setStationName(stations[j]);
                        station1.setVisited(false);
                    }

                    if (map.containsKey(stations[j + 1])) {//���map���Ѵ��ڸ�վ��ĺ�һվ
                        station2 = map.get(stations[j + 1]);
                        map.remove(stations[j + 1]);
                    } else { 								//������һ��վ�㣬����ʼ�������ڿ�ʼ�׶�
                        station2.setStationName(stations[j + 1]);
                        station2.setVisited(false);
                    }
                    if (!station1.getLine().contains(line.getLineName()))//�����ǰվδ����line�У�����line�е�ǰվ��
                        station1.AddStationLine(line.getLineName());
                    if (!station2.getLine().contains(line.getLineName()))//�����ǰվδ����line�У�����line�е�ǰվ��
                        station2.AddStationLine(line.getLineName());
                    if (!station1.getLinkStations().contains(station2))
                        station1.AddLinkStation(station2);
                    if (!station2.getLinkStations().contains(station1))
                        station2.AddLinkStation(station1);

                    station1.setPreStation(station1.getStationName());
                    station2.setPreStation(station2.getStationName());
                    map.put(stations[j], station1);//��station1���·Ż�map
                    map.put(stations[j + 1], station2);//��station2���·Ż�map
                    if (!line.getStations().contains(station1.getStationName())) {
                        line.stationAdd(station1.getStationName());
                    }
                    if (!line.getStations().contains(station2.getStationName())) {
                        line.stationAdd(station2.getStationName());
                    }
                }
                LineSet.add(line);//����·�����ܵ�LineSet�У��������
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void bfs(String start, String end){
        for (String a :map.keySet()){  //��ÿ���ڵ���г�ʼ������
            map.get(a).setVisited(false);
            map.get(a).setDistance(0);
        }
        station now = new station();
        Queue<String> queue = new LinkedList<>();//ʹ�ö��У�ʵ��bfs�㷨

        now = map.get(start);
        queue.add(start);
        while(!queue.isEmpty()){
            String nowName = queue.remove();
            map.get(nowName).setVisited(true);
            if (now.getStationName().equals(end)){
                break;
            }
            for (station station1 :map.get(nowName).getLinkStations()){
                if(!map.get(station1.getStationName()).isVisited()){//δ���ʹ����ٽ�վ��
                    map.get(station1.getStationName()).setPreStation(nowName);//ΪpreStation��ֵ
                    map.get(station1.getStationName()).setDistance(map.get(nowName).getDistance()+1);//�ٽ�վ�ľ���Ϊ��վ�����1
                    queue.offer(station1.getStationName());
                }
            }
        }
    }

    public static void PrintPath(String start,String end){
        List<String> path = new ArrayList<>();
        Stack<String> printline = new Stack<>();
        int num = 1;//�ڼ�վ
        int cnt = 0;//���˴���
        String str = end;
        while(!str.equals(start)){
            path.add(str); 
            printline.push(str);
            str = map.get(str).getPreStation();
        }
        path.add(str);//��start����path
        printline.push(str);
        for (int i=1;i<path.size()-1;i++){
            if (map.get(path.get(i)).getLine().size() == 1){
                continue;
            }
            String temp1 = null;
            String temp2 = null;
            //ÿ��ս�ж�һ���Ƿ��л���վ
            for (String str1 : map.get(path.get(i)).getLine()){//��վ��ǰһվ�Ĺ�ͬӵ�е���·����temp1��
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
            for (String str1 : map.get(path.get(i)).getLine()){//��һվ�뱾վ�Ĺ�ͬӵ�е���·����temp2��
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
            if (!temp1.equals(temp2))//��temp1��temp2����·��ͬ��վΪת��վ
                map.get(path.get(i)).setchange(true);                                                                                                                                
        }//�ж�path�еĻ���վ
        System.out.println("��"+path.size()+"վ");
        while(!printline.empty()){
            String printStation = printline.pop();
            if(num == 1){
                for (String strnow : map.get(printStation).getLine()){
                    for (String nextStation : map.get(path.get(path.size()-num-1)).getLine()){
                        if (strnow.equals(nextStation)) {
                            System.out.println("��ǰ��Ϊ��"+strnow);
                            
                        }
                    }
                }
            }
            if (map.get(printStation).ischange()){
                String nowline ="";
                for (String strnow : map.get(printStation).getLine()){
                    //path.get(path.size()-numStation)Ϊ����վ��һվ ��վ���е��߾��ǻ�����
                    for (String nextStation : map.get(path.get(path.size()-num-1)).getLine()){
                        if (strnow.equals(nextStation))
                            nowline = nowline + strnow;
                    }
                }
                cnt++;
                System.out.println("");
                System.out.println("ת��Ϊ" + nowline);
            }
            System.out.print(printStation + " ");
            num++;
        }
    }

    public static void main(String[] ards){

        read("D:\\homework\\�������\\������·��Ϣ.txt"); // ���ö�ȡ�ļ�����
        Scanner scanner = new Scanner(System.in);
		System.out.print("1:���Ҹ���·վ��   2������ĳվ��ĳվ����̾���  ������������:");
		int chance = scanner.nextInt();
        switch(chance) {
		case 1:{
			System.out.print("�������ѯ����·:");
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
			System.out.print("���������վ��");
            String start = scanner.next();
            System.out.print("�������յ�վ��");
            String end = scanner.next();
            
            if (!map.containsKey(start)){//�ж����վ�Ƿ����
            	System.out.println("���վ������");
            	break;
            }
            if (!map.containsKey(end)){//�ж��յ�վ�Ƿ����
            	System.out.println("�յ�վ������");
            	break;
            }
            if (start.equals(end)){//�ж����վ���յ�վ�Ƿ���ͬ
                System.out.print("���վ���յ�վ��ͬ ��վΪ" + end + "����Ҫ��������");
                break;
            }
            bfs(start,end);
            PrintPath(start,end);
            break;
		}
		default:{
			System.out.println("������ѡ��");
			break;
		}
	}
        return;
    }
}