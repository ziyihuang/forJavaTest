package learn;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * @author huangziyi@circle
 * @date 2017年3月15日 time 上午11:06:42
 * 
 */
public class learnMap {
	public static void main(String[] args) 
	{
	

	    ArrayList<Map> lslist = new ArrayList<Map>(); 
//	    HashMap<String, String> map=new HashMap<String, String>();
	    HashMap<Integer, String> map=new HashMap<Integer, String>();
	    map.put(1, "love you");
	    map.put(2, "love you");
		map.put(3,"2|2|2");
		map.put(4,"2|2222");
	    lslist.add(map);
		
		
//		Map<Integer, room2> map1 = new HashMap<Integer, room2>();
//		int cnt=1;
//		for(int i=1;i<100;i++)
//			for(int j=0;j<6;j++)
//			{
////				room2 room=new room2((long)(100*i+j), (10000L+i));
//				map1.put(cnt++, new room2((long)(100*i+j), (10000L+i)));
//				
//			}
		
//		for (Integer key : map1.keySet()) {  
//		       room2 room = map1.get(key);
//			System.out.println("the key is"+key+"values: kugouid:"+room.getKugouid()+"roomid:"+room.getRoomid());
////		    System.out.println("Key = " + key);  
//		  
//		}  
		
		//遍历map中的键  
		  
//		for (Integer key : map1.keySet()) {  
//		       room2 room = map1.get(key);
//			System.out.println("the key is"+key+"values: kugouid:"+room.getKugouid()+"roomid:"+room.getRoomid());
////		    System.out.println("Key = " + key);  
//		  
//		}  
		  
		//遍历map中的值  
		  
		for (String value : map.values()) {  
		  
		    System.out.println("Value : " + value);  
		    System.out.println(lslist);
		  
		}  
		
		for (Integer key :  map.keySet()) {  
			  
		
			String  value = map.get(key);  
		  
		    System.out.println("Key = " + key + ", Value = " + value);  
		  
		}  
	}
	

}


class room2{
	private long kugouid;
	private long roomid;
	
	/**
	 * @param l
	 * @param m
	 */
	public room2(long kugouId, long roomId) {
		
		this.kugouid = kugouId;
		this.roomid = roomId;
		// TODO Auto-generated constructor stub
	}
	public long getKugouid() {
		return kugouid;
	}
	public void setKugouid(long kugouid) {
		this.kugouid = kugouid;
	}
	public long getRoomid() {
		return roomid;
	}
	public void setRoomid(long roomid) {
		this.roomid = roomid;
	}
	
	
}

