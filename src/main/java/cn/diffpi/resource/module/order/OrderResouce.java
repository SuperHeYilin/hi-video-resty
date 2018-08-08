package cn.diffpi.resource.module.order;

import cn.diffpi.core.kit.BaseSv;
import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.ExcelUtil;
import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.order.model.Order;
import cn.diffpi.resource.module.order.vo.OrderVo;
import cn.diffpi.resource.platform.user.model.PtUser;
import cn.diffpi.security.annotation.AuthSign;
import cn.diffpi.security.credential.CredentialType;
import cn.dreampie.common.http.HttpResponse;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.orm.transaction.Transaction;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


@API("/module/order")
@AuthSign
public class OrderResouce extends ApiResource {

    @GET
    public SplitPage listOrder() {
        SplitPage page = getModel(SplitPage.class, true);
        // 可变占位符参数对象集合
        ArrayList<Object> arrayList = new ArrayList<Object>();
        // 通过参数获取动态sql
        String sql = Order.dao.orderSql(getUserId(), arrayList, getParams());

        Order.dao.splitPageBaseSql(page, "select ", sql, arrayList.toArray());

        return page;
    }


    /**
     * @Author: Yi Huang
     * @Description:查询订单
     * @user:GS-bailay
     * @param: order_mobile 订单号或者手机号
     * @param: mobile 手机号
     * @param: state订单状态
     * @param: store门店id
     * @param: create_time 订单创建起始时间
     * @param: create_time1 订单创建截止时间
     * @param: pay_type 支付方式
     * @Date: 2017-12-04 11:05
     */
    @GET("/orders")
    public SplitPage orders(String mobile, String state, String create_time, String create_time1, String pay_type) {
        SplitPage page = getModel(SplitPage.class, true);
        PtUser ptUser = PtUser.dao.findById(getUserId());

        StringBuffer sb = new StringBuffer();
        sb.append("select mo.*,ms.name,cu.phonenum from module_order mo left join client_user cu on mo.user = cu.id ,module_store ms where mo.store = ms.id and mo.del = 0");
        if (!"1".equals(ptUser.get("user_type"))) {
            Integer userStore = ptUser.get("store", Integer.class);
            if (userStore == null) {
                throw new HttpException(HttpStatus.FORBIDDEN, "not_order_auth", "未取得访问权限");
            }
            sb.append(" and mo.store=" + userStore);
        }
        if (StringKit.isNotBlank(mobile)) {
            sb.append(" and (mo.no='" + mobile + "' or cu.phonenum='" + mobile + "')");
        }
        if (StringKit.isNotBlank(state)) {
            if (!state.equals("请选择")) {
                sb.append(" and mo.state in (" + state + ")");
            }
        }
        if (StringKit.isNotBlank(pay_type)) {
            if (!pay_type.equals("请选择")) {
                sb.append(" and mo.pay_type in (" + pay_type + ")");
            }
        }
        if (StringKit.isNotBlank(create_time) && StringKit.isNotBlank(create_time1)) {
            sb.append(" and mo.create_time between '" + create_time + "' and '" + create_time1 + "'");
        }
        BaseSv.me.splitPageBaseSql(page, "", sb.toString());
        return page;
    }

