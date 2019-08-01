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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class Vehicle_Details extends AppCompatActivity {
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
    Button btnSave;
    final DBHelper newDb = new DBHelper(this);
    EditText txtDateBookedIn;
    EditText txtMake;
    EditText txtMileage;
    EditText txtModel;
    EditText txtRegistrationNumber;
    EditText txtReturningDate;
    EditText txtServicesDate;
    EditText txtVinNumber;
    EditText txtYear;

    class C02411 implements OnClickListener {
        C02411() {
        }

        public void onClick(View v) {
            Vehicle_Details.this.saveToDatabase();
        }
    }

    class C02422 implements TextWatcher {
        C02422() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Vehicle_Details.this.readVehicleDetails(Vehicle_Details.this.txtRegistrationNumber.getText().toString());
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_vehicle__details);
        Intent intent = getIntent();
        intPkey = new Integer(Integer.parseInt(intent.getStringExtra(Client_Details.pKey))).intValue();
        pKey = intent.getStringExtra(Client_Details.pKey);
        ServiceArray = new ArrayList(intent.getStringArrayListExtra(""));
        this.txtMake = (EditText) findViewById(R.id.txtVehicleMake);
        this.txtModel = (EditText) findViewById(R.id.txtVehicleModel);
        this.txtYear = (EditText) findViewById(R.id.txtVehicleYear);
        this.txtMileage = (EditText) findViewById(R.id.txtVehicleMileage);
        this.txtVinNumber = (EditText) findViewById(R.id.txtVehicleVinNumber);
        this.txtRegistrationNumber = (EditText) findViewById(R.id.txtRegistration_Number);
        this.txtDateBookedIn = (EditText) findViewById(R.id.txtVehiclDateBookedIn);
        this.txtServicesDate = (EditText) findViewById(R.id.txtVehicleServiceDate);
        this.txtReturningDate = (EditText) findViewById(R.id.txtVehicleReturningDate);
        this.btnSave = (Button) findViewById(R.id.btnNextToImages);
        this.txtRegistrationNumber = (EditText) findViewById(R.id.txtRegistration_Number);
        if (Status.getStatus().toString() != "Add_Vehicle") {
            Cursor c = this.newDb.forYourVehicle(DBHelper.TABLE_NAMEOV, intent.getStringExtra(Client_Details.pKey), ServiceArray, "record");
            while (c.moveToNext()) {
                this.txtMake.setText(c.getString(0));
                this.txtModel.setText(c.getString(1));
                this.txtYear.setText(c.getString(2));
                this.txtMileage.setText(c.getString(3));
                this.txtVinNumber.setText(c.getString(4));
                this.txtDateBookedIn.setText(c.getString(5));
                this.txtServicesDate.setText(c.getString(6));
                this.txtReturningDate.setText(c.getString(7));
                this.txtRegistrationNumber.setText(c.getString(8));
            }
        } else if (Status.getStatus().toString() == "Add_Vehicle") {
            intPkey = 0;
        }
        this.btnSave.setOnClickListener(new C02411());
        this.txtRegistrationNumber.addTextChangedListener(new C02422());
    }

    public void saveToDatabase() {
        ServiceArray.add(6, this.txtMake.getText().toString());
        ServiceArray.add(7, this.txtModel.getText().toString());
        ServiceArray.add(8, this.txtYear.getText().toString());
        ServiceArray.add(9, this.txtMileage.getText().toString());
        ServiceArray.add(10, this.txtVinNumber.getText().toString());
        ServiceArray.add(11, this.txtDateBookedIn.getText().toString());
        ServiceArray.add(12, this.txtServicesDate.getText().toString());
        ServiceArray.add(13, this.txtReturningDate.getText().toString());
        ServiceArray.add(14, this.txtRegistrationNumber.getText().toString());
        Intent intent = new Intent(this, Front_Images.class);
        if (intPkey <= 0) {
            intent.putStringArrayListExtra("", ServiceArray);
            intent.putExtra(pKey, "0");
            startActivity(intent);
        } else if (Boolean.valueOf(this.newDb.updateVehicle(Integer.valueOf(intPkey), ServiceArray)).booleanValue()) {
            Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_SHORT).show();
            intent.putStringArrayListExtra("", ServiceArray);
            intent.putExtra(pKey, pKey);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Update Failed  ", Toast.LENGTH_SHORT).show();
        }
    }

    public void readVehicleDetails(String registrationNumber) {
        try {
            Cursor c = this.newDb.forYourVehicle(DBHelper.TABLE_NAMEOV, registrationNumber, ServiceArray, "Searching");
            while (c.moveToNext()) {
                this.txtMake.setText(c.getString(0));
                this.txtModel.setText(c.getString(1));
                this.txtYear.setText(c.getString(2));
                this.txtMileage.setText(c.getString(3));
                this.txtVinNumber.setText(c.getString(4));
            }
            if (c.getCount() == 0) {
                this.txtMake.setText("");
                this.txtModel.setText("");
                this.txtYear.setText("");
                this.txtMileage.setText("");
                this.txtVinNumber.setText("");
            }
            this.newDb.close();
        } catch (Exception e) {
        }
    }
}
