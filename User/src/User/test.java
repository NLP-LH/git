package User;

import java.util.HashMap;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,String> userInfor= new HashMap<String,String>();//用户的非机构化信息
		userInfor.put("家", "柳林馆");
		userInfor.put("阿姨家", "普惠南里");
		HashMap<String,String> friendRel=new HashMap<String,String>();//好友关系
		friendRel.put("1231", "罗小明");
		friendRel.put("1232", "王小丽");
		friendRel.put("1233", "张小丽");
		friendRel.put("1234", "张大明");
		friendRel.put("1235", "灯狗蛋");
		
		Users Us=new Users();
		boolean ss=Us.Create("1230","tracy" ,"侨福芳草地", "柳林馆", userInfor, friendRel);
		System.out.println(ss);
		boolean sss=Us.Create("1232","tom", "qiaofu", "hehe", null, null);
		System.out.println(sss);
		Users ssss=new Users();
		String[] s =ssss.Query("1230", "chat","张小明的爷爷是我");
		for(String gege:s){
			System.out.println(gege);		
		}
		
	}

}
