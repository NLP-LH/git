package User;
/**
 * Created by liuhui on 15/10/21.
 */
import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
	/**
	 * 用户类，包括若干结构化字段，和若干非结构字段
	 */
	private static final long serialVersionUID = 1L;

	private String IM_UID;//用户ID
	
	private String homeAddress;//家庭住址
	
	private String companyAddress;//公司住址
	
	HashMap<String,String> userInfor= new HashMap<String,String>();//用户的非结构化信息，如<外婆家，侨福芳草地>
	
	HashMap<String,String> friendRel=new HashMap<String,String>();//好友关系，KEY是用户的ID，VALUE是用户的昵称
	
	public User(String IM_UID,String homeAddress,String companyAddress,HashMap<String,String> userInfor,HashMap<String,String> friendRel){
		this.IM_UID=IM_UID;
		this.homeAddress=homeAddress;
		this.companyAddress=companyAddress;
		this.userInfor=userInfor;
		this.friendRel=friendRel;
	}

	public String getIM_UID() {
		return IM_UID;
	}

	public void setIM_UID(String iM_UID) {
		IM_UID = iM_UID;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public HashMap<String, String> getUserInfor() {
		return userInfor;
	}

	public void setUserInfor(HashMap<String, String> userInfor) {
		this.userInfor = userInfor;
	}

	public HashMap<String, String> getFriendRel() {
		return friendRel;
	}

	public void setFriendRel(HashMap<String, String> friendRel) {
		this.friendRel = friendRel;
	}

	@Override
	public String toString() {
		return "User [IM_UID=" + IM_UID + ", homeAddress=" + homeAddress + ", companyAddress=" + companyAddress
				+ ", userInfor=" + userInfor + ", friendRel=" + friendRel + "]";
	}
	
}
