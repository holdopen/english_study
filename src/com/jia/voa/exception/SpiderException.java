package com.jia.voa.exception;


public class SpiderException extends Exception {
	/**
     * 
     */
	private static final long serialVersionUID = 4509935684739611377L;
	private Exception cause;

	public SpiderException(String detailMessage, Exception cause) {
		super(detailMessage, cause.getCause());
		this.cause = cause;
	}

	public SpiderException(String detailMessage) {
		super(detailMessage);
	}

	public SpiderException(Throwable cause) {
		super(cause.getCause());
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
		cause.printStackTrace();
	}
}
