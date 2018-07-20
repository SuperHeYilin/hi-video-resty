package cn.diffpi.core.kit;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mysql.fabric.xmlrpc.base.Param;

import cn.diffpi.kit.StrKit;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.orm.Model;
import cn.dreampie.route.core.Params;

/**
 * ModelInjector
 */
public final class ModelInjector {
	
	@SuppressWarnings("unchecked")
	public static <T> T inject(Class<?> modelClass, Params p, boolean skipConvertError,boolean isHasqz) {
		String modelName = modelClass.getSimpleName();
		return (T)inject(modelClass, StrKit.firstCharToLowerCase(modelName), p, skipConvertError,isHasqz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final <T> T inject(Class<?> modelClass, String modelName, Params p, boolean skipConvertError,boolean isHasqz) {
		Object model = null;
		try {
			model = modelClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (model instanceof Model)
			injectActiveRecordModel((Model)model, modelName, p, skipConvertError,isHasqz);
		else
			injectCommonModel(model, modelName, p, modelClass, skipConvertError,isHasqz);
		
		return (T)model;
	}
	
	private static final void injectCommonModel(Object model, String modelName, Params p, Class<?> modelClass, boolean skipConvertError,boolean isHasqz) {
		Method[] methods = modelClass.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set") == false)	// only setter method
				continue;
			
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1)						// only one parameter
				continue;
			
			String attrName = methodName.substring(3);
			Object value = null;
			if(isHasqz){
			    if(p.get(StrKit.firstCharToLowerCase(attrName))!=null){
			        value=p.get(StrKit.firstCharToLowerCase(attrName),Object.class);
			    }
			}else{
				p.get(modelName + "." + StrKit.firstCharToLowerCase(attrName));
			}
//			request.getQueryParam(modelName + "." + StrKit.firstCharToLowerCase(attrName));
			if (value != null) {
				try {
					method.invoke(model, TypeConverter.convert(types[0], value.toString()));
				} catch (Exception e) {
					if (skipConvertError == false)
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static final void injectActiveRecordModel(Model<?> model, String modelName, Params p, boolean skipConvertError,boolean isHasqz) {
		String modelNameAndDot = modelName + ".";
		if(isHasqz){
			modelNameAndDot="";
		}
		for(int i=0;i<p.getNames().length;i++){
			String paraKey=p.getNames()[i];
			if(paraKey.startsWith(modelNameAndDot)) {
				String paraName = paraKey.substring(modelNameAndDot.length());
				try{
					Class colType = model.getColumnType(paraName);
				}catch(Exception e1){continue;};
				try {
					model.set(paraName,p.getValues()[i]);
				} catch (Exception ex) {
					if (skipConvertError == false)
					  throw new RuntimeException("Can not convert parameter: " + modelNameAndDot + paraName, ex);
				}
			}
		}
//		Map<String, List<String>> parasMap = request.getQueryParams();
//		for (Entry<String, List<String>> e : parasMap.entrySet()) {
//			String paraKey = e.getKey();
//			if (paraKey.startsWith(modelNameAndDot)) {
//				String paraName = paraKey.substring(modelNameAndDot.length());
//				try{
//				Class colType = model.getColumnType(paraName);
//				}catch(Exception e1){
//					continue;
//				}
////				if (colType == null) return;
//					//throw new ActiveRecordException("The model attribute " + paraKey + " is not exists.");
//				
//				List<String> paraValue = e.getValue();
//				try {
//					// Object value = Converter.convert(colType, paraValue != null ? paraValue[0] : null);
//					//Object value = paraValue.get(0) != null ? TypeConverter.convert(colType, paraValue.get(0)) : null;
//					model.set(paraName, paraValue.get(0));
//				} catch (Exception ex) {
//					if (skipConvertError == false)
//						throw new RuntimeException("Can not convert parameter: " + modelNameAndDot + paraName, ex);
//				}
//			}
//		}
	}
}

