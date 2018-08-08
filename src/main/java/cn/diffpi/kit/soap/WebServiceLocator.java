/**
 * WebServiceLocator.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.diffpi.kit.soap;

public class WebServiceLocator extends org.apache.axis.client.Service implements cn.diffpi.kit.soap.WebService {

    public WebServiceLocator() {

    }


    public WebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebServiceSoap
    private java.lang.String WebServiceSoap_address = "http://119.84.122.105:12345/WebService.asmx";

    public java.lang.String getWebServiceSoapAddress() {
        return WebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebServiceSoapWSDDServiceName = "WebServiceSoap";

    public java.lang.String getWebServiceSoapWSDDServiceName() {
        return WebServiceSoapWSDDServiceName;
    }

    public void setWebServiceSoapWSDDServiceName(java.lang.String name) {
        WebServiceSoapWSDDServiceName = name;
    }

    public cn.diffpi.kit.soap.WebServiceSoapImpl getWebServiceSoap() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebServiceSoap_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebServiceSoap(endpoint);
    }

    public cn.diffpi.kit.soap.WebServiceSoapImpl getWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.diffpi.kit.soap.WebServiceSoapImpl _stub = new cn.diffpi.kit.soap.WebServiceSoapImpl(portAddress, this);
            _stub.setPortName(getWebServiceSoapWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebServiceSoapEndpointAddress(java.lang.String address) {
        WebServiceSoap_address = address;
    }


    // Use to get a proxy class for WebServiceSoap12
    private java.lang.String WebServiceSoap12_address = "http://119.84.122.105:12345/WebService.asmx";

    public java.lang.String getWebServiceSoap12Address() {
        return WebServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebServiceSoap12WSDDServiceName = "WebServiceSoap12";

    public java.lang.String getWebServiceSoap12WSDDServiceName() {
        return WebServiceSoap12WSDDServiceName;
    }

    public void setWebServiceSoap12WSDDServiceName(java.lang.String name) {
        WebServiceSoap12WSDDServiceName = name;
    }

    public cn.diffpi.kit.soap.WebServiceSoapImpl12 getWebServiceSoap12() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebServiceSoap12_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebServiceSoap12(endpoint);
    }

    public cn.diffpi.kit.soap.WebServiceSoapImpl12 getWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.diffpi.kit.soap.WebServiceSoapImpl12 _stub = new cn.diffpi.kit.soap.WebServiceSoapImpl12(portAddress, this);
            _stub.setPortName(getWebServiceSoap12WSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebServiceSoap12EndpointAddress(java.lang.String address) {
        WebServiceSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.diffpi.kit.soap.WebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.diffpi.kit.soap.WebServiceSoapImpl _stub = new cn.diffpi.kit.soap.WebServiceSoapImpl(new java.net.URL(WebServiceSoap_address), this);
                _stub.setPortName(getWebServiceSoapWSDDServiceName());
                return _stub;
            }
            if (cn.diffpi.kit.soap.WebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.diffpi.kit.soap.WebServiceSoapImpl12 _stub = new cn.diffpi.kit.soap.WebServiceSoapImpl12(new java.net.URL(WebServiceSoap12_address), this);
                _stub.setPortName(getWebServiceSoap12WSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WebServiceSoap".equals(inputPortName)) {
            return getWebServiceSoap();
        } else if ("WebServiceSoap12".equals(inputPortName)) {
            return getWebServiceSoap12();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://HXHK/", "WebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://HXHK/", "WebServiceSoap"));
            ports.add(new javax.xml.namespace.QName("http://HXHK/", "WebServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("WebServiceSoap".equals(portName)) {
            setWebServiceSoapEndpointAddress(address);
        } else if ("WebServiceSoap12".equals(portName)) {
            setWebServiceSoap12EndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
