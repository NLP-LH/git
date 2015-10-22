package User;
/**
 * Created by liuhui on 15/10/21.
 */
import java.util.HashMap;

public class Operate {
	
	public boolean Create(String IM_UID,String homeAddress,String companyAddress,HashMap<String,String> userInfor,HashMap<String,String> friendRel){
		InputAndOutput IO=new InputAndOutput();
		//HashMap<String,User> UserMap=IO.Input();
		if(NLPQuery.UserMap!=null){
			if(NLPQuery.UserMap.containsKey(IM_UID)){	
				System.out.println("用户已经存在");
				return false;
			}		
		}else{		
			NLPQuery.UserMap=new HashMap<String,User>();
		}
		User newUser=new User(IM_UID,homeAddress,companyAddress,userInfor,friendRel);
		NLPQuery.UserMap.put(IM_UID, newUser);
		IO.Output(NLPQuery.UserMap);
		return true;
	}
	
	public User getUser(String IM_UID){
		//InputAndOutput IO=new InputAndOutput();
		//HashMap<String,User> UserMap=IO.Input();
		if(NLPQuery.UserMap.containsKey(IM_UID)){		
			return NLPQuery.UserMap.get(IM_UID);
		}else{	
			return null;
		}
	}
	
	public boolean deleteUser(String IM_UID){
		InputAndOutput IO=new InputAndOutput();
		//HashMap<String,User> UserMap=IO.Input();
		if(NLPQuery.UserMap.containsKey(IM_UID)){	
			NLPQuery.UserMap.remove(IM_UID);
			IO.Output(NLPQuery.UserMap);
			return true;
		}else{		
			return false;
		}
		
		
	}
}
