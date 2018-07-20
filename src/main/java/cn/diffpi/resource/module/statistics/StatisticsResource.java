package cn.diffpi.resource.module.statistics;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.statistics.service.OrderService;
import cn.diffpi.resource.module.statistics.service.StoreService;
import cn.diffpi.resource.module.statistics.service.UserService;
import cn.dreampie.orm.Record;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@API("/pt/statistics")
public class StatisticsResource extends ApiResource {

    UserService user = new UserService();
    OrderService order = new OrderService();
    StoreService store = new StoreService();


    /**
     * 打包SimpleCard组件数据
     * @return
     */
    @GET("/simplecard")
    public Map<String, Object> getSimpleCard() {
        Map<String, Object> map = new HashMap<>();
        //添加用户注册数量
        map.put("person_num", user.getCount());
        //添加今日销售额 已验证
        map.put("today_money", order.getTodayMoney("2"));
        //添加待审核订单数
        map.put("reimburse", order.getOrderCount("1"));
        //添加支付笔数 已经核验
        map.put("order_count", order.getOrderCount("2"));
        return map;
    }

    /**
     * 获取第二部分数据
     * @return
     */
    @GET("/secondcard")
    public Map<String, Object> getSecondCard() {
        Map<String, Object> map = new HashMap<>();

        // 添加总销售额 状态 1
        map.put("all_money", order.getAllMoney());

        // 添加年平均销售额
        map.put("avg_year_money", order.getAvgYear());

        // 添加本月销售额
        map.put("month_money", order.getMonthMoney(0, "2"));

        //当月去年当月销售额
        double thisMonth = 0;
        double lastYearMonth = 0;
        if (order.getMonthMoney(0, "2").get("month_money") != null) {
            thisMonth = Double.parseDouble(order.getMonthMoney(0, "2").get("month_money").toString());
        }
        if ( order.getMonthMoney(1, "2").get("month_money") != null) {
            lastYearMonth = Double.parseDouble(order.getMonthMoney(1, "2").get("month_money").toString());
        }
        // 添加当月的同比增长率
        map.put("month_year_compare", toFormat(thisMonth, lastYearMonth, 2));

        //今日昨日销售额
        double thisDay = 0;
        double lastDay = 0;
        if (order.getSomeDay(0, "2").get("pay_amount") != null ) {
            thisDay = Double.parseDouble(order.getSomeDay(0, "2").get("pay_amount").toString());
        }
        if ( order.getSomeDay(1, "2").get("pay_amount") != null) {
            lastDay = Double.parseDouble(order.getSomeDay(1, "2").get("pay_amount").toString());
        }
        //添加当日的同比增长率
        map.put("day_year_compare", toFormat(thisDay,lastDay, 2));

        //添加月平均销售额
        double thisAvgMonth = 0;
        if (order.getMonthMoney(0, "2").get("month_money") != null) {
            thisAvgMonth = Double.parseDouble(order.getMonthMoney(0, "2").get("month_money").toString());
        }
        map.put("month_avg", formatAvg(thisAvgMonth));

        //今年去年销售额
        double thisYear = 0;
        double lastYear = 0;
        if (order.getYearMoney(0, "2").get("year_money") != null ) {
            thisYear = Double.parseDouble(order.getYearMoney(0, "2").get("year_money").toString());
        }
        if ( order.getYearMoney(1, "2").get("year_money") != null) {
            lastYear = Double.parseDouble(order.getYearMoney(1, "2").get("year_money").toString());
        }
        // 添加昨日销售额
        map.put("yesterday", lastDay);

        //添加当年的同比增长率
        map.put("year_year_compare", toFormat(thisYear,lastYear, 2));

        // 查询最近18个月的数据
        map.put("recently_month", order.getRecentlyMonth(18, "2"));

        // 查询最近18天的数据
        map.put("recently_day", order.getRecently(18));

        // 查询今年的销售额
        map.put("year_money", thisYear);

        return map;
    }

    /**
     * 返回第三部分数据
     * @return
     */
    @GET("/thirdcard")
    public Map<String, Object> getThirdCard() {
        Map<String, Object> map = new HashMap<>();
        //添加门店排名 前7
        map.put("ranking_store", store.getRanking(7));
        //今年每月销售情况
        map.put("ranking_eachmonth", order.getYearByMonthMoney());
        //添加今年每月用户注册量
        map.put("register_eachmonth", user.getYearByMonth());

        return map;
    }

    /**
     * 返回第三部分 时间切换的数据
     * @return
     */
    @POST("/thirdtime")
    public List<Record> getThirdCardTime(String type) {
       switch (type) {
           case "today":
               return order.getTodayForHour();
           case "week":
               return order.getWeekMoney();
           case "month":
               return order.getMonthForDay();
           default:
               return order.getYearByMonthMoney();
       }
    }

