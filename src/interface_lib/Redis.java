package interface_lib;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/*
 * 清缓存的步骤
 * 1.连接服务器
 * 2.查询key的类型
 * 3.执行相应缓存的修改或者删除操作
 * hash查询，先设置key和field，再查field,查config表，初级场配置 
*/
public class Redis {	
    public static void main(String[] args) {
        new Redis().Operate(); 
    }        
    private Jedis jedis;
    private JedisPool jedisPool;    
    public Redis() {
        initialPool(); 
        jedis = jedisPool.getResource(); 
    }
    private void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxActive(300); 
        config.setMaxIdle(100); 
        config.setMaxWait(10000); 
        config.setTestOnBorrow(false); 
        //配置redis服务器IP、端口
       jedisPool = new JedisPool(config,"10.16.6.16",7002);//疯狂夹娃娃测试
       
//        jedisPool = new JedisPool(config,"10.16.6.69",9000);//酷狗看测试
//        jedisPool = new JedisPool(config,"10.16.6.70",9000);//繁星测试
    }    
    private void Operate() {         
//    	String key= "hidden_stars_list";//手机直播-隐身主播列表
//    	String key= "live_history_ranking:703695390";//手机直播-艺人7天榜单
//    	String key= "live_current_ranking:6641654951458806096264";//手机直播-艺人本场榜单
//    	String key= "room_star:1013084";//kugou_show.room_star、kugou_mfanxing.room_star表房间信息缓存
//    	String key= "switch_config";//kugou_mps.switch_config表缓存。hget查询
//    	String field= "max_viewenum_unaudit";//手机直播-审核未通过主播观众人数上限配置。key为switch_config
//    	String key= "mobile_roomId_generator";//手机直播-分配的最后一个房间号[非预发布]
//    	String key= "mobile_list_focus_add_star_max:664167103";
    	
//    	String key= "user_total_income_501726938";//疯狂夹娃娃，501726938[酷狗ID]的mi
//    	String key= "gift_list_1";//疯狂夹娃娃，礼物列表初级场
//    	String key= "config";//疯狂夹娃娃，config表
    	String key="user_today_signin_824290205";//签到的缓存
//    	String key="user_today_signin_802600647";
//    	String key="point_level_list";//积分等级缓存

    	
//    	String key="user_base_info_821424875";
//    	String key="user_winning_announcement";//夹娃娃中奖公告
//    	String key="user_winning_podium";//夹娃娃领奖台
//    	String field= "play_level_1";
//    	String key="user_all_recharge_824290205";//用户累计充值金额
//    	System.out.println(jedis.hget(key, field));
//    	System.out.println(jedis.set(key,"810"));
//    	System.out.println(jedis.hget(key, field));
//    	System.out.println(jedis.get(key));
//    	System.out.println(jedis.lrange(key, 0, -1));//list类型查询
//    	System.out.println(jedis.type(key));//查询key的类型
    	//查询有效期[不存在的返回-2，不过期的返回-1]
//    	System.out.println(jedis.ttl(key));
    	//删除
    	jedis.del(key);    		
    	System.out.println(jedis.get(key));
    	//清空数据
//    	System.out.println(jedis.flushDB());
    	
    	//get字节数据，再转自定义对象，再取值
//    	String key= "mobile_room_view:11013073";
//    	byte[] bytes = jedis.get(key.getBytes());
//    	Redis_MobileRoomView view = Redis_SerialUtil.parseValue(Redis_MobileRoomView.class, bytes);    	
//    	System.out.print(view.getRobotNum());//输出view中的RobotNum
    	
    	//查所有key
//    	System.out.println(jedis.keys("*"));
    	//模糊查找
//    	System.out.println(jedis.keys("*601462539*"));
    }    
}