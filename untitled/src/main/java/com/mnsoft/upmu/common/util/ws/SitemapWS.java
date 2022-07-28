package com.mnsoft.upmu.common.util.ws;

public interface SitemapWS extends javax.xml.rpc.Service {
    public String getSitemapWSSoapAddress();

    public SitemapWSSoap getSitemapWSSoap() throws javax.xml.rpc.ServiceException;

    public SitemapWSSoap getSitemapWSSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
