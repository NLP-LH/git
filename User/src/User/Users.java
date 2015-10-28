package User;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import Util.Cn2Spell;
import Util.InputAndOutput;
import Util.StringCompare;

public class Users {
	public static HashMap<String,User> UserMap;// 存入ID，USER对
	
	Users(){
		if(UserMap==null){//判断内存中是否有值，没有就从文件中进行读取，没有文件就新建一个
			File file =new File("UserMap");
			if(file.exists()){		
				UserMap=InputAndOutput.Input();
			}else{
				HashMap<String,User> tempUserMap=new HashMap<String,User>();	
				tempUserMap.put("testUser", new User("testUser",null,null,null,null,null));
				InputAndOutput.Output(tempUserMap);
				UserMap=tempUserMap;
			}				
		}		
	}
	///////////////////////////////////////////////////////////////////////////////////////
	//基本方法
	public User getUser(String IM_UID){//根据用户ID取用户
		//System.out.println(UserMap);
		if(UserMap.containsKey(IM_UID)){		
			return UserMap.get(IM_UID);
		}else{	
			return null;
		}
	}
	
	public boolean deleteUser(String IM_UID){//删除一个用户
		if(UserMap.containsKey(IM_UID)){	
			UserMap.remove(IM_UID);
			//InputAndOutput.Output(UserMap);//保存到文件中
			return true;
		}else{		
			return false;
		}
	}
	public boolean Create(String IM_UID,String nickname,String homeAddress,String companyAddress,HashMap<String,String> userInfor,HashMap<String,String> friendRel){//添加一个用户
		if(UserMap.containsKey(IM_UID)){	
			//System.out.println("用户已经存在");
			return false;
		}		
		User newUser=new User(IM_UID,nickname,homeAddress,companyAddress,userInfor,friendRel);
		UserMap.put(IM_UID, newUser);
		//InputAndOutput.Output(UserMap);//保存到文件中
		return true;
	}
	
	public boolean Create(User user){//添加一个用户
		if(UserMap.containsKey(user.getIM_UID())){	
			//System.out.println("用户已经存在");
			return false;
		}		
		UserMap.put(user.getIM_UID(), user);
		//InputAndOutput.Output(UserMap);//保存到文件中
		return true;
	}
///////////////////////////////////////////////////////////////////////////////////////
	//外部可调用方法
	public String[] Query(String IM_UID, String type, String text) {//祥哥总查询方法
		String[] res = null;
		if (type.equals("chat")) {
			res = IDQuery(IM_UID, text);
		} else if(type.equals("map")) {
			res = AddressQuery(IM_UID, text);
		} else {
			return null;
		}
		return res;
	}
	
	public boolean revise(String IM_UID,String type,String text){//镇权修改属性方法，需要改成WEB接口
		User newUser = getUser(IM_UID);
		if(newUser==null){	
			System.out.println(UserMap);
			return false;
		}
		//System.out.println("");
		if(type.equals("Com")){
			newUser.setCompanyAddress(text);
		}else if(type.equals("Home")){
			newUser.setHomeAddress(text);
		}else if(type.equals("nickname")){
			newUser.setNickname(text);
		}else{
			return false;
		}
		UserMap.put(IM_UID, newUser);
		//InputAndOutput.Output(Users.UserMap);
		return true;	
	}

	public boolean signUp(String IM_UID,String nickname,String homeAddress,String companyAddress){//老张注册的方法，需要改成WEB接口
		if(IM_UID==null||IM_UID.equals("")||nickname==null||nickname.equals("")){
			return false;
		}
		User newUser = getUser(IM_UID);
		if(newUser!=null){		
			return false;
		}
		newUser=new User(IM_UID,nickname,homeAddress,companyAddress,null,null);
		UserMap.put(IM_UID, newUser);
		return true;
	}
	
	public boolean addFriend(String IM_UID,String friendID,String alias){//添加好友
		User User = getUser(IM_UID);
		User friendUser = getUser(friendID);
		if(User==null||friendUser==null){		
			return false;
		}
		String nickname=friendUser.getNickname();
		if(alias==null||alias.equals("")){
			alias=nickname;
		}
		HashMap<String,String> friendRel=User.getFriendRel();
		if(friendRel==null){
			friendRel=new HashMap<String,String>();//好友关系，KEY是用户的ID，VALUE是用户的昵称	
			friendRel.put(friendID, alias);
			User.setFriendRel(friendRel);
			UserMap.put(IM_UID, User);	
			//InputAndOutput.Output(UserMap);
		}else{
			friendRel.put(friendID, alias);
			User.setFriendRel(friendRel);
			UserMap.put(IM_UID, User);	
			//InputAndOutput.Output(UserMap);
		}	
		return true;
	}
	
