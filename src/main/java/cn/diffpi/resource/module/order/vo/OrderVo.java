package cn.diffpi.resource.module.order.vo;

/**
 * @author super
 * @date 2018/7/10 20:15
 */
public class OrderVo {
    private String name;
    private String no;
    private String phonenum;
    private int goodsNum;
    private double orderAmount;
    private double payAmount;
    private double refundMoney;
    private String payType;
    private String state;
    private String isSend;
    private String createTime;
    private String payTime;

    public OrderVo() {
    }

    public OrderVo(String name, String no, String phonenum, int goodsNum, double orderAmount, double payAmount, double refundMoney, String payType, String state, String isSend, String createTime, String payTime) {
        this.name = name;
        this.no = no;
        this.phonenum = phonenum;
        this.goodsNum = goodsNum;
        this.orderAmount = orderAmount;
        this.payAmount = payAmount;
        this.refundMoney = refundMoney;
        this.payType = payType;
        this.state = state;
        this.isSend = isSend;
        this.createTime = createTime;
        this.payTime = payTime;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "OrderVo{" +
                "name='" + name + '\'' +
                ", no='" + no + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", goodsNum=" + goodsNum +
                ", orderAmount=" + orderAmount +
                ", payAmount=" + payAmount +
                ", refundMoney=" + refundMoney +
                ", payType='" + payType + '\'' +
                ", state='" + state + '\'' +
                ", isSend='" + isSend + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payTime='" + payTime + '\'' +
                '}';
    }
}
