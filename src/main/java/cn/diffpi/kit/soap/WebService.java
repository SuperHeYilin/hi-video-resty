/**
 * WebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.diffpi.kit.soap;

public interface WebService extends javax.xml.rpc.Service {
    public java.lang.String getWebServiceSoapAddress();

    public cn.diffpi.kit.soap.WebServiceSoapImpl getWebServiceSoap() throws javax.xml.rpc.ServiceException;

    public cn.diffpi.kit.soap.WebServiceSoapImpl getWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getWebServiceSoap12Address();

    public cn.diffpi.kit.soap.WebServiceSoapImpl12 getWebServiceSoap12() throws javax.xml.rpc.ServiceException;

    public cn.diffpi.kit.soap.WebServiceSoapImpl12 getWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
