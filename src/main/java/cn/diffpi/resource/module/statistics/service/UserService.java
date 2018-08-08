package cn.diffpi.resource.module.statistics.service;

import cn.dreampie.orm.Record;
import cn.dreampie.orm.TableSetting;

import java.util.List;

public class UserService {
    /**
     * 创建record的执行器  针对client_user表 并不开启缓存
     */
    Record dao = new Record(new TableSetting("client_user", false));

    /**
     * //     * 得到用户数量
     * //     * @return
     * //
     */
    public Long getCount() {
        return dao.countBy("isvali = ? and del = 0", "0");
    }

    /**
     * 分别罗列今年每月的用户注册量
     *
     * @return
     */
    public List<Record> getYearByMonth() {
        String sql = "SELECT  DATE_FORMAT(create_time,'%Y-%m') x,COUNT(id) y from client_user GROUP BY x";
        return dao.find(sql);
    }

    /**
     * 得到在线用户
     *
     * @return
     */
    public Long getOnline() {
        return dao.countBy("online = ?", "1");
    }


}
