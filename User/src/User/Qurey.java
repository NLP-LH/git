package User;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Qurey {
	public String[] AddressQuery(String IM_UID, String text) {//根据用户ID查询用户家的住址或者公司住址
		String[] res=new String[1];
		if(text==null||IM_UID==null){
			return null;
		}
		Operate Op = new Operate();
		User newUser = Op.getUser(IM_UID);
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
		Operate Op = new Operate();
		User newUser = Op.getUser(IM_UID);
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
}
