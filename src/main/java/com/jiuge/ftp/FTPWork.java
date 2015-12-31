package com.jiuge.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuge.ftp.exception.FtpException;

/**
 * 
 * @author wjq1
 *
 */
public abstract class FTPWork {
  private static final Logger logger = LoggerFactory.getLogger(FTPWork.class);

  private FtpClientModel ftpClientModel = null;

  private String host;

  private int port;

  private String userName;

  private String passWord;

  private int localMode = 1;

  public FTPWork(String host, int post, String userName, String passWord, int localMode) {
    super();
    this.host = host;
    this.port = post;
    this.userName = userName;
    this.passWord = passWord;
    this.localMode = localMode;
    init(host, post, userName, passWord, localMode);

  }

  public void init(String ip, int port, String userName, String passWord, int localMode) {
    ftpClientModel = new FtpClientModel();
    ftpClientModel.setIp(ip);
    ftpClientModel.setPort(port);
    ftpClientModel.setUserName(userName);
    ftpClientModel.setPassword(passWord);
    ftpClientModel.setLocalMode(localMode);
  }

  /**
   * 
   * @param remotePath 远程路径
   * @param localPath 本地路径
   * @param fileName 要上传或者下载的文件名称
   */
  public void doWork(String remotePath, String localPath, String fileName) {
    try {
      connectServer();
      handle(remotePath, localPath, fileName);
    } catch (Exception fe) {
      logger.error("Do ftp work, ERROR! ", fe);
    } finally {
      try {
        closeConnect();
      } catch (Exception e) {
        logger.error("Close ftp connection Fail, hit exception!", e);
      }
    }

  }

  /**
   * 具体的处理 上传或者下载
   * 
   * @param remotePath 远程路径
   * @param localPath 本地路径
   * @param FileName 要上传或者下载的文件名称
   */
  public abstract void handle(String remotePath, String localPath, String FileName);

  /**
   * 切换到远程目录
   * 
   * @param filePath
   * @return
   * @throws IOException
   */
  protected void changeToWorkingDirectory(String filePath) {
    filePath = filePath.replaceAll("\\\\", "/");
    try {
      ftpClientModel.getFtpClient().changeWorkingDirectory(filePath);
    } catch (Exception e) {
      throw new FtpException("切换目录失败", e);
    }
  }

  /**
   * 连接服务
   */
  public void connectServer() {
    for (int i = 0; i < 3; i++) {
      if (doconnect()) {
        logger.info("ftp链接成功。。。。。");
        break;
      } else {
        if (i == 2) {
          logger.error("重试链接3次失败 抛出链接失败异常。。。。。");
          throw new FtpException("ftp 连接重试多次失败");
        }
      }
    }

  }

  public boolean doconnect() {
    try {
      FTPClient ftpClient = ftpClientModel.getFtpClient();
      ftpClient.connect(ftpClientModel.getIp(),
          ftpClientModel.getPort());
      ftpClient.login(ftpClientModel.getUserName(),
          ftpClientModel.getPassword());
      logger.info("恭喜" + ftpClientModel.getUserName() + "成功登陆FTP服务器");
      return true;
    } catch (Exception ex) {
      logger.error("链接失败 重试链接。。。。。", ex);
      return false;
    }
  }

  /**
   * 关闭连接
   */
  public void closeConnect() {
    try {
      ftpClientModel.getFtpClient().disconnect();
    } catch (IOException ex) {
      throw new FtpException("ftp连接关闭连接失败", ex);
    }
  }

  /**
   * @return the model
   */
  public FTPClient getFtpClient() {
    return ftpClientModel.getFtpClient();
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  /**
   * @return the port
   */
  public int getPort() {
    return port;
  }

  /**
   * @param port the port to set
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * @return the userName
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @return the passWord
   */
  public String getPassWord() {
    return passWord;
  }

  /**
   * @param passWord the passWord to set
   */
  public void setPassWord(String passWord) {
    this.passWord = passWord;
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

}
