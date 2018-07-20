package cn.diffpi.resource.platform.user.model;


import java.util.List;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.resource.platform.role.model.PtRole;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;
import cn.dreampie.security.DefaultPasswordService;

/***
 * Created by one__l on 2016年8月18日
 */
@Table(name = "pt_user")
public class PtUser extends Model<PtUser> {
	public final static PtUser dao = new PtUser();

	/***
	 * 新增用户
	 * @param user
	 * @return
	 */
	public boolean save(PtUser user) {

		String password = user.get("password");
		user.set("created_at", DateUtil.getCurrentDate());
		user.set("password", DefaultPasswordService.instance().crypto(password));
		
		if(!valiUsername(user.<String>get("username"))) throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "用户名已存在");

		return user.save();
	}
	
	/**
	 * 得到用户角色
	 * @return
	 */
	public List<PtRole> getRoles(){
		List<PtRole> roles;
	    if (this.get("roles") == null && this.get("id") != null) {
	      String sql = "SELECT pr.* FROM pt_role pr RIGHT JOIN pt_user_role pur ON pr.id = pur.role_id where pur.user_id = ?";
	      roles = PtRole.dao.find(sql, this.get("id"));
	      this.put("roles", roles);
	    } else {
	      roles = this.get("roles");
	    }
	    return roles;
	}
	
	/***
	 * 登录
	 * @param username
	 * @param pwd
	 * @return
	 */
	public PtUser login(String username , String pwd){
		if (username == null || pwd == null ) {
			throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "用户名跟密码不能为空");
		}
		String sql = "select * from pt_user t where t.username = ?";

		PtUser user = dao.unCache().findFirst(sql, username.toLowerCase());
		if (user != null) {
			if (user.get("password") != null && DefaultPasswordService.instance().match(pwd, user.<String>get("password")) ) {
				return user.remove("password");
			} else{
				throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "用户名或密码错误");
			}
		} else {
			throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "用户名或密码错误");
		}
		
	}
	
	/***
	 * 修改用户密码
	 * @param username
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public PtUser upPwd(String username , String oldpwd , String newpwd){
		if(username == null || oldpwd == null || newpwd == null){
			throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "请检查参数");
		} 
		
		PtUser user = dao.findFirstBy(" username = ? ", username);
		if(user != null){
			if (user.get("password") != null && DefaultPasswordService.instance().match(oldpwd, user.<String>get("password")) ) {
				user.set("password",DefaultPasswordService.instance().crypto(newpwd)).update();
				return user;
			} else{
				throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "用户名或密码错误");
			}
		} else {
			throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "用户名或密码错误");
		}
		
	}
	
	private boolean valiUsername(String name){
		
		List<PtUser> users = dao.findBy(" username=?", name);
		
		return users.size() < 1;
		
	}
}