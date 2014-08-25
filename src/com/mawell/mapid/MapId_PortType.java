/**
 * MapId_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mawell.mapid;

public interface MapId_PortType extends java.rmi.Remote {
    public com.mawell.mapid.FieldSet[] getFields(java.lang.String id, java.lang.String idType, com.mawell.mapid.SearchFields[] input) throws java.rmi.RemoteException;
}
