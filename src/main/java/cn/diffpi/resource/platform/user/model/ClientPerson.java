package cn.diffpi.resource.platform.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/***
 * 用户个人信息类
 */
@Table(name = "client_person",cached=true)	
public class ClientPerson extends Model<ClientPerson>{
	public final static ClientPerson dao = new ClientPerson();
	
	/***
	 * 得到用户
	 * @param userId
	 * @return
	 */
	public ClientPerson person(Integer userId){
		
		ClientPerson clientPerson = ClientPerson.dao.findFirstBy(" user = ?", userId);
		
		return clientPerson;
	}
	
	public void filter(){
		this.remove("id","user","state");
	}
}
