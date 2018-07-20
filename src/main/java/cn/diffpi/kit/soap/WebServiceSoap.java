/**
 * WebServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.diffpi.kit.soap;

public interface WebServiceSoap extends java.rmi.Remote {

    /**
     * 获取航班列表
     */
    public java.lang.String getFlightList(java.lang.String strJson, int count, int dataIndex) throws java.rmi.RemoteException;

    /**
     * 获取航班详细信息
     */
    public java.lang.String getFlightInfo(java.lang.String fltid) throws java.rmi.RemoteException;

    /**
     * 获取航班详细信息
     */
    public java.lang.String getFlightInfoByUserid(java.lang.String fltid, java.lang.String strUserName) throws java.rmi.RemoteException;

    /**
     * 获取航班保障详情
     */
    public java.lang.String getFlighGuarInfo(java.lang.String fltid) throws java.rmi.RemoteException;

    /**
     * 获取机组详情
     */
    public java.lang.String getFlighUnitInfo(java.lang.String fltid) throws java.rmi.RemoteException;

    /**
     * 获取天气信息
     */
    public java.lang.String getFlighWeaInfo(java.lang.String fltid) throws java.rmi.RemoteException;

    /**
     * 获取航班航行通告
     */
    public java.lang.String getFlighNotInfoByFlt(java.lang.String fltid) throws java.rmi.RemoteException;

    /**
     * 航行通告查询
     */
    public java.lang.String getFlighNotInfoByIcao(java.lang.String strAirValues, java.lang.String strFirValues) throws java.rmi.RemoteException;

    /**
     * 天气查询
     */
    public java.lang.String getFlighWeaInfoByIcao(java.lang.String strAirValues, java.lang.String strFirValues) throws java.rmi.RemoteException;

    /**
     * 获取机场CODE对应表
     */
    public java.lang.String getAirports() throws java.rmi.RemoteException;

    /**
     * 获取情报区CODE对应表
     */
    public java.lang.String getFirs() throws java.rmi.RemoteException;

    /**
     * 获取运行状态
     */
    public java.lang.String getFltRunState(java.lang.String sType, java.lang.String sDate, java.lang.String eDate) throws java.rmi.RemoteException;

    /**
     * 获取运行状态航班列表数据
     */
    public java.lang.String getFltListByRun(java.lang.String strJson) throws java.rmi.RemoteException;

    /**
     * 获取运行状态检测项趋势数据
     */
    public java.lang.String getFltRunTre(java.lang.String strRunType, java.lang.String sType, java.lang.String sDate, java.lang.String eDate) throws java.rmi.RemoteException;

    /**
     * 获取航班调整通知
     */
    public java.lang.String getFltAdjSheets(java.lang.String date) throws java.rmi.RemoteException;

    /**
     * 获取航班调整明细
     */
    public java.lang.String getFltAdjs(java.lang.String strSheetId) throws java.rmi.RemoteException;

    /**
     * 电报查询
     */
    public java.lang.String getTelex(int iCount, int iPageIndex, java.lang.String strKey, java.lang.String dataSource, java.lang.String sDate, java.lang.String eDate) throws java.rmi.RemoteException;

    /**
     * 获取发报等级列表
     */
    public java.lang.String getTelexGrade() throws java.rmi.RemoteException;

    /**
     * 电报发送
     */
    public boolean insertTelex(java.lang.String strJson) throws java.rmi.RemoteException;

    /**
     * 个人飞行记录查询
     */
    public java.lang.String getFltDetall(int iCount, int iPageIndex, java.lang.String strUserid, java.lang.String sDate, java.lang.String eDate) throws java.rmi.RemoteException;

    /**
     * 个人飞行记录明细
     */
    public java.lang.String getFltDetallInfo(java.lang.String strUserid, java.lang.String strFltid) throws java.rmi.RemoteException;

    /**
     * 安全员任务查询
     */
    public java.lang.String getSafeTask() throws java.rmi.RemoteException;

    /**
     * 根据卡号获取安全员ID
     */
    public java.lang.String getSafeUserByNo(java.lang.String strCrewid) throws java.rmi.RemoteException;

    /**
     * 获取安全员
     */
    public java.lang.String getSafeUser() throws java.rmi.RemoteException;

    /**
     * 短信发送
     */
    public java.lang.String toMessage_Phone(java.lang.String phones, java.lang.String strMessage) throws java.rmi.RemoteException;
}
