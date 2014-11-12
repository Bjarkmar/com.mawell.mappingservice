/**
 * MapId_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mawell.mapid;

public class MapId_ServiceLocator extends org.apache.axis.client.Service implements com.mawell.mapid.MapId_Service {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapId_ServiceLocator() {
    }


    public MapId_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MapId_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
    	super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MapIdSOAP
    private java.lang.String MapIdSOAP_address = "http://localhost:8080/com.mawell.mappingservice/services/MapIdSOAP";

    public java.lang.String getMapIdSOAPAddress() {
        return MapIdSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MapIdSOAPWSDDServiceName = "MapIdSOAP";

    public java.lang.String getMapIdSOAPWSDDServiceName() {
        return MapIdSOAPWSDDServiceName;
    }

    public void setMapIdSOAPWSDDServiceName(java.lang.String name) {
        MapIdSOAPWSDDServiceName = name;
    }

    public com.mawell.mapid.MapId_PortType getMapIdSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MapIdSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMapIdSOAP(endpoint);
    }

    public com.mawell.mapid.MapId_PortType getMapIdSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.mawell.mapid.MapIdSOAPStub _stub = new com.mawell.mapid.MapIdSOAPStub(portAddress, this);
            _stub.setPortName(getMapIdSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMapIdSOAPEndpointAddress(java.lang.String address) {
        MapIdSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.mawell.mapid.MapId_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.mawell.mapid.MapIdSOAPStub _stub = new com.mawell.mapid.MapIdSOAPStub(new java.net.URL(MapIdSOAP_address), this);
                _stub.setPortName(getMapIdSOAPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("MapIdSOAP".equals(inputPortName)) {
            return getMapIdSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://mapid.mawell.com/", "MapId");
    }

    @SuppressWarnings("rawtypes")
	private java.util.HashSet ports = null;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://mapid.mawell.com/", "MapIdSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
if ("MapIdSOAP".equals(portName)) {
            setMapIdSOAPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
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
