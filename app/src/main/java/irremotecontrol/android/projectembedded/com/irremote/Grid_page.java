package irremotecontrol.android.projectembedded.com.irremote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by NotMe on 19/11/2559.
 */

public class Grid_page extends Activity{
    EditText tx;
    Context context;
    TinyDB tinydb;
    List<String> plantsList;
    ArrayList<String> plantsAL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_page);
        context = getApplicationContext();
        // Get the widgets reference from XML layout
        GridView gv = (GridView) findViewById(R.id.gv);
        Button btn = (Button) findViewById(R.id.btn);
        tx=(EditText)findViewById(R.id.editText) ;
        tinydb = new TinyDB(context);
        // Initializing a new String Array
        final String[] plants = new String[]{};
        plantsAL = new ArrayList( Arrays.asList( plants ) );
        // Populate a List from Array elements
        //List<String> plantsList = new ArrayList<String>(Arrays.asList(plants));
        try{
            plantsAL=tinydb.getListString("MyButton");
        }
        catch (Exception e)
        {

        }
        plantsList = plantsAL;
        // Create a new ArrayAdapter
        final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1, plantsList);

        // Data bind GridView with ArrayAdapter (String Array elements)
        gv.setAdapter(gridViewArrayAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Display the selected/clicked item text and position on TextView
                Toast toast = Toast.makeText(context, "GridView item clicked : " +selectedItem
                        + "\nAt index position : " + position, Toast.LENGTH_SHORT);
                toast.show();
                /*tv.setText("GridView item clicked : " +selectedItem
                        + "\nAt index position : " + position);*/
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add/insert item to ArrayAdapter
                // Insert at the end of ArrayAdapter
                // ArrayAdapter is zero based index
                Context context = getApplicationContext();
                if(tx.getText().toString().equalsIgnoreCase(""))
                {

                    // Display the selected/clicked item text and position on TextView
                    Toast toast = Toast.makeText(context,"Please Type Text", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    //plantsList.add(plantsList.size(),tx.getText().toString());
                    plantsAL.add(plantsAL.size(),tx.getText().toString());
                    tinydb.putListString("MyButton",plantsAL);
                    plantsList = plantsAL;
                    // Update the GridView
                    gridViewArrayAdapter.notifyDataSetChanged();

                    // Get the newly added item from ArrayAdapter
                    String addedItemText = plantsList.get(plantsList.size()-1);

                    // Confirm the addition
                    Toast.makeText(getApplicationContext(),
                            "Item added : " + addedItemText, Toast.LENGTH_SHORT).show();
                    tx.setText("");
                }

            }
        });
    }
}
