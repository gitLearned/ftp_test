package com.jiuge.ftp.exception;

/**
 * ftp 异常类
 * 
 * @author wjq1
 *
 */
public class FtpException extends RuntimeException {
  private static final long serialVersionUID = 2116541845397356819L;

  public FtpException() {
    super();
  }

  public FtpException(String message, Throwable cause) {
    super(message, cause);
  }

  public FtpException(String message) {
    super(message);
  }

  public FtpException(Throwable cause) {
    super(cause);
  }

}
