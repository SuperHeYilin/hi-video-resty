/**
 * WebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.diffpi.kit.soap;

import javax.xml.rpc.ServiceException;

public class WebServiceServer {
    public static WebServiceServer wb = new WebServiceServer();
    private WebService ws;
    public WebServiceServer(){
        ws=new WebServiceLocator();
    }
    
    public WebServiceSoapImpl getWebServiceSoap() throws ServiceException{
        return ws.getWebServiceSoap();
    }
    
    public WebServiceSoapImpl12 getWebServiceSoap12() throws ServiceException{
        return ws.getWebServiceSoap12();
    }
   
}
