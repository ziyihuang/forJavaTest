package pangxiong;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
//@Component
public class RechargeSuccessCommonServer{
	
//	Logger logger = LoggerFactory.getLogger(RechargeSuccessCommonServer.class);
//	
//	@Autowired
//	private UserRedEnvelopeDao useRedEnvelopeDao;
//	@Autowired
//	private RechargeSuccessDao rechargeSuccessDao;
//	@Autowired
//	private BaseRedisService baseRedisService;
//	@Autowired
//	private OddsConfigDao oddsConfigDao;
//	@Autowired
//	private RedisUserService redisUserService;
//	@Autowired
//	private UserRedEnvelopeService userRedEnvelopeService;
	
//	@Transactional(rollbackFor = { Exception.class })
	
//	public static void main(String[] args) throws Exception {
	

		
		
		
//    	Integer item = chanceSelect(list).intValueExact();
////		BigDecimal winningMoney =chanceSelect(list);
//    	System.out.println(item);
//		
//	}
	
	

	
//	private static List<OddsConfig> getOddsConfig(int gradeType){
//		String key="ODDS_CONFIG"+gradeType;
//			List<OddsConfig> list=oddsConfigDao.getAllConfig(gradeType);
//			return list;
//	}
	
	private static BigDecimal chanceSelect(List<OddsConfig> list) {  
//         if(list == null || list.size() == 0){
//        	 return null;  
//         }
         Integer sum =0;  
         for (OddsConfig odds : list) {  
        	 
              sum += odds.getOdds();  
         }  
         // 从1开始  
         Integer rand = new Random().nextInt(sum) + 1;  
           
         for (OddsConfig odds : list) {  
              rand -=odds.getOdds();  
              // 选中  
              if(rand <= 0) {  
                   return odds.getMoney(); 
              }  
         }  
         return null;  
    } 
	
	
    public static void main(String[] args) {  
    	List<OddsConfig> list=new ArrayList<OddsConfig>();
    	
    	OddsConfig oc1=new OddsConfig();
    	oc1.setMoney(new BigDecimal(100));
    	oc1.setOdds(75);
    	
    	OddsConfig oc2=new OddsConfig();
    	oc2.setMoney(new BigDecimal(5));
    	oc2.setOdds(925);
    	
//    	OddsConfig oc3=new OddsConfig();
//    	oc3.setMoney(new BigDecimal(30));
//    	oc3.setOdds(130000);
//    	
//    	OddsConfig oc4=new OddsConfig();
//    	oc4.setMoney(new BigDecimal(35));
//    	oc4.setOdds(280000);
//    	
//    	OddsConfig oc5=new OddsConfig();
//    	oc5.setMoney(new BigDecimal(40));
//    	oc5.setOdds(100000);
//    	
//    	OddsConfig oc6=new OddsConfig();
//    	oc6.setMoney(new BigDecimal(45));
//    	oc6.setOdds(46989);
//    	
//    	OddsConfig oc7=new OddsConfig();
//    	oc7.setMoney(new BigDecimal(50));
//    	oc7.setOdds(23000);
//    	
//    	OddsConfig oc8=new OddsConfig();
//    	oc8.setMoney(new BigDecimal(60));
//    	oc8.setOdds(10);
//    	
//    	OddsConfig oc9=new OddsConfig();
//    	oc9.setMoney(new BigDecimal(100));
//    	oc9.setOdds(1);
    	
    	list.add(oc1);
    	list.add(oc2);
//    	list.add(oc3);
//    	list.add(oc4);
//    	list.add(oc5);
//    	list.add(oc6);
//    	list.add(oc7);
//    	list.add(oc8);
//    	list.add(oc9);
    	
        Map<Integer, Integer> count = new HashMap<Integer,Integer>();  
        for (int i = 0; i < 100000; i++) {  
              
        	Integer  item = chanceSelect(list).intValueExact();  
              
            if (count.containsKey(item)) {  
                count.put(item, count.get(item) + 1);  
            } else {  
                count.put(item, 1);  
            }  
        }  
          
        for(Integer id : count.keySet()){  
//        	BigDecimal  y = count.get(id)/1000000;
            System.out.println(id+"\t出现了 "+count.get(id)+" 次");      
        }  
        
        Long z=new Long(0);    
        for(Integer id : count.keySet()){  
        	z+=id*count.get(id);   
        }  
        System.out.println("红包总金额：" + z);   
    }  
}
