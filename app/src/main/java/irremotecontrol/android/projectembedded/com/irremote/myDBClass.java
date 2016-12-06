package irremotecontrol.android.projectembedded.com.irremote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NotMe on 19/11/2559.
 */

    public class myDBClass extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "dataIR";
    public static final String COL_func = "function";
    public static final String COL_model = "model";
    public static final String COL_cmdID = "cmdID";
    public static final String COL_protocol = "protocol";
    public static final String COL_hexcode = "hexcode";
    public static final String COL_size = "size";
        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "mydatabase";

        // Table Name
        private static final String TABLE_MEMBER = "dataIR";

        public myDBClass(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            String sql = "CREATE TABLE "+TABLE_NAME +" ( "
                    +COL_func + " TEXT, "
                    +COL_model + "TEXT, "
                    +COL_cmdID + " TEXT PRIMARY KEY , "
                    +COL_protocol + " TEXT, "
                    +COL_hexcode + " TEXT, "
                    +COL_size +" INTEGER);";
            db.execSQL(sql);

        }
    public void insertData(String func,String model,String cmdID,String protocol,String hexcode,int size)
    {
        try {SQLiteDatabase db;
        db = this.getWritableDatabase();
        String sql="INSERT INTO "+TABLE_NAME+" ("
                +COL_func+", "
                +COL_model+", "
                +COL_cmdID+", "
                +COL_protocol+", "
                +COL_hexcode+ ", "
                +COL_size+") VALUES ("
                +func+", "
                +model+", "
                +cmdID+", "
                +protocol+", "
                +hexcode+ ", "
                +size+" );";

            db.execSQL(sql);
        }
        catch (Exception e)
        {

        }

    }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    }