    /**
     * @Author: Yi Huang
     * @Description:查询已支付订单
     * @user:GS-bailay
     * @param: order_mobile 订单号或者手机号
     * @param: mobile 手机号
     * @param: store 门店id
     * @param: create_time创建起始日期
     * @param: create_time1创建截止日期
     * @param: pay_time 支付起始日期
     * @param: pay_time1 支付截止日期
     * @param: pay_type 支付方式
     * @Date: 2017-12-07 15:28
     */
    @GET("/refund")
    public SplitPage refundOrder(String state, String mobile, String create_time, String create_time1, String pay_time, String pay_time1, String pay_type) {
        SplitPage page = getModel(SplitPage.class, true);
        PtUser ptUser = PtUser.dao.findById(getUserId());

        StringBuffer sb = new StringBuffer();
        sb.append("select mo.*,ms.name,cu.phonenum from module_order mo left join client_user cu on mo.user = cu.id,module_store ms where mo.store = ms.id and mo.del = 0");
        if (!"1".equals(ptUser.get("user_type"))) {
            Integer userStore = ptUser.get("store", Integer.class);
            if (userStore == null) {
                throw new HttpException(HttpStatus.FORBIDDEN, "not_order_auth", "未取得访问权限");
            }
            sb.append(" and mo.store=" + userStore);
        }
        String dbState = "'1','2'";
        if (StringKit.isNotBlank(state)) {
            if (state.equals("1")) {
                dbState = "5";
            }
        }
        sb.append(" and mo.state in (" + dbState + ")");
        if (StringKit.isNotBlank(mobile)) {
            sb.append(" and (mo.no='" + mobile + "' or cu.phonenum='" + mobile + "')");
        }
        if (StringKit.isNotBlank(pay_type)) {
            if (!pay_type.equals("请选择")) {
                sb.append(" and mo.pay_type in (" + pay_type + ")");
            }
        }
        if (StringKit.isNotBlank(create_time) && StringKit.isNotBlank(create_time1)) {
            sb.append(" and mo.create_time between '" + create_time + "' and '" + create_time1 + "'");
        }
        if (StringKit.isNotBlank(pay_time) && StringKit.isNotBlank(pay_time1)) {
            sb.append(" and mo.pay_time between '" + pay_time + "' and '" + pay_time1 + "'");
        }
        BaseSv.me.splitPageBaseSql(page, "", sb.toString());
        return page;
    }

    /**
     * 订单整单退
     *
     * @Author: Yi Huang
     * @Description:订单退款
     * @user:GS-bailay
     * @param: id值{8}
     * @Date: 2017-12-05 10:39
     */
    @PUT("refund")
    @Transaction
    public boolean refundOrder(Integer id, Double money, String desc) {
        return Order.dao.orderRefund(id, money, desc);
    }

    /***
     * 商品退款
     * @param goodsId
     * @param orderId
     * @param refundNum
     * @return
     */
    @PUT("refund/goods")
    @Transaction
    public boolean refundOrderGoods(Integer goodsId, Integer orderId, Integer refundNum, String desc) {
        return Order.dao.orderGoodsRefund(goodsId, orderId, refundNum, desc);
    }

    /**
     * @Author: Yi Huang
     * @Description: 删除选中订单
     * @user:GS-bailay
     * @param: ids id
     * @Date: 2017-12-04 11:09
     */
    @PUT("/delete")
    public boolean deleteOrder(String ids) {
        return Order.dao.update("update module_order set del = '1' where id in (" + ids + ")");
    }

    /***
     * 检查订单信息
     * @param no
     * @return
     */
    @GET("/check/:no")
    @AuthSign(valiLevel = CredentialType.NO)
    public Order checkOrder(String no) {
        return Order.dao.getCheckNo(no);
    }

    /***
     * 校验订单
     * @param no
     * @return
     */
    @GET("/validate/:no")
    @AuthSign(valiLevel = CredentialType.NO)
    public boolean validateOrder(String no, String type) {
        return Order.dao.validateOrder(no, type);
    }

