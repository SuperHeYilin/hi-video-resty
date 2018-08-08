package cn.diffpi.resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.security.token.AccessToken;
import cn.diffpi.security.token.TokenManager;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.common.util.json.Jsoner;
import cn.dreampie.orm.Model;
import cn.dreampie.orm.page.FullPage;

public abstract class BaseModel<T extends Model<T>> extends Model<T> {

    public Integer getUserId(HttpRequest httprt) {
        String appToken = httprt.getQueryParam("token");
        if (appToken != null) {
            AccessToken token = TokenManager.getAccessToken(appToken);
            if (token != null) {
                Integer userId = Integer.valueOf(token.getExtra("userId").toString());
                return userId;
            }
        }
        throw new HttpException(HttpStatus.UNAUTHORIZED, "未登陆");
    }

    public <V> T set(String key, Object value, Class<V> clazz, Object defaultValue) {
        V result = null;
        if (value != null) {
            if (clazz.isAssignableFrom(value.getClass())) {
                result = (V) value;
            } else {
                result = Jsoner.toObject(Jsoner.toJSON(value), clazz);
            }
        } else {
            result = (V) defaultValue;
        }

        return super.set(key, result);
    }

    /**
     * 分页查询 如果对象开启缓存就使用缓存
     *
     * @param splitPage 分页参数
     * @param select    字段返回
     * @param sql       语句
     * @param params    ? 传参
     */
    public void splitPageBaseSql(SplitPage splitPage, String select, String sql, Object... params) {
        // 接收返回值对象
        StringBuilder formSqlSb = new StringBuilder();
        LinkedList<Object> paramValue = new LinkedList<Object>();
        if (params.length > 0) {
            for (Object object : params) {
                paramValue.add(object);
            }
        }
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
        try {
            FullPage<T> page;
            if (super.isUseCache()) {
                page = super.getMClass().newInstance().fullPaginate(splitPage.getPageNumber(), splitPage.getPageSize(), select + formSql, paramValue.toArray());
            } else {
                page = super.getMClass().newInstance().unCache().fullPaginate(splitPage.getPageNumber(), splitPage.getPageSize(), select + formSql, paramValue.toArray());
            }
            splitPage.setTotalPage(page.getTotalPage());
            splitPage.setTotalRow(page.getTotalRow());
            splitPage.setList(page.getList());
            splitPage.compute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getMap() {

        Map<String, String> propMap = new HashMap<String, String>();

        for (Entry<String, Object> obj : super.getAttrs().entrySet()) {
            propMap.put(obj.getKey(), String.valueOf(obj.getValue()));
        }

        return propMap;
    }

    /***
     * 设置参数自动过滤掉非法参数
     * @param key
     * @param value
     * @return
     */
    public T setByFilter(String key, Object value) {
        if (super.hasColumn(key)) {
            set(key, value);
        }

        return (T) this;
    }

}
