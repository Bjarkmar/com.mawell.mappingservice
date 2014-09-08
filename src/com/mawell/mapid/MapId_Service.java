/**
 * MapId_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mawell.mapid;

public interface MapId_Service extends javax.xml.rpc.Service {
    public java.lang.String getMapIdSOAPAddress();

    public com.mawell.mapid.MapId_PortType getMapIdSOAP() throws javax.xml.rpc.ServiceException;

    public com.mawell.mapid.MapId_PortType getMapIdSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
