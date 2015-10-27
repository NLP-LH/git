package Util;

/**
 * Created by liuhui on 15/10/21.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import User.User;

public class InputAndOutput {
	public static void Output(HashMap<String,User> UserMap) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UserMap"));// ；ObjectOutputStream能把Object输出成Byte流
			oos.writeObject(UserMap);
			oos.flush(); // 缓冲流
			oos.close(); // 关闭流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static HashMap<String,User> Input() {
//		File file =new File("UserMap");//先检查是否有文件，没有就返回NULL
//		if(file.exists()){	
//		}else{
//			return null;
//		}
		ObjectInputStream oin = null;// 局部变量必须要初始化
		try {
			oin = new ObjectInputStream(new FileInputStream("UserMap"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HashMap<String,User> UserMap = null;
		try {
			UserMap = (HashMap<String,User>) oin.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return UserMap;
	}

}