    /**
     * 导出Excel
     *
     * @return
     */
    @GET("/export")
    @AuthSign
    public boolean exportExcel() {

        // 可变占位符参数对象集合
        ArrayList<Object> arrayList = new ArrayList<Object>();
        // 通过参数获取动态sql
        String sql = Order.dao.orderSql(getUserId(), arrayList, getParams());

        List<Order> orderList = Order.dao.find("select " + sql, arrayList.toArray());

        List<OrderVo> newList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = new OrderVo();

            if (StrKit.notBlank(order.<String>get("name"))) {
                orderVo.setName(order.<String>get("name"));
            } else {
                orderVo.setName("");
            }

            if (StrKit.notBlank(order.<String>get("no"))) {
                orderVo.setNo(order.<String>get("no"));
            } else {
                orderVo.setNo("");
            }

            if (StrKit.notBlank(order.<String>get("phonenum"))) {
                orderVo.setPhonenum(order.<String>get("phonenum"));
            } else {
                orderVo.setPhonenum("");
            }

            if (order.get("goods_num", Integer.class) != null) {
                orderVo.setGoodsNum(order.get("goods_num", Integer.class));
            } else {
                orderVo.setGoodsNum(0);
            }

            if (order.get("order_amount", Double.class) != null) {
                orderVo.setOrderAmount(order.get("order_amount", Double.class));
            } else {
                orderVo.setOrderAmount(0);
            }

            if (order.get("pay_amount", Double.class) != null) {
                orderVo.setPayAmount(order.get("pay_amount", Double.class));
            } else {
                orderVo.setPayAmount(0);
            }

            if (order.get("refund_money", Double.class) != null) {
                orderVo.setRefundMoney(order.get("refund_money", Double.class));
            } else {
                orderVo.setRefundMoney(0);
            }

            // 支付类型
            String payType = order.<String>get("pay_type");
            if (StrKit.notBlank(payType)) {
                if ("0".equals(payType)) {
                    orderVo.setPayType("微信支付");
                } else if ("1".equals(payType)) {
                    orderVo.setPayType("余额支付");
                } else if ("2".equals(payType)) {
                    orderVo.setPayType("提货卡支付");
                } else if ("3".equals(payType)) {
                    orderVo.setPayType("支付宝支付");
                } else {
                    orderVo.setPayType("");
                }
            } else {
                orderVo.setPayType("");
            }
            // 状态
            String state = order.<String>get("state");
            if (StrKit.notBlank(state)) {
                if ("0".equals(state)) {
                    orderVo.setState("待支付");
                } else if ("1".equals(state)) {
                    orderVo.setState("已支付");
                } else if ("2".equals(state)) {
                    orderVo.setState("已核验");
                } else if ("3".equals(state)) {
                    orderVo.setState("已取消");
                } else if ("4".equals(state)) {
                    orderVo.setState("退款申请");
                } else if ("5".equals(state)) {
                    orderVo.setState("已退款");
                } else {
                    orderVo.setState("");
                }
            } else {
                orderVo.setState("");
            }

            // 是否推送到erp
            String isSend = order.<String>get("is_send");
            if (StrKit.notBlank(isSend)) {
                if ("0".equals(isSend)) {
                    orderVo.setIsSend("未发送");
                } else if ("1".equals(isSend)) {
                    orderVo.setIsSend("已发送");
                } else if ("2".equals(isSend)) {
                    orderVo.setIsSend("发送失败");
                } else {
                    orderVo.setIsSend("未发送");
                }
            } else {
                orderVo.setIsSend("未发送");
            }

            if (StrKit.notBlank(order.<String>get("create_time"))) {
                orderVo.setCreateTime(order.<String>get("create_time"));
            } else {
                orderVo.setCreateTime("");
            }

            if (StrKit.notBlank(order.<String>get("pay_time"))) {
                orderVo.setPayTime(order.<String>get("pay_time"));
            } else {
                orderVo.setPayTime("");
            }

            newList.add(orderVo);
            System.out.println(orderVo);

        }
        ExcelUtil excelUtil = new ExcelUtil();
        String[] header = {"门店", "订单号", "手机号", "数量", "订单金额", "实付金额", "退款金额", "付款方式", "订单状态", "发送到erp", "创建时间", "支付时间"};

        try {
            setResponseHeader(getResponse(), "订单信息.xls");
            OutputStream os = getResponse().getOutputStream();
            excelUtil.exportDataToExcel(newList, header, "订单详情", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    //发送响应流方法
    private void setResponseHeader(HttpResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /***
     * 发送订单到ERP
     * @param id
     * @return
     */
    @POST("/send")
    public boolean sendOrder(final Integer id) {
        return Order.dao.sendOrder(id);
    }

}
