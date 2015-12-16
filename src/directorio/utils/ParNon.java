package directorio.utils;

/**
 *  Used to emit a stream of alterating string values: "even", "odd", etc.  This
 *  is often used in the Inspector pages to make the class of a &lt;tr&gt; alternate
 *  for presentation reasons.
 *
 *  @version $Id: ParNon.java,v 1.1 2010/09/08 17:44:41 cvsima Exp $
 *  @author Howard Lewis Ship
 *
 **/

public class ParNon
{
    private boolean even = true;

    /**
     *  Returns "even" or "odd".  Whatever it returns on one invocation, it will
     *  return the opposite on the next.  By default, the first value
     *  returned is "even".
     *
     **/

    public String getNext()
    {
        String result = even ? "par" : "non";

        even = !even;

        return result;
    }
    
    public boolean isEven()
    {
        return even;
    }

	/**
	 *  Overrides the even flag.
	 * 
	 **/
	
    public void setEven(boolean value)
    {
        even = value;
    }
}
