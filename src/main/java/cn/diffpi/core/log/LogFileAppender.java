package cn.diffpi.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

public class LogFileAppender extends RollingFileAppender<ILoggingEvent>   {

private String currentlyActiveFile;
	
	@Override
	protected void subAppend(ILoggingEvent event){
		
		if(currentlyActiveFile == null){
			currentlyActiveFile = getFile();
		}
		
		setFile(currentlyActiveFile);
		start();
		super.subAppend(event);
	}
}
