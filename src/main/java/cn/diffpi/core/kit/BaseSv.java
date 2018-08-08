package cn.diffpi.core.kit;

import java.util.LinkedList;

import cn.dreampie.log.Logger;
import cn.dreampie.orm.Record;
import cn.dreampie.orm.page.FullPage;

public class BaseSv {
    public Logger log = Logger.getLogger(getClass());

    public static BaseSv me = new BaseSv();

    /**
     * 分页
     *
     * @param dataSource 数据源
     * @param splitPage
     * @param select
     */
    public void splitPageBaseSql(SplitPage splitPage, String select, String sql, Object... paramValue) {
        // 接收返回值对象
        StringBuilder formSqlSb = new StringBuilder();

        // 调用生成from sql，并构造paramValue
        formSqlSb.append(sql);

        // 行级：过滤

        // 排序
        String orderColunm = splitPage.getOrderColunm();
        String orderMode = splitPage.getOrderMode();
        if (null != orderColunm && !orderColunm.isEmpty() && null != orderMode && !orderMode.isEmpty()) {
            formSqlSb.append(" order by ").append(orderColunm).append(" ").append(orderMode);
        }

        String formSql = formSqlSb.toString();

        FullPage<?> page = new Record().fullPaginate(splitPage.getPageNumber(), splitPage.getPageSize(), select + formSql, paramValue);
        splitPage.setTotalPage(page.getTotalPage());
        splitPage.setTotalRow(page.getTotalRow());
        splitPage.setList(page.getList());
        splitPage.compute();
    }

    /**
     * 分页
     *
     * @param dataSource 数据源
     * @param splitPage
     * @param select
     */
    public void splitPageBaseSql(SplitPage splitPage, String select, String sql) {
        // 接收返回值对象
        StringBuilder formSqlSb = new StringBuilder();
        LinkedList<Object> paramValue = new LinkedList<Object>();

        // 调用生成from sql，并构造paramValue
        formSqlSb.append(sql);

        // 行级：过滤

        // 排序
        String orderColunm = splitPage.getOrderColunm();
        String orderMode = splitPage.getOrderMode();
        if (null != orderColunm && !orderColunm.isEmpty() && null != orderMode && !orderMode.isEmpty()) {
            formSqlSb.append(" order by ").append(orderColunm).append(" ").append(orderMode);
        }

        String formSql = formSqlSb.toString();

        FullPage<?> page = new Record().fullPaginate(splitPage.getPageNumber(), splitPage.getPageSize(), select + formSql, paramValue.toArray());
        splitPage.setTotalPage(page.getTotalPage());
        splitPage.setTotalRow(page.getTotalRow());
        splitPage.setList(page.getList());
        splitPage.compute();
    }
}
