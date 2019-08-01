package za.co.weebly.sgngcobo.crud;
/*
 * copyright @2017 By   : Siyabonga Gregory
 * Developer's Website  : www.sgngcobo.weebly.com
 * Developer's Number   : 084-492-1275
 * Developer's E-mail   : huge.fuze@gmail.com
 * */
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String pKey = "";
    ArrayAdapter<String> Adapter;
    ArrayList<String> Services = new ArrayList();
    Button btnBegin;
    public int deleteKey = 0;
    Intent intent = null;
    final DBHelper newDb = new DBHelper(this);
    ArrayList<String> pkeys = new ArrayList();
    ListView txtMainList;
    EditText txtSearch;

    class C02331 implements OnItemClickListener {
        C02331() {
        }

        public void onItemClick(AdapterView<?> adapterView, View agr1, int index, long id) {
            MainActivity.pKey = "";
            MainActivity.this.confirmMessage(agr1);
            Toast.makeText(MainActivity.this.getApplicationContext(), (CharSequence) MainActivity.this.Services.get(index),Toast.LENGTH_LONG).show();
            MainActivity.this.intent = new Intent(MainActivity.this, Client_Details.class);
            MainActivity.this.intent.putExtra(MainActivity.pKey, (String) MainActivity.this.pkeys.get(index));
            MainActivity.this.deleteKey = new Integer((String) MainActivity.this.pkeys.get(index)).intValue();
        }
    }

    class C02342 implements TextWatcher {
        C02342() {
        }

        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            MainActivity.this.reloadListViewByReferenceNumber();
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        public void afterTextChanged(Editable arg0) {
        }
    }

    class C02353 implements OnClickListener {
        C02353() {
        }

        public void onClick(DialogInterface arg0, int arg1) {
            Status.setStatus("NULL");
            MainActivity.this.startActivity(MainActivity.this.intent);
        }
    }

    class C02364 implements OnClickListener {
        C02364() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (MainActivity.this.newDb.deleteRecord(Integer.valueOf(MainActivity.this.deleteKey))) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "Record has been successfully deleted  " + MainActivity.this.deleteKey, Toast.LENGTH_SHORT).show();
                MainActivity.this.reloadListView();
                return;
            }
            Toast.makeText(MainActivity.this.getApplicationContext(), "Process failed  ", Toast.LENGTH_SHORT).show();
        }
    }

    class C02375 implements OnClickListener {
        C02375() {
        }

        public void onClick(DialogInterface arg0, int arg1) {
            Status.setStatus("Add_Vehicle");
            MainActivity.this.startActivity(MainActivity.this.intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.btnBegin = (Button) findViewById(R.id.btnBegin);
        this.txtMainList = (ListView) findViewById(R.id.txtMainList);
        this.txtSearch = (EditText) findViewById(R.id.txtSearch);
        reloadListView();
        this.Adapter = new ArrayAdapter(this, R.layout.activity_main, this.Services);
        this.txtMainList.setOnItemClickListener(new C02331());
        this.txtSearch.addTextChangedListener(new C02342());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bookIn:
                Intent intent = new Intent(this, Client_Details.class);
                intent.putExtra(pKey, "0");
                startActivity(intent);
                return true;
            case R.id.recordFault:
                Intent faultIntent = new Intent(this, Fault.class);
                faultIntent.putExtra(pKey, "0");
                startActivity(faultIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void reloadListView() {
        this.Services.clear();
        Cursor c = this.newDb.getClientDetails();
        while (c.moveToNext()) {
            this.Services.add("Name        : " + c.getString(1) + "\n" + "Surname  : " + c.getString(2));
            this.pkeys.add(c.getString(0));
        }
        this.txtMainList.setAdapter(this.Adapter);
        this.newDb.close();
    }

    public void reloadListViewByReferenceNumber() {
        try {
            this.Services.clear();
            this.pkeys.clear();
            Cursor c = this.newDb.forYourVehicle(DBHelper.TABLE_NAME, this.txtSearch.getText().toString(), this.Services, "record");
            while (c.moveToNext()) {
                this.Services.add("Name        : " + c.getString(1) + "\n" + "Surname  : " + c.getString(2));
                this.pkeys.add(c.getString(0));
            }
            this.txtMainList.setAdapter(this.Adapter);
            this.newDb.close();
        } catch (Exception e) {
            reloadListView();
        }
    }

    public void confirmMessage(View view) {
        Builder alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setMessage((CharSequence) "Add Vehicle,Update Or Delete Record ? ");
        alertDialogBuilder.setNegativeButton((CharSequence) "Update", new C02353());
        alertDialogBuilder.setPositiveButton((CharSequence) "Delete", new C02364());
        alertDialogBuilder.setNeutralButton((CharSequence) "Add Vehicle", new C02375());
        alertDialogBuilder.create().show();
    }

    public void ToClientDetails(View view) {
        Intent intent = new Intent(this, Client_Details.class);
        intent.putExtra(pKey, "0");
        startActivity(intent);
    }
}
