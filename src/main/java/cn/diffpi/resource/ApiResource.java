package cn.diffpi.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import cn.diffpi.core.MyHttpRequest;
import cn.diffpi.core.kit.ModelInjector;
import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.StringKit;
import cn.diffpi.security.token.AccessToken;
import cn.diffpi.security.token.TokenManager;
import cn.dreampie.client.Client;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.common.util.properties.Proper;
import cn.dreampie.orm.Record;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.core.Params;
import cn.dreampie.route.core.Resource;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by one__l on 15-1-16.
 */
@API("/api/b/v1.0")
public class ApiResource extends Resource
{
    private String url = Proper.getProp("application.properties").get(
            "refreshcache.url");

    public void RefreshCache(String className, Boolean blean)
    {
        if (blean)
        {
            Client c = new Client(url + className + "?auth=admin");
            c.get();
        }
    }

    /**
     * 可以做一些公用的方法
     */
    public SplitPage splitPageBaseSql(Integer start, Integer end)
    {

        return null;
    }

    public SplitPage getSplitPage()
    {
        SplitPage page = new SplitPage();
        String pageNumber = getParam("pageNumber");
        String pageSize = getParam("pageSize");
        String orderColunm = getParam("orderColunm");
        String orderMode = getParam("orderMode");
        page.setPageNumber(Integer.parseInt(pageNumber));
        page.setPageSize(Integer.parseInt(pageSize));
        page.setOrderColunm(orderColunm);
        page.setOrderMode(orderMode);
        return page;
    }

    public Integer getUserId()
    {
        MyHttpRequest myHttpRequest = (MyHttpRequest) getRequest();
        String appToken = myHttpRequest.getAppToken();
        if (appToken != null)
        {
            AccessToken token = TokenManager.getAccessToken(appToken);
            if (token != null)
            {
                Integer userId = Integer.valueOf(token.getExtra("userId")
                        .toString());
                return userId;
            }
        }
        throw new HttpException(HttpStatus.UNAUTHORIZED, "未登陆");
        // return 12;
    }

    /**
     * 获取以modelName开头的参数，并自动赋值给record对象
     * 
     * @author lb
     * @date 2016年9月2日23:34:18
     * @param modelName
     * @return
     */
    public Record getRecord(String modelName)
    {
        String modelNameAndDot = modelName + ".";
        Record model = new Record();
        boolean exist = false;
        Map<String, List<String>> parasMap = getRequest().getQueryParams();
        for (Entry<String, List<String>> e : parasMap.entrySet())
        {
            String paraKey = e.getKey();
            if (paraKey.startsWith(modelNameAndDot))
            {
                String paraName = paraKey.substring(modelNameAndDot.length());
                List<String> paraValue = e.getValue();
                Object value = paraValue.get(0) != null ? (paraValue.size() == 1 ? paraValue
                        .get(0) : StringKit.getListStr(paraValue, ","))
                        : null;
                model.set(paraName, value);
                exist = true;
            }
        }
        if (exist)
        {
            return model;
        }
        else
        {
            return null;
        }
    }

    /**
     * 获取前端传来的数组对象并响应成Record列表
     * 
     * @author lb
     * @date 2016年9月2日23:33:50
     * @param modelName
     * @return
     */
    public List<Record> getRecords(String modelName)
    {
        List<String> nos = getModelsNoList(modelName);
        List<Record> list = new ArrayList<Record>();
        for (String no : nos)
        {
            Record r = getRecord(modelName + "[" + no + "]");
            if (r != null)
            {
                list.add(r);
            }
        }
        return list;
    }

    /**
     * 获取前端传来的数组对象并响应成Model列表
     * 
     * @author lb
     * @date 2016年9月2日23:33:54
     * @param modelClass
     * @param modelName
     * @return
     */
    public <T> List<T> getModels(Class<T> modelClass, String modelName)
    {
        List<String> nos = getModelsNoList(modelName);
        List<T> list = new ArrayList<T>();
        for (String no : nos)
        {
            T m = getModel(modelClass, modelName + "[" + no + "]");
            if (m != null)
            {
                list.add(m);
            }
        }
        return list;
    }

    /**
     * 提取model对象数组的标号
     * 
     * @author lb
     * @date 22016年9月2日23:33:59
     * @param modelName
     * @return
     */
    private List<String> getModelsNoList(String modelName)
    {
        // 提取标号
        List<String> list = new ArrayList<String>();
        String modelNameAndLeft = modelName + "[";
        Params p = getParams();
        for (String str : p.getNames())
        {
            if (str.startsWith(modelNameAndLeft))
            {
                String no = str.substring(str.indexOf("[") + 1,
                        str.indexOf("]"));
                if (!list.contains(no))
                {
                    list.add(no);
                }
            }
        }
        return list;
    }

    public Map<String, String> getModel(String modelName)
    {
        Map<String, String> map = new HashMap<String, String>();
        String modelNameAndDot = modelName + ".";
        Map<String, List<String>> parasMap = getRequest().getQueryParams();
        for (Entry<String, List<String>> e : parasMap.entrySet())
        {
            String paraKey = e.getKey();
            if (paraKey.startsWith(modelNameAndDot))
            {
                String paraName = paraKey.substring(modelNameAndDot.length());
                map.put(paraName, e.getValue().get(0));
            }
        }
        return map;
    }

    /**
     * @param modelClass
     * @param isHasqz
     *            不需要前缀 true 需要 false 或者不传 需要
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getModel(Class<T> modelClass, Boolean... isHasqz)
    {
        if (isHasqz.length > 0)
        {
            return (T) ModelInjector.inject(modelClass, getParams(), false,
                    isHasqz[0]);
        }
        return (T) ModelInjector.inject(modelClass, getParams(), false, false);
    }

    @SuppressWarnings("unchecked")
    public <T> T getModel(Class<T> modelClass, String modelName,
            Boolean... isHasqz)
    {
        if (isHasqz.length > 0)
        {
            return (T) ModelInjector.inject(modelClass, modelName, getParams(),
                    false, isHasqz[0]);
        }
        return (T) ModelInjector.inject(modelClass, modelName, getParams(),
                false, false);
    }

    public Locale getLocale(String language)
    {
        language = StringUtils.isEmpty(language) ? "zh_CN" : language;
        return new Locale(language);
    }

    public Locale getLocale()
    {
        return getRequest().getLocale();
    }

}
