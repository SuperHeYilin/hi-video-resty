package cn.diffpi.config;

import java.util.Map;
import java.util.Set;

import cn.diffpi.core.MyHandler;
import cn.diffpi.core.PerUrlManager;
import cn.diffpi.core.intercepter.MySecurityIntercepter;
import cn.diffpi.i18n.MessageSourceAccessorUtil;
import cn.dreampie.common.spring.SpringPlugin;
import cn.dreampie.log.Colorer;
import cn.dreampie.orm.ActiveRecordPlugin;
import cn.dreampie.orm.provider.druid.DruidDataSourceProvider;
import cn.dreampie.quartz.QuartzPlugin;
import cn.dreampie.route.config.Config;
import cn.dreampie.route.config.ConstantLoader;
import cn.dreampie.route.config.HandlerLoader;
import cn.dreampie.route.config.InterceptorLoader;
import cn.dreampie.route.config.PluginLoader;
import cn.dreampie.route.config.ResourceLoader;
import cn.dreampie.route.core.Route;
import cn.dreampie.route.handler.cors.CORSHandler;
import cn.dreampie.route.interceptor.transaction.TransactionInterceptor;

public class AppConfig extends Config {
    public void configConstant(ConstantLoader constantLoader) {
        // 单页应用 避免解析路径springframework
        constantLoader.setDefaultForward("/");

        MessageSourceAccessorUtil.add("cn/diffpi/response/i18n/err/error");
        MessageSourceAccessorUtil.initMessageSource();
    }

    public void configResource(ResourceLoader resourceLoader) {
        // 设置resource的目录 减少启动扫描目录
        resourceLoader.addIncludePackages("cn.diffpi.resource");
    }

    public void configPlugin(PluginLoader pluginLoader) {
        DruidDataSourceProvider ddsp = new DruidDataSourceProvider("default");
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(ddsp);
        activeRecordPlugin.addIncludePackages("cn.diffpi.resource");
//         pluginLoader.add(new SpringPlugin(SpringConfig.class));
        pluginLoader.add(activeRecordPlugin);
//        pluginLoader.add(new QuartzPlugin("default", false));
        Colorer.devEnable(false);
    }

    public void configInterceptor(InterceptorLoader interceptorLoader) {
        // 权限拦截器 limit 为最大登录session数
        // interceptorLoader.add(new MySessionInterceptor(new
        // MyAuthenticateService()));
        // 事务的拦截器 @Transaction
        interceptorLoader.add(new TransactionInterceptor());

        // 权限拦截器
        interceptorLoader.add(new MySecurityIntercepter());
    }

    public void afterRouterBuild(Map<String, Map<String, Set<Route>>> routesMap) {

    }

    public void configHandler(HandlerLoader handlerLoader) {
        // 跨域

        CORSHandler corsHandler = new CORSHandler();
        corsHandler.setAllowedMethods("GET", "POST", "PUT", "DELETE");
        corsHandler.setAllowedOrigins("http://localhost:3000");
        corsHandler.setAllowedOrigins("http://192.168.1.107:3000");
        corsHandler.setAllowedHeaders("*");
        handlerLoader.add(corsHandler);
        // handlerLoader.add(new MyHandler());
    }

    /**
     * Call back after Resty start
     */
    public void afterStart() {
        PerUrlManager.me.pass();
    }
}
