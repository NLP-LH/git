package User;
import java.io.File;
/**
 * Created by liuhui on 15/10/21.
 */
import java.util.HashMap;

public class NLPQuery {
	
	public static HashMap<String,User> UserMap= new HashMap<String,User>();
	
	NLPQuery(){
		File file =new File("UserMap");
		if(file.exists()){
			InputAndOutput IO=new InputAndOutput();
			UserMap=IO.Input();
		}else{
			InputAndOutput IO=new InputAndOutput();
			HashMap<String,User> UserMap=new HashMap<String,User>();	
			UserMap.put("testUser", new User("testUser",null,null,null,null));
			IO.Output(UserMap);
			UserMap=IO.Input();
		
		}		
	}
	
	public String[] Query(String IM_UID, String type, String text) {//总查询方法
		String[] res = null;
		Qurey Qu=new Qurey();
		if (type.equals("chat")) {
			res = 	Qu.IDQuery(IM_UID, text);
		} else if(type.equals("map")) {
			res = Qu.AddressQuery(IM_UID, text);
		} else {
			return null;
		}
		return res;
	}
	
	


	//查询用户 BOSS
	//用户注册，老张 注册用户 ID 昵称 ， 修改 昵称
	//修改用户属性 镇权  传入 ID，TYPE ,TEXT  返回BOOLEAN
}
