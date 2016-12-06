package irremotecontrol.android.projectembedded.com.irremote;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by NotMe on 18/11/2559.
 */

public class Setting extends Activity{
    EditText udpIP,udpPORT;
    Button  apply;
    String str_udpIP,str_udpPORT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        udpIP = (EditText)findViewById(R.id.udp_IP);
        udpPORT = (EditText)findViewById(R.id.udp_PORT);
        apply = (Button)findViewById(R.id.apply);
        SharedPreferences sp = getSharedPreferences("IRREMOTE", Context.MODE_PRIVATE);
        str_udpIP = sp.getString("udpIP",null);
        str_udpPORT = sp.getString("udpPORT",null);
        udpIP.setText(str_udpIP);
        udpPORT.setText(str_udpPORT);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("IRREMOTE", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("udpIP",udpIP.getText().toString());
                editor.putString("udpPORT",udpPORT.getText().toString());
                editor.commit();
                finish();
            }
        });
    }
}
