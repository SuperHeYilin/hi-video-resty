package cn.diffpi.resource.module.order.model;

import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.BaseModel;
import cn.diffpi.resource.platform.user.model.PtUser;
import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.client.ClientResult;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.common.util.json.Jsoner;
import cn.dreampie.common.util.properties.Proper;
import cn.dreampie.orm.annotation.Table;
import cn.dreampie.route.core.Params;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Table(name = "module_order")
public class Order extends BaseModel<Order> {
    public static final Order dao = new Order();

    /**
     * 订单条件查询拼装sql
     *
     * @param userId
     * @param arrayList
     * @param p
     * @return
     */
    public String orderSql(int userId, ArrayList<Object> arrayList, Params p) {
        StringBuilder sql = new StringBuilder(" mo.*,ms.name,cu.phonenum from module_order mo left join client_user cu on mo.user = cu.id ,module_store ms where mo.store = ms.id and mo.del = 0 ");

        PtUser ptUser = PtUser.dao.findById(userId);

        // 判断是否为非受限用户  如果为普通用户 获取门店
        if (!"1".equals(ptUser.get("user_type"))) {
            Integer userStore = ptUser.get("store", Integer.class);
            if (userStore == null) {
                throw new HttpException(HttpStatus.FORBIDDEN, "not_order_auth", "未取得访问权限");
            }
            sql.append(" and mo.store=" + userStore);
        }

        // 订单状态
        String state = p.get("state");
        if (StrKit.notBlank(state) && !"0,1,2,3,4,5,6".equals(state)) {
            sql.append(" and mo.state = ? ");
            arrayList.add(state);
        }
        // erp推送状态
        String erp = p.get("erp");
        if (StrKit.notBlank(erp) && !"all".equals(erp)) {
            sql.append(" and mo.is_send = ? ");
            arrayList.add(erp);
        }
        // 支付类型
        String payType = p.get("pay_type");
        if (StrKit.notBlank(payType) && !"all".equals(payType)) {
            sql.append("and mo.pay_type = ? ");
            arrayList.add(payType);
        }
        // 起始时间
        String startDate = p.get("create_time");
        if (StrKit.notBlank(startDate)) {
            sql.append(" and mo.create_time >= ? ");
            arrayList.add(startDate);
        }
        // 截止时间
        String endDate = p.get("create_time1");
        if (StrKit.notBlank(endDate)) {
            sql.append(" and mo.create_time <= ? ");
            arrayList.add(endDate);
        }
        // 订单手机号 订单号
        String number = p.get("mobile");
        if (StringKit.isNotBlank(number)) {
            sql.append(" and (mo.no = ? or cu.phonenum = ?)");
            arrayList.add(number.trim());
            arrayList.add(number.trim());
        }

        return sql.toString();
    }