    /**
     * 时间切换查询销售额
     * @param d1 起始
     * @param d2 结束
     * @return
     */
    @POST("/thirdtimechange")
    public List<Record> getTimeChange(String d1, String d2) {

//        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//        String startDate = simpleFormat.format(d1);
////        Date d = simpleFormat.parse(d1);
//        String endDate = simpleFormat.format(d2);
//
//        Date start = null;
//        Date end = null;
//        long diffDays = 0L;
//        try {
//            start = simpleFormat.parse(startDate);
//            end = simpleFormat.parse(endDate);
//
//            long diff = end.getTime() - start.getTime();
//
//            diffDays = diff / (24 * 60 * 60 * 1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 计算时间天数差

        long diffDays = DateUtil.getCurrentTimeDifference(d2, d1, "yyyy-MM-dd hh:mm:ss");

        StringBuffer sb = new StringBuffer();
//        String sb1 = "SELECT DATE_FORMAT(pay_time,'%Y-%m-%d') x, SUM(pay_amount) y from module_order " +
//                "WHERE pay_time BETWEEN '2017-12-01 18:24:13' AND '2017-12-08 18:24:13' and state = 2 GROUP BY x";
//        String state = null;

        sb.append("SELECT DATE_FORMAT(pay_time,'");

        if (diffDays <= 0) {
            sb.append("");
        }

        //大于0天 小于30天 按天分部数据
        if (diffDays > 0 && diffDays <= 30 ) {
            sb.append("%Y-%m-%d");
        }

        // 大于30 小于365 按月分部数据
        if (diffDays > 30 && diffDays <= 366) {
            sb.append("%Y-%m");
        }

        // 大于365 按年分部数据
        if (diffDays >= 366) {
            sb.append("%Y");
        }

        sb.append("') x, SUM(pay_amount) y from module_order " +
                "WHERE pay_time BETWEEN ? AND ? and state = 2 GROUP BY x");

        return new Record().find(sb.toString(), d1, d2);
    }


    /**
     * 返回第四部分数据
     * @return
     */
    @GET("/fourthcard")
    public Map<String, Object> getFourthCard() {
        Map<String, Object> map = new HashMap<>();

        //添加支付类型占比
        map.put("payTypeData", order.getPayTypeRanking());

        //添加热门商品占比
        map.put("goodsRankingData", order.getGoodsTypeRanking());

        //添加最近多少小时的数据
        map.put("recentlyHour", order.getTodayForHour());

        return map;
    }

    @GET("/lastcard")
    public Map<String, Object> getLastCard() {
        Map<String, Object> map = new HashMap<>();

        //添加门店销售情况 前9999
        map.put("storeRanking", store.getRanking(99999));

        //总销售额 用于算门店百分比
        map.put("allMoney", order.getAllMoney());

        //添加最近18天数据
        map.put("recently", order.getRecently(18));

        return map;
    }

    /**
     * 计算同比增长率
     * @param a 今年
     * @param b 去年
     */
//    public String toFormat(double a, double b) {
//        // 创建一个数值格式化对象
////        NumberFormat numberFormat = NumberFormat.getInstance();
//        // 设置精确到小数点后2位
////        numberFormat.setMaximumFractionDigits(2);
////        numberFormat.setMinimumIntegerDigits(3);
//
////        String result = numberFormat.format(( (float) a - (float) b )/ (float) b * 100);
////        String result1 = numberFormat.format((  a -  b )/  b * 100);
//
//
//
//
//        String result = String.format("%.2f",(  a -  b )/  b );
//        System.out.println("同比增长率：" + a + " : " + b + " = " + result + "%");
//
//        return result;
//    }

    /**
     * 计算同比环比
     * @param v1 当前 今年 当月 今日
     * @param v2 比较 去年 去年当月 昨日
     * @param scale 保留的小数位数
     * @return
     */
    private double toFormat(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留小数 参数出错");
        }
        BigDecimal b2 ;
        if (v2 == 0) {
        	b2 = new BigDecimal(Double.toString(1));
        }else{
        	b2 = new BigDecimal(Double.toString(v2));
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b3 = new BigDecimal(Double.toString(100.00));

        System.out.println("新的增长率：当前：" + b1 + " 对比：" + b2 + " 结果: " +
                (b1.subtract(v2==0?new BigDecimal(0):b2)).multiply(b3).divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue()
        );

        return (b1.subtract(v2==0?new BigDecimal(0):b2)).multiply(b3).divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 计算月平均销售
     * @return
     */
    private String formatAvg(double m) {
        // 获取当月第几天
        int n = Integer.parseInt(DateUtil.getCurrentDate("dd"));

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        String result = numberFormat.format(m / n);

        System.out.println(m +" ÷ " + n + "月平均销售额" + result);

        return result;
    }


//    /**
//     * 获取当日的销售额
//     * @return
//     */
//
//    public Object getTodayMoney() {
//        String start = DateUtil.getCurrentDate(DateUtil.DAY_MIN) + " 00:00:00";
//        String end = DateUtil.getCurrentAfert(DateUtil.getCurrentDate(), DateUtil.DAY_MIN) + " 00:00:00";
//        Character state = '2'; //已经审核
//        return order.getMoney(start, end, state).toString();
//    }

//    /**
//     * 获取当月销售
//     * @return
//     */
//    public double getMonthMoney() {
//        String start = DateUtil.getCurrentDate("yyyy-MM") + "-01 00:00:00";
//        String end = DateUtil.getCurrentDate();
//        Character state = '2'; //已经审核
//        return Double.parseDouble(order.getMoney(start, end, state).toString());
//    }


}
