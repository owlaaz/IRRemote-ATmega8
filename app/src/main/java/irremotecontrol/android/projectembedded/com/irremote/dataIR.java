package irremotecontrol.android.projectembedded.com.irremote;

/**
 * Created by NotMe on 19/11/2559.
 */

public class dataIR {
    public String func;
    public String model;
    public String cmdID;
    public String protocol;
    public String hexcode;
    public String size;
    public int nbits;

    dataIR()
    {   func="";
        model="";
        cmdID="";
        protocol="";
        hexcode="";
        size="";
        nbits =0;
    }
    public void  set(String f,String m,String c,String p,String h)
    {
        this.func=f;
        this.model=m;
        this.cmdID=c;
        this.protocol=p;
        this.hexcode=h;
        this.nbits=0;
    }
    public void  set(String f,String m,String c,String p,String h,int n)
    {
        this.func=f;
        this.model=m;
        this.cmdID=c;
        this.protocol=p;
        this.hexcode=h;
        this.nbits=n;
    }

}
