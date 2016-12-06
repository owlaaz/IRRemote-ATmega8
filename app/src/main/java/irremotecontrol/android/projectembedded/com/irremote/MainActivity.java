package irremotecontrol.android.projectembedded.com.irremote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;


public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    String message = "";
    myDBClass myDb;
    ServerSocket serverSocket;
    List<dataIR> qcmd;
    int index;
    int status;
    int count;
    FloatingActionButton bt_setting, bt1, bt_tcp;
    TextView txr;
    TinyDB tinydb;
    List<String> plantsList;
    ArrayList<String> plantsAL;
    Context context;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = (FloatingActionButton) findViewById(R.id.button);
        bt_setting = (FloatingActionButton) findViewById(R.id.bt_setting);
        GridView gv = (GridView) findViewById(R.id.gv);
        txr = (TextView) findViewById(R.id.TextReceive);
        txr.setMovementMethod(new ScrollingMovementMethod());
        txr.setText("");

        status = 0;
        count = 0;
        index = 0;

        qcmd = new ArrayList<dataIR>();
        context = getApplicationContext();
        tinydb = new TinyDB(context);
        final String[] plants = new String[]{"OnOff,PSI","1,PSI","2,PSI","3,PSI","4,PSI","5,PSI","6,PSI","7,PSI","8,PSI","9,PSI","0,PSI","UP,PSI","DOWN,PSI","LEFT,PSI","RIGHT,PSI","OK,PSI"};
        plantsAL = new ArrayList(Arrays.asList(plants));
        /*try
        {
            plantsAL = tinydb.getListString("MyButton");
        }
        catch(Exception e)
        {

        }
        tinydb.putListString("MyButton", plantsAL);*/
        plantsList = plantsAL;
        // Create a new ArrayAdapter
        final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, plantsList);

        gv.setAdapter(gridViewArrayAdapter);

        setSharedPreferences();

        myDb = new myDBClass(this);
        db=myDb.getWritableDatabase(); // First method
        setDB();

        Thread socketServerThread = new Thread(new MainActivity.SocketServerThread());
        socketServerThread.start();


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Display the selected/clicked item text and position on TextView
                Toast toast = Toast.makeText(context, "GridView item clicked : " + selectedItem
                        + "\nAt index position : " + position, Toast.LENGTH_SHORT);
                toast.show();
                addtoQ(position);
                /*tv.setText("GridView item clicked : " +selectedItem
                        + "\nAt index position : " + position);*/
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendUDP();

            }
        });
        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Setting.class);
                startActivity(i);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void addtoQ(int cmdindex) {
        dataIR temp = new dataIR();
        if(cmdindex==0)
        {
            temp.set("OnOff", "PSI", "10001", "NEC", "A1DE21DE",32);
        }
        else if (cmdindex==1)
        {
            temp.set("1", "PSI", "00002", "NEC", "A1DEE11E",32);
        }
        else if(cmdindex==2)
        {
            temp.set("2", "PSI", "00003", "NEC", "A1DE619E",32);
        }
        else if (cmdindex==3)
        {
            temp.set("3", "PSI", "00004", "NEC", "A1DEA15E",32);
        }
        else if(cmdindex==4)
        {
            temp.set("4", "PSI", "00005", "NEC", "A1DED12E",32);
        }
        else if (cmdindex==5)
        {
            temp.set("5", "PSI", "00006", "NEC", "A1DE51AE",32);
        }
        else if(cmdindex==6)
        {
            temp.set("6", "PSI", "00007", "NEC", "A1DE916E",32);
        }
        else if (cmdindex==7)
        {
            temp.set("7", "PSI", "00008", "NEC", "A1DEF10E",32);
        }
        else if(cmdindex==8)
        {
            temp.set("8", "PSI", "00009", "NEC", "A1DE718E",32);
        }
        else if (cmdindex==9)
        {
            temp.set("9", "PSI", "00010", "NEC", "A1DEB14E",32);
        }
        else if(cmdindex==10)
        {
            temp.set("0", "PSI", "00011", "NEC", "A1DE49B6",32);
        }
        else if (cmdindex==11)
        {
            temp.set("UP", "PSI", "00012", "NEC", "A1DE817E",32);
        }
        else if(cmdindex==12)
        {
            temp.set("DOWN", "PSI", "00013", "NEC", "A1DE11EE",32);
        }
        else if (cmdindex==13)
        {
            temp.set("LEFT", "PSI", "00014", "NEC", "A1DE09F6",32);
        }
        else if(cmdindex==14)
        {
            temp.set("RIGHT", "PSI", "00015", "NEC", "A1DE41BE",32);
        }
        else if(cmdindex==15)
        {
            temp.set("OK","PSI","00016","NEC","A1DE9966",32);
        }
        qcmd.add(temp);
        Toast toast = Toast.makeText(context, qcmd.get(qcmd.size()-1).cmdID, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setDB() {
        myDb.insertData("OPEN", "JVC", "00FAF", "RC5", "48260482", 73);
        myDb.insertData("CLOSE", "JVC", "00AAF", "RC5", "48261222", 73);

    }



    private void setSharedPreferences() {
        SharedPreferences sp = getSharedPreferences("IRREMOTE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("udpIP", "255.255.255.255");
        editor.putString("udpPORT", "10000");
        editor.commit();
    }
    private  int getsum(String str)
    {
        int c=0;
        int i=0;
        while (str.charAt(i)!='\0')
        {
            c ^=str.charAt(i);
            i++;
        }
        return  c;
    }

    private void sendUDP() {
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);
        String str = "$IRCIP," + ipAddress + "*";
        String hex = getChecksum(str);
        str=str+hex;
        String str_udpIP, str_udpPORT;
        txr.append("SEND: " + str + "\n");
        Context context = getApplicationContext();
        SharedPreferences sp = getSharedPreferences("IRREMOTE", Context.MODE_PRIVATE);
        str_udpIP = sp.getString("udpIP", "255.255.255.255");
        str_udpPORT = sp.getString("udpPORT", "10000");
        if (!str_udpIP.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
            CharSequence text = "Error: Invalid IP Address";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (!str_udpPORT.matches("^(6553[0-5]|655[0-2]\\d|65[0-4]\\d\\d|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)$")) {
            CharSequence text = "Error: Invalid Port Number";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        try {
            String uriString = "udp://" + str_udpIP + ":" + str_udpPORT + "/";
            uriString += Uri.encode(str);
            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        } catch (Exception e) {

        }
    }

    private String getChecksum(String str) {
        int c = 0;
        int i = 1;
        if (str.charAt(0) == '$') {
            while (str.charAt(i) != '*') {
                c ^= str.charAt(i);
                i++;
            }
        }
        return  Integer.toString(c, 16);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction0() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    public class SocketServerThread extends Thread {
        static final int SocketServerPORT = 10000;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                      txr.append("Running.....\n");
                    }
                });
                int docmd;
                String[] separatedtype ;
                String[] separatedchecksum ;
                String[] separated2;
                int c=0;
                int cr=0;
                String type ;
                String cmd;
                String checksumcmd ;
                String length ;
                String hex;
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String incomingMsg = in.readLine();
                    String fromIP = socket.getInetAddress().toString();
                    message =incomingMsg+"from " + fromIP;
                    separatedtype = incomingMsg.split(",");
                    separatedchecksum = incomingMsg.split("\\*");
                    c=0;
                    cr=0;
                    type = separatedtype[0];
                    checksumcmd = separatedchecksum[0];
                    length = separatedchecksum[1];
                    hex=getChecksum(incomingMsg);
                    docmd =1;
                    /*
                    * 0 sendCMD(fromIP);
                    * 1 sendBlank(fromIP);
                    * 2 sendWrong(fromIP)
                    *
                    * */
                    if(hex.equalsIgnoreCase(length)){

                        if (type.equalsIgnoreCase("$IRREQ") && (!qcmd.isEmpty()) && index < qcmd.size()) {
                            separated2 = separatedtype[1].split("\\*");
                            cmd = separated2[0];
                            while (index < qcmd.size() && cmd.equalsIgnoreCase(qcmd.get(index).cmdID))
                            {
                                index++;
                            }

                            if (index < qcmd.size()) {
                                docmd=0;
                            }
                            else {
                                docmd=1;
                            }
                        }
                        else if (type.equalsIgnoreCase("$IRIVD*40") && (!qcmd.isEmpty()) && index < qcmd.size()) {
                            docmd=0;//repeat cmd
                        }
                        else {
                            docmd=1;
                        }}
                    else
                    {
                        docmd=2;
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            txr.append(message+"\n");
                        }
                    });

                    MainActivity.SocketServerReplyThread socketServerReplyThread = new MainActivity.SocketServerReplyThread(
                            socket, docmd);
                    socketServerReplyThread.run();

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    private class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;
        private int cmd;
        SocketServerReplyThread(Socket socket,int docmd) {
            hostThreadSocket = socket;
            cmd=docmd;
        }

        @Override
        public void run() {
            String msgReply="" ;
            String hex="";
            if(cmd==0)
            {
                dataIR temp;
                int re=0;
                if((qcmd.size()-index - 1)>0)re=1;
                temp = qcmd.get(index);
                if(temp.protocol.equalsIgnoreCase("PAN")||temp.protocol.equalsIgnoreCase("NEC"))
                    msgReply="$IRCMD," + re + "," + temp.cmdID + "," + temp.protocol + "," + temp.hexcode+","+temp.nbits + "*";
                else
                    msgReply="$IRCMD," + re + "," + temp.cmdID + "," + temp.protocol + "," + temp.hexcode + "*";
            }
            else if(cmd==1){
                msgReply="$IREMP*";
            }
            else
            {msgReply = "$IRIVD*";}
            hex=getChecksum(msgReply);
            if(hex.equals("0")||hex.equals("1")||hex.equals("2")||hex.equals("3")||hex.equals("4")||hex.equals("5")||hex.equals("6")||hex.equals("7")||hex.equals("8")||hex.equals("9"))
                hex="0"+hex;
            msgReply=msgReply+hex;
            try {
                OutputStream outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                msgReply=msgReply+ System.getProperty("line.separator");
                printStream.print(msgReply);
                printStream.flush();
                printStream.close();
                message = "SEND: " + msgReply;

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                       txr.append(message);
                    }
                });

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Something wrong! " + e.toString() + "\n";
            }

            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    //txr.append(message);
                }
            });
        }
    }

}

