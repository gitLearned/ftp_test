package com.jiuge.ftp;

import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

public class FtpClientModel {

  public FtpClientModel() {
  }

  private FTPClient ftpClient = null;
  private String host = null;
  private int port = 0;
  private String userName = null;
  private String password = null;
  private int dataTimeout = 60000;
  private int connectTimeout = 60000;
  private int localMode = 1;
  private String ip;

  /**
   * @return the ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * @param ip the ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * @return the localMode
   */
  public int getLocalMode() {
    return localMode;
  }

  /**
   * @param localMode the localMode to set
   */
  public void setLocalMode(int localMode) {
    this.localMode = localMode;
  }

  /**
   * @return the readTimeout
   */
  public int getDataTimeout() {
    return dataTimeout;
  }

  /**
   * @param readTimeout the readTimeout to set
   * @throws SocketException
   */
  public void setDataTimeout(int dataTimeout) throws SocketException {
    this.dataTimeout = dataTimeout;
    getFtpClient().setDataTimeout(dataTimeout);
  }

  public int getConnectTimeout() {
    return this.connectTimeout;
  }

  public void setConnectTimeout(int connectTimeout) throws SocketException {
    this.connectTimeout = connectTimeout;
    getFtpClient().setConnectTimeout(connectTimeout);
  }

  public String getHost() {
    return this.host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public FTPClient getFtpClient() {
    if (this.ftpClient == null) {
      this.ftpClient = new FTPClient();
      this.ftpClient.setConnectTimeout(getConnectTimeout());
      this.ftpClient.setDataTimeout(dataTimeout);
      if (getLocalMode() == 0) {
        this.ftpClient.enterLocalPassiveMode();
      } else {
        this.ftpClient.enterLocalActiveMode();
      }
    }
    return this.ftpClient;
  }

}
