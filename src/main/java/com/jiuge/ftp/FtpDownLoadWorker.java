package com.jiuge.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jiuge.ftp.exception.FtpException;

/**
 * ftp下载操作
 * 
 * @author wjq1
 *
 */
public class FtpDownLoadWorker extends FTPWork {
  private static final Logger logger = LoggerFactory.getLogger(FTPWork.class);

  public FtpDownLoadWorker(String host, int port, String userName, String passWord, int localMode) {
    super(host, port, userName, passWord, localMode);

  }

  /**
   * 下载文件
   * 
   * @param remoteFile远程文件路径(服务器端)
   * @param localFile本地文件路径(客户端)
   */
  @Override
  public void handle(String remotePath, String localPath, String fileName) {
    // 本地文件
    File localFile = new File(Paths.get(localPath, fileName).toUri());
    // 下载如果本地文件已经存在 则删除（暂时没有做成和远程文件比对大小 根据情况判断是否重新下载的情况）
    if (localFile.exists()) {
      localFile.delete();
    }
    // 本地临时文件 下载完成后重命名为fileName
    File localTempFile = new File(localPath, fileName + ".tmp");

    try (FileOutputStream os = new FileOutputStream(localTempFile)) {
      changeToWorkingDirectory(remotePath);

      boolean success = getFtpClient().retrieveFile(fileName, os);

      if (success == true) {
        logger.info(fileName + "成功下载到" + localPath);
      }
    } catch (Exception e) {
      throw new FtpException("下载文件失败", e);
    }
    // 重命名文件为最终文件
    localTempFile.renameTo(localFile);
  }

  @Override
  public FTPClient getFtpClient() {
    return super.getFtpClient();
  }
}
