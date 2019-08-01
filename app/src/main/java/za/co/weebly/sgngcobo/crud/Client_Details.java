package za.co.weebly.sgngcobo.crud;
/*
 * copyright @2017 By   : Siyabonga Gregory
 * Developer's Website  : www.sgngcobo.weebly.com
 * Developer's Number   : 084-492-1275
 * Developer's E-mail   : huge.fuze@gmail.com
 * */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class Client_Details extends AppCompatActivity {
    public static final String Cell_Phone = "";
    public static final String Email = "";
    public static final String Home_Tel = "";
    public static final String Name = "";
    public static ArrayList<String> ServiceArray = new ArrayList();
    public static final String ServiceData = "";
    public static final String Surname = "";
    public static final String Work_Tel = "";
    public static int intPkey = 0;
    public static String pKey = "";
    Button btnToVehicle;
    EditText txtCellPhone;
    EditText txtEmail;
    EditText txtHomeTel;
    EditText txtName;
    EditText txtSurname;
    EditText txtWorkTel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_client__details);
        DBHelper newDb = new DBHelper(this);
        Intent intent = getIntent();
        intPkey = new Integer(Integer.parseInt(intent.getStringExtra(MainActivity.pKey))).intValue();
        pKey = intent.getStringExtra(MainActivity.pKey);
        this.txtName = (EditText) findViewById(R.id.txtFullName);
        this.txtSurname = (EditText) findViewById(R.id.txtSurname);
        this.txtEmail = (EditText) findViewById(R.id.txtEmail);
        this.txtHomeTel = (EditText) findViewById(R.id.txtHomeTelNo);
        this.txtWorkTel = (EditText) findViewById(R.id.txtWorkTelNo);
        this.txtCellPhone = (EditText) findViewById(R.id.txtCelPhoneNumber);
        Cursor c = newDb.forYourVehicle(DBHelper.TABLE_NAME, intent.getStringExtra(MainActivity.pKey), ServiceArray, "record");
        while (c.moveToNext()) {
            this.txtName.setText(c.getString(1));
            this.txtSurname.setText(c.getString(2));
            this.txtEmail.setText(c.getString(3));
            this.txtWorkTel.setText(c.getString(4));
            this.txtHomeTel.setText(c.getString(5));
            this.txtCellPhone.setText(c.getString(6));
        }
    }

    public void ToVehicleDetails(View view) {
        DBHelper newDb = new DBHelper(this);
        this.txtName = (EditText) findViewById(R.id.txtFullName);
        this.txtSurname = (EditText) findViewById(R.id.txtSurname);
        this.txtEmail = (EditText) findViewById(R.id.txtEmail);
        this.txtHomeTel = (EditText) findViewById(R.id.txtHomeTelNo);
        this.txtWorkTel = (EditText) findViewById(R.id.txtWorkTelNo);
        this.txtCellPhone = (EditText) findViewById(R.id.txtCelPhoneNumber);
        Intent intent = new Intent(this, Vehicle_Details.class);
        ServiceArray.add(0, this.txtName.getText().toString());
        ServiceArray.add(1, this.txtSurname.getText().toString());
        ServiceArray.add(2, this.txtEmail.getText().toString());
        ServiceArray.add(3, this.txtHomeTel.getText().toString());
        ServiceArray.add(4, this.txtWorkTel.getText().toString());
        ServiceArray.add(5, this.txtCellPhone.getText().toString());
        if (intPkey <= 0) {
            intent.putStringArrayListExtra("", ServiceArray);
            intent.putExtra(pKey, pKey);
            startActivity(intent);
        } else if (Boolean.valueOf(newDb.updateClientDetails(Integer.valueOf(intPkey), ServiceArray)).booleanValue()) {
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            intent.putStringArrayListExtra("", ServiceArray);
            intent.putExtra(pKey, pKey);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Update Failed  ", Toast.LENGTH_SHORT).show();
        }
    }
}
