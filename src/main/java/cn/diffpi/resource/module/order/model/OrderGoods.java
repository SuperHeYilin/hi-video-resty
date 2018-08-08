package cn.diffpi.resource.module.order.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

import java.util.List;

@Table(name = "module_order_goods")
public class OrderGoods extends BaseModel<OrderGoods> {
    public static final OrderGoods dao = new OrderGoods();

    public List<OrderGoods> getOrderGoodsById(Integer orderId) {
        return dao.findBy("orderid = ?", orderId);
    }

    /***
     * 根据订单id获取商品信息
     * @param orderId
     * @return
     */
    public List<OrderGoods> getOrderGoods(Integer orderId) {
        String sql = "SELECT\n" +
                "	mog.*,\n" +
                "	mg.GoodsID,\n" +
                "	gimg.src,\n" +
                "	gimg.goods_id AS ggs \n" +
                "FROM\n" +
                "	module_order_goods mog\n" +
                "	LEFT JOIN module_goods mg ON mog.goods_id = mg.id\n" +
                "	LEFT JOIN ( SELECT src, goods_id FROM module_goods_image mgi WHERE is_cover = 1 ) gimg ON mog.goods_id = gimg.goods_id \n" +
                "WHERE\n" +
                "	mog.orderid = ?\n" +
                "ORDER BY\n" +
                "	mog.type";

        return OrderGoods.dao.find(sql, orderId);
    }
}
