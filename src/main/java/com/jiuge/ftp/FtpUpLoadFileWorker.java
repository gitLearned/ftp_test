package com.jiuge.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuge.ftp.exception.FtpException;

public class FtpUpLoadFileWorker extends FTPWork {
  private static final Logger logger = LoggerFactory.getLogger(FtpUpLoadFileWorker.class);

  public FtpUpLoadFileWorker(String host, int port, String userName, String passWord,
      int localMode) {
    super(host, port, userName, passWord, localMode);

  }

  @Override
  public void handle(String remotePath, String localPath, String fileName) {
    boolean success = false;
    changeToWorkingDirectory(remotePath);// 改变工作路径
    File localFile = Paths.get(localPath, fileName).toFile();
    if (!localFile.exists()) {
      throw new FtpException("本地文件不存在");
    }
    try (FileInputStream inputStream = new FileInputStream(localFile)) {
      logger.info(localFile.getName() + "开始上传.....");
      success = getFtpClient().storeFile(localFile.getName(), inputStream);
      if (success == true) {
        logger.info(localFile.getName() + "上传成功");
      }
    } catch (Exception e) {
      throw new FtpException("下载文件失败", e);
    }

  }

  @Override
  public FTPClient getFtpClient() {
    return super.getFtpClient();
  }
}
