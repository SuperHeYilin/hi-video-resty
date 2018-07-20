package cn.diffpi.i18n;

import java.util.HashSet;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageSourceAccessorUtil {
	public static MessageSourceAccessor messageSourceAccessor;
	
	private static final HashSet<String> baseNamesSet = new HashSet<String>();
	
	/**
     * 设置国际化资源信息
     */
    public static void initMessageSource() {
        String[] totalBaseNames = baseNamesSet.toArray(new String[0]);

        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setBasenames(totalBaseNames);
        
        MessageSourceAccessorUtil.messageSourceAccessor = new MessageSourceAccessor(bundleMessageSource);
    }

    public static void add(String path){
    	baseNamesSet.add(path);
    }
}
