package com.ns.greg.library.fasthook.exception;

/**
 * @author Gregory
 * @since 2017/3/13
 */
public class EasyException extends Exception {

  public static final int OTHER_CAUSE = -1;
  public static final int RUNNABLE_OBJECT_METHOD_ERROR = 1;
  public static final int INTERRUPTED_ERROR = 2;

  private int code;

  public EasyException(Throwable cause) {
    super(cause);

    code = OTHER_CAUSE;
  }

  public EasyException(int code, String message) {
    super(message);
    this.code = code;
  }

  public EasyException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * Access the code for this error.
   *
   * @return The numerical code for this error.
   */
  public int getCode() {
    return code;
  }
}
