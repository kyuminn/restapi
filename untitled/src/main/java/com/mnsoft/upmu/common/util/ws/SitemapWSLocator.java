package com.mnsoft.upmu.common.util.ws;

public class SitemapWSLocator extends org.apache.axis.client.Service implements SitemapWS {

    public SitemapWSLocator() {
    }


    public SitemapWSLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SitemapWSLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SitemapWSSoap
    private String SitemapWSSoap_address = "https://autoway.hyundai.net/WebServices/Common/SitemapWSF/SitemapWS.asmx";

    public String getSitemapWSSoapAddress() {
        return SitemapWSSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String SitemapWSSoapWSDDServiceName = "SitemapWSSoap";

    public String getSitemapWSSoapWSDDServiceName() {
        return SitemapWSSoapWSDDServiceName;
    }

    public void setSitemapWSSoapWSDDServiceName(String name) {
        SitemapWSSoapWSDDServiceName = name;
    }

    public SitemapWSSoap getSitemapWSSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SitemapWSSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSitemapWSSoap(endpoint);
    }

    public SitemapWSSoap getSitemapWSSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SitemapWSSoapStub _stub = new SitemapWSSoapStub(portAddress, this);
            _stub.setPortName(getSitemapWSSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSitemapWSSoapEndpointAddress(String address) {
        SitemapWSSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SitemapWSSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                SitemapWSSoapStub _stub = new SitemapWSSoapStub(new java.net.URL(SitemapWSSoap_address), this);
                _stub.setPortName(getSitemapWSSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
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
        String inputPortName = portName.getLocalPart();
        if ("SitemapWSSoap".equals(inputPortName)) {
            return getSitemapWSSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "SitemapWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "SitemapWSSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("SitemapWSSoap".equals(portName)) {
            setSitemapWSSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
