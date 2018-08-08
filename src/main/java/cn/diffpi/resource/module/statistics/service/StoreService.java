package cn.diffpi.resource.module.statistics.service;

import cn.dreampie.orm.Record;

import java.util.List;

public class StoreService {
    public static final Record dao = new Record();

    /**
     * 获得门店总销售额排名
     *
     * @return
     */
    public List<Record> getRanking(int limit) {
        String sql = "SELECT a.name title,b.su total FROM module_store a,(SELECT store,SUM(pay_amount) AS su FROM module_order " +
                "where state = '2' GROUP BY store ORDER BY su DESC\n" +
                ") b WHERE a.id=b.store ORDER BY su DESC limit 0,?";
        return dao.find(sql, limit);
    }

    /**
     * 返回门店销售
     * @return
     */
//    public List<Record> getStoreRanking() {
//        String sql = "SELECT a.name,b.m from module_store a,(SELECT store,SUM(pay_amount) m from " +
//                "module_order GROUP BY store) b where a.id = b.store";
//        return dao.find(sql);
//    }

}
