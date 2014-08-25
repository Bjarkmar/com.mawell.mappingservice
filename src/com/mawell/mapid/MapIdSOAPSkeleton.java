/**
 * MapIdSOAPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.mawell.mapid;

public class MapIdSOAPSkeleton implements com.mawell.mapid.MapId_PortType, org.apache.axis.wsdl.Skeleton {
    private com.mawell.mapid.MapId_PortType impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "input"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://mapid.mawell.com/", "searchFields"), com.mawell.mapid.SearchFields[].class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getFields", _params, new javax.xml.namespace.QName("", "fieldSet"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://mapid.mawell.com/", "fieldSet"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://mapid.mawell.com/", "getFields"));
        _oper.setSoapAction("http://mapid.mawell.com/MapId/getFields");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getFields") == null) {
            _myOperations.put("getFields", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getFields")).add(_oper);
    }

    public MapIdSOAPSkeleton() {
        this.impl = new com.mawell.mapid.MapIdSOAPImpl();
    }

    public MapIdSOAPSkeleton(com.mawell.mapid.MapId_PortType impl) {
        this.impl = impl;
    }
    public com.mawell.mapid.FieldSet[] getFields(java.lang.String id, java.lang.String idType, com.mawell.mapid.SearchFields[] input) throws java.rmi.RemoteException
    {
        com.mawell.mapid.FieldSet[] ret = impl.getFields(id, idType, input);
        return ret;
    }

}
