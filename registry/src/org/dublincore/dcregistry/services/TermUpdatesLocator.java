/*
 * The contents of this file, as updated from time to time by Dublin Core
 * Metadata Initiative (DCMI), are subject to the Dublin Core Public
 * License Version 1.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a current copy of the
 * License at http://dublincore.org/dcpl/.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Dublin Core Metadata Initiative. For
 * more information on the Dublin Core Metadata Initiative, please see
 * http://dublincore.org/.
 *
 * This is Original Code.
 *
 * The Developer of the Original Code is:
 *  H.Wagner    <wagnerh@oclc.org>
 * 
 * Portions created by DCMI are Copyright (C) 2000. 
 *
 */

package org.dublincore.dcregistry.services;

public class TermUpdatesLocator extends org.apache.axis.client.Service implements org.dublincore.dcregistry.services.TermUpdates {

    // Use to get a proxy class for termUpdates
    //private final java.lang.String termUpdates_address = "http://localhost:8080/axis/services/termUpdates";
		private final java.lang.String termUpdates_address = "must_be_supplied_by_invoker";

    public java.lang.String gettermUpdatesAddress() {
        return termUpdates_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String termUpdatesWSDDServiceName = "termUpdates";

    public java.lang.String gettermUpdatesWSDDServiceName() {
        return termUpdatesWSDDServiceName;
    }

    public void settermUpdatesWSDDServiceName(java.lang.String name) {
        termUpdatesWSDDServiceName = name;
    }

    public org.dublincore.dcregistry.services.Updates gettermUpdates() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(termUpdates_address);
        }
        catch (java.net.MalformedURLException e) {
            return null; // unlikely as URL was validated in WSDL2Java
        }
        return gettermUpdates(endpoint);
    }

    public org.dublincore.dcregistry.services.Updates gettermUpdates(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.dublincore.dcregistry.services.TermUpdatesSoapBindingStub _stub = new org.dublincore.dcregistry.services.TermUpdatesSoapBindingStub(portAddress, this);
            _stub.setPortName(gettermUpdatesWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.dublincore.dcregistry.services.Updates.class.isAssignableFrom(serviceEndpointInterface)) {
                org.dublincore.dcregistry.services.TermUpdatesSoapBindingStub _stub = new org.dublincore.dcregistry.services.TermUpdatesSoapBindingStub(new java.net.URL(termUpdates_address), this);
                _stub.setPortName(gettermUpdatesWSDDServiceName());
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
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("termUpdates".equals(inputPortName)) {
            return gettermUpdates();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        //return new javax.xml.namespace.QName("http://localhost:8080/axis/services/termUpdates", "termUpdates");
				return new javax.xml.namespace.QName("must_be_supplied_by_invoker", "termUpdates");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("termUpdates"));
        }
        return ports.iterator();
    }

}
