package com.googlecode.jsendnsca.sender;

/**
 *  Thrown if an exception is encountered while sending a Nagios Passive Alert
 *	@author Raj.Patel
 */
public class NagiosException extends Exception 
{

	private static final long serialVersionUID = 5630051795639637370L;

	/**
     * Constructs an instance of <code>NagiosException</code> with the cause
     * @param cause the cause
     */
    public NagiosException(Throwable cause)
    {
    	super(cause);
    }

    /**
     * Constructs an instance of <code>NagiosException</code> with the cause
     * @param msg the detail message.
     * @param cause the cause
     */
    public NagiosException(String msg, Throwable cause)
    {
    	super(msg,cause);
    }

    /**
     * Creates a new instance of <code>NagiosException</code> without detail message.
     */
    public NagiosException()
    {
    }

    /**
     * Constructs an instance of <code>NagiosException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NagiosException(String msg)
    {
        super(msg);
    }

}