	public boolean deleteFriend(String IM_UID,String friendID){//删除好友
		User User = getUser(IM_UID);
		User friendUser = getUser(friendID);
		if(User==null||friendUser==null){		
			return false;
		}
		HashMap<String,String> friendRel=User.getFriendRel();
		if(friendRel==null){
			return false;
		}else{
			if(friendRel.containsKey(friendID)){
				friendRel.remove(friendID);
				User.setFriendRel(friendRel);
				UserMap.put(IM_UID, User);	
				//InputAndOutput.Output(UserMap);
				return true;
			}else{			
				return false;
			}
		}
	}
	
	public boolean modifyFriendalias(String IM_UID,String friendID,String alias){ //no True returned
		User User = getUser(IM_UID);
		if(User==null){		
			return false;
		}
		if(alias==null||alias.equals("")){
			User friendUser = getUser(friendID);
			if(friendUser==null){		
				return false;
			}
			alias=friendUser.getNickname();
		}
		HashMap<String,String> friendRel=User.getFriendRel();
		if(friendRel.containsKey(friendID)){
			if(alias==null||alias.equals("")){	
				return false;
			}else{			
				friendRel.put(friendID, alias);
				User.setFriendRel(friendRel);
				UserMap.put(IM_UID, User);	
				//InputAndOutput.Output(UserMap);
				return true;
			}	
		}
		return false;
	}
	
	//查询用户 
	//用户注册，老张 注册用户 ID 昵称 ， 修改 昵称
	//修改用户属性 镇权  传入 ID，TYPE ,TEXT  返回BOOLEAN	
	
///////////////////////////////////////////////////////////////////////////////////////
	public String[] AddressQuery(String IM_UID, String text) {//根据用户ID查询用户家的住址或者公司住址
		String[] res=new String[1];
		if(text==null||IM_UID==null){
			return null;
		}
		User newUser = getUser(IM_UID);
		if (newUser == null) {
			return null;
		}
		if (text.equals("home")) {
			res[0]=newUser.getHomeAddress();
			return res;
		} else if (text.equals("company")) {
			res[0]=newUser.getCompanyAddress();
			return res;
		}
		return null;
	}

	public String[] IDQuery(String IM_UID, String text) {//根据用户ID和文本查询好友的ID
		if(text==null||IM_UID==null){
			return null;
		}
		StringCompare lt = new StringCompare();
		Cn2Spell CS=new Cn2Spell();
		User newUser = getUser(IM_UID);
		if (newUser == null) {
			return null;
		}
		String[] res=null;
		HashMap<String,String> friendRel=newUser.getFriendRel();
		LinkedList<Map.Entry<String,Float>> simList=new LinkedList<Map.Entry<String,Float>>();
		//HashMap<String,Float> sim=new HashMap<String,Float>();
		for(Map.Entry<String, String> temp:friendRel.entrySet()){
			if(temp.getValue().equals(text)){
				res=new String[1];
				res[0]=temp.getKey();
				return res;
			}else{
				simList.add(new AbstractMap.SimpleEntry<String, Float>(temp.getKey(), lt.getSimilarityRatio(CS.converterToSpell(temp.getValue()), CS.converterToSpell(text))) );
			}		
		}
		//Entry<String, Float> res=new Entry<String,Float>[5]();
		res= sort(simList);
		return res;
	}
	
	private String[] sort(LinkedList<Map.Entry<String,Float>> simList){
		LinkedList<Map.Entry<String,Float>> res=new LinkedList<Map.Entry<String,Float>>();
		if(simList==null){		
			return null;		
		}else{
			res.add(simList.get(0));
			for(int i=1;i<simList.size();i++){
				Map.Entry<String,Float> s=simList.get(i);
				for(int j=0;j<res.size();j++){
					if(s.getValue()>res.get(j).getValue()){
						res.add(j, s);
						break;
					}	
				}				
			}
		}	
//		if(res==null){
//			return null;
//		}
		int length=3; //返回ID的数量
		if (res.size()<length){
			String[] resString =new String[res.size()];			
			for(int i=0;i<res.size();i++){			
				resString[i]=res.get(i).getKey();
			}
			return resString;
		}else{
			String[] resString =new String[length];			
			for(int i=0;i<length;i++){	
				resString[i]=res.get(i).getKey();
			}
			return resString;
		}		
	}
///////////////////////////////////////////////////////////////////////////////////////
	

}
