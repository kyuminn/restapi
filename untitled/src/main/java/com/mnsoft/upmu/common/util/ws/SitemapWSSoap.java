package com.mnsoft.upmu.common.util.ws;

public interface SitemapWSSoap extends java.rmi.Remote {

    /**
     * 암호화된 문장에 대한 복호화
     */
    public String getPlainText(String strEncID, String strCompanyCode, String strEncText) throws java.rmi.RemoteException;
}
