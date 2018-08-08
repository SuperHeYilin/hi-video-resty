package cn.diffpi.resource.module.statistics.service;

import cn.dreampie.orm.Record;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public static final Record dao = new Record();

    /**
     * 获取某个时间段的销售额
     *
     * @param start
     * @param end
     * @param state 订单状态
     * @return
     **/
    public Object getMoney(String start, String end, Character state) {
        String sql = "SELECT SUM(pay_amount) from module_order where pay_time >= ? and pay_time < ? and state = ?";
        return dao.find(sql, start, end, state).get(0);
    }

    /**
     * 查询最近多少天的数据
     *
     * @param day
     * @return
     */
    public List<Record> getRecently(int day) {
        String sql = "SELECT DATE_FORMAT(t.pay_time,'%Y-%m-%d') as x,SUM(pay_amount) as y from module_order t WHERE DATE_FORMAT(t.pay_time,'%Y-%m-%d')>\n" +
                "DATE_FORMAT(date_sub(curdate(), interval ? DAY),'%Y-%m-%d') and state = 2 GROUP BY x";

        return dao.find(sql, day);
    }

    /**
     * 获取最近多少小时的数据
     *
     * @param hour
     * @return
     */
    public List<Record> getHour(int hour) {
        String sql2 = "select DATE_FORMAT(pay_time,'%H') h,DATE_FORMAT(pay_time,'%H:%i') hm,sum(pay_amount) n from module_order where " +
                "pay_time > DATE_SUB(NOW(),INTERVAL ? HOUR) and state = 2 GROUP BY h";
        String sql = "SELECT date_format(pay_time,'%H:00') h, SUM(pay_amount) n from module_order where" +
                " DATE(pay_time) = DATE(NOW()) and state = 2 GROUP BY x";
        return dao.find(sql, hour);
    }

    /**
     * 返回过去第几天的销售额 0 今天 1 昨天 2 前天 。。。
     *
     * @param n     间隔
     * @param state 状态
     * @return
     */
    public Record getSomeDay(int n, String state) {
        String sql = "SELECT SUM(pay_amount) as pay_amount FROM module_order WHERE" +
                " TO_DAYS( NOW( ) ) - TO_DAYS(pay_time) = ? and state = ?";
        return dao.findFirst(sql, n, state);
    }

    /**
     * 查询今天某个状态的销售额
     *
     * @return
     */
    public Record getTodayMoney(String state) {
        String sql = "SELECT SUM(pay_amount) as pay_amount from module_order where DATE(pay_time) = DATE(NOW()) and state = ? and del = 0 ";
        Record record = dao.findFirst(sql, state);
        return record;
    }

    /**
     * 查询今天 按小时分部数据
     *
     * @return
     */
    public List<Record> getTodayForHour() {
        String sql = "SELECT date_format(pay_time,'%H:00') x, SUM(pay_amount) y from module_order where" +
                " DATE(pay_time) = DATE(NOW()) and state = 2 GROUP BY x";
        return dao.find(sql);
    }

    /**
     * 查询本周的数据 按日排列
     *
     * @return
     */
    public List<Record> getWeekMoney() {
        String sql = "SELECT date(date_format(pay_time,'%Y-%m-%d')) x, SUM(pay_amount) y from module_order where pay_time BETWEEN\n" +
                "(select subdate(curdate(),if(date_format(curdate(),'%w')=0,7,date_format(curdate(),'%w'))-1)) AND\n" +
                "(select subdate(curdate(),if(date_format(curdate(),'%w')=0,7,date_format(curdate(),'%w'))-7)) AND state = 2 GROUP BY x";
        return dao.find(sql);
    }

    /**
     * 查询当月 按日排列
     *
     * @return
     */
    public List<Record> getMonthForDay() {
        String sql = "SELECT DATE_FORMAT(pay_time,'%Y-%m-%d') x,SUM(pay_amount) y from module_order where " +
                "DATE_FORMAT(pay_time,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m') and state = 2 GROUP BY x";
        return dao.find(sql);
    }

    /**
     * 查询最近多少年当月的销售额 0 本年 1 去年
     *
     * @param state
     * @return
     */
    public Record getMonthMoney(int year, String state) {
        String sql = "SELECT SUM(pay_amount) as month_money from module_order where YEAR(pay_time) = (SELECT YEAR(NOW()) - ?) and" +
                " MONTH(pay_time) = (SELECT MONTH(NOW())) and state = ?";
        Record record = dao.findFirst(sql, year, state);
        return record;
    }

    /**
     * 分别查询最近多少月的销售额
     *
     * @param month
     * @return
     */
    public List<Record> getRecentlyMonth(int month, String state) {
        String sql = "SELECT DATE_FORMAT(t.pay_time,'%Y-%m') as x,SUM(pay_amount) as y from" +
                " module_order t WHERE DATE_FORMAT(t.pay_time,'%Y-%m')>\n" +
                "DATE_FORMAT(date_sub(curdate(), interval ? month),'%Y-%m') and state = ? GROUP BY x";
        return dao.find(sql, month, state);
    }

    /**
     * 查询最近某年的销售额 0 今年 1 去年 。。。
     *
     * @param state
     * @return
     */
    public Record getYearMoney(int y, String state) {
        String sql = "SELECT SUM(pay_amount) as year_money from module_order where YEAR(pay_time) =" +
                " (SELECT YEAR(NOW()) - ?) and state = ?";
        Record record = dao.findFirst(sql, y, state);
        return record;
    }

    /**
     * 获得年平均销售额
     *
     * @return
     */
    public Record getAvgYear() {
        String sql = "SELECT AVG(temp.s) avg_year_money from (SELECT SUM(t.pay_amount) s,DATE_FORMAT(t.pay_time,'%Y') y " +
                "from module_order t where state = 2 GROUP BY y) as temp";
        Record record = dao.findFirst(sql);
        return record;
    }


    /**
     * 查询今年销售额 按月分部
     *
     * @return
     */
    public List<Record> getYearByMonthMoney() {
        String sql = "SELECT DATE_FORMAT(t.pay_time,'%Y-%m') as x,SUM(pay_amount) as y from module_order t " +
                "WHERE YEAR(pay_time) = (SELECT YEAR(NOW())) GROUP BY x";
        return dao.find(sql);
    }

    /**
     * 查询某个状态的全部销售额
     *
     * @return
     */
    public Record getAllMoney() {
        String sql = "SELECT SUM(pay_amount) as allmoney from module_order where state = 2";
        Record record = dao.findFirst(sql);
        return record;
    }


    /**
     * 支付类型排名 微信 余额 提货卡
     *
     * @return
     */
    public List<Record> getPayTypeRanking() {
        String sql1 = "SELECT b.type_name AS x,COUNT(a.pay_type) AS y from module_order a,pay_type b" +
                " where a.pay_type=b.pay_type GROUP BY x";
        String sql = "SELECT pay_type as x,COUNT(pay_type) as y from module_order GROUP BY pay_type";
        return dao.find(sql);
    }

    /**
     * 热门产品排名
     *
     * @return
     */
    public List<Record> getGoodsTypeRanking() {
        List<Record> list = new ArrayList<>();
        String sql = "SELECT goods_name x, COUNT(goods_name) y from module_order_goods GROUP BY x " +
                "ORDER BY y DESC LIMIT 0,5";
        String sql2 = "SELECT CASE c.x WHEN 0 THEN '其它' ELSE '其它' END x,SUM(c.y) y FROM " +
                "(SELECT goods_name x, COUNT(goods_name) y from module_order_goods GROUP BY " +
                "x ORDER BY y DESC LIMIT 3,999999) c";
        list = dao.find(sql);
        list.add(dao.findFirst(sql2));
        return list;
    }


    /**
     * 得到某个状态的订单数量
     *
     * @param state
     * @return
     **/
    public Record getOrderCount(String state) {
        String sql = "SELECT COUNT(*) as n from module_order where state = ? and del = 0 ";
        return dao.findFirst(sql, state);
    }
}