    /****
     * 订单整单退款
     * @param orderId
     * @return
     */
    public boolean orderRefund(Integer orderId, Double money, String desc) {
        Order order = dao.findById(orderId);
        Double payAmount = order.<Double>get("pay_amount");
        Double refundMoney = order.<Double>get("refund_money") == null ? 0 : order.<Double>get("refund_money");
        System.out.print(sub(payAmount, refundMoney) + ":" + (payAmount - refundMoney < money));
        if (sub(payAmount, refundMoney) < money || money == 0) {
            throw new HttpException(HttpStatus.NOT_FOUND, "paramErr", "退款金额受限");
        }

        Client c = new Client(Proper.use("application.properties").get("order.refund.url"));
        ClientRequest cr = new ClientRequest();
        cr.addParam("orderId", orderId + "");
        cr.addParam("money", money + "");
        cr.addParam("memo", desc);

        if (refundMoney == 0) {
            cr.addParam("refundAll", "0");
        } else {
            cr.addParam("refundAll", "1");
        }

        c.build(cr);
        ClientResult clientResult = c.post();
        if (clientResult.getStatus() == HttpStatus.OK) {
            order
                    .set("state", "5")
                    .set("refund_desc", desc)
                    .set("refund_money", money + refundMoney)
                    .update();

            List<OrderGoods> goodsList = OrderGoods.dao.getOrderGoodsById(orderId);
            for (OrderGoods orderGoods : goodsList) {
                orderGoods
                        .set("refund_num", orderGoods.get("num"))
                        .set("num", 0)
                        .update();
            }

            return true;
        } else {
            JSONArray jsonArray = Jsoner.toObject(clientResult.getResult());
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, jsonObject.getString("key"), jsonObject.getString("message"));
        }
    }

    /****
     * 订单单个商品退款
     * @param orderId
     * @return
     */
    public boolean orderGoodsRefund(Integer goodsId, Integer orderId, Integer refundNum, String desc) {
        Order order = dao.findById(orderId);
        OrderGoods orderGoods = OrderGoods.dao.findById(goodsId);
        Double money = refundNum * orderGoods.get("price", Double.class);
        Double payAmount = order.get("pay_amount", Double.class);
        Double orderAmount = order.get("order_amount", Double.class);
        //已计算的退款金额
        Double oldRefundMoney = order.get("refund_money", Double.class);
        oldRefundMoney = oldRefundMoney == null ? 0 : oldRefundMoney;
        //新的预计退款金额
        //Double newRefundMoney = money;

        //计算实际退款金额
        money = money * (payAmount / (orderAmount));

        if (payAmount - oldRefundMoney < money || money == 0) {
            throw new HttpException(HttpStatus.NOT_FOUND, "paramErr", "退款金额受限");
        }

        Client c = new Client(Proper.use("application.properties").get("order.refund.url"));
        ClientRequest cr = new ClientRequest();
        cr.addParam("orderId", orderId + "");
        cr.addParam("money", money + "");
        cr.addParam("orderGoods", goodsId + "");
        cr.addParam("refundNum", refundNum + "");
        cr.addParam("memo", desc);
        c.build(cr);
        if (c.post().getStatus() == HttpStatus.OK) {
            Integer totalNum = order.get("goods_num", Integer.class) - refundNum;
            if (totalNum == 0) {
                order.set("state", "5");
            }
            order
                    .set("refund_desc", desc)
                    .set("refund_money", money + oldRefundMoney)
                    .update();

            orderGoods
                    .set("num", orderGoods.get("num", Integer.class) - refundNum)
                    .set("refund_num", refundNum)
                    .update();

            return true;
        }

        throw new HttpException(HttpStatus.NOT_FOUND, "paramErr", "退款失败");
    }

    /**
     * 提供精确的减法运算。
     */
    private double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /***
     * 根据订单编号查询待检验的订单
     * @param no
     * @return
     */
    public Order getCheckNo(String no) {
        if (no == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "paramErr", "订单号无效");
        }

        Order order = Order.dao.findFirst("select mo.*,ms.`name` as store_name,ms.shop_id from module_order mo LEFT JOIN module_store ms on mo.store = ms.id where mo.no = ? and mo.del='0'", no);
        if (order == null) {
            throw new HttpException(HttpStatus.NOT_FOUND, "order_not_fund", "未找到此订单");
        }
        List<OrderGoods> orderGoodsList = new ArrayList<>();
        if (order.get("id") != null) {
            orderGoodsList = OrderGoods.dao.getOrderGoods(order.<Integer>get("id"));
        }
        order.put("goods", orderGoodsList);
        /*if(!order.<String>get("state").equals("1")){
            throw new HttpException(HttpStatus.NOT_FOUND,"order_not_fund","此订单不是待核验状态");
        }*/

        return order;
    }

    /***
     * 根据订单编号验证订单
     * @param no
     * @return
     */
    public boolean validateOrder(String no, String type) {
        if (no == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "paramErr", "订单号无效");
        }

        Order order = Order.dao.findFirst("select mo.*,ms.`name` as store_name,ms.shop_id from module_order mo LEFT JOIN module_store ms on mo.store = ms.id where mo.no = ? and mo.del='0'", no);
        if (order == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "order_not_fund", "未找到此订单");
        }
        if (!order.<String>get("state").equals("1")) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "order_not_fund", "此订单不是待取货状态");
        }

        order
                .set("state", "2")
                .update();

        sendOrder(order.get("id", Integer.class));

        return true;
    }

    /***
     * 关闭支付已超时的订单
     */
    public void closeOrder() {
        Order.dao.update("UPDATE module_order mo SET state = 3 WHERE state = 0 and (unix_timestamp(now())-unix_timestamp(mo.create_time)) > 1800");
    }

    /***
     * 发送订到到ERP
     * @param id
     * @return
     */
    public boolean sendOrder(final Integer id) {
        Client client = new Client(Proper.use("application.properties").get("order.send.url"));
        ClientRequest cr = new ClientRequest();
        cr.setParams(new HashMap<String, String>() {{
            put("id", id + "");
        }});

        ClientResult clientResult = client.build(cr).post();
        if (clientResult.getStatus() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
    }
}
