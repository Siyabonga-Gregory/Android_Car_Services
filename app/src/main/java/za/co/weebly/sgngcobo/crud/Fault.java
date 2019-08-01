package za.co.weebly.sgngcobo.crud;
/*
 * copyright @2017 By   : Siyabonga Gregory
 * Developer's Website  : www.sgngcobo.weebly.com
 * Developer's Number   : 084-492-1275
 * Developer's E-mail   : huge.fuze@gmail.com
 * */

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Fault extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static ArrayList<String> ServiceArray = new ArrayList();
    public static final String ServiceData = "";
    public static int intPkey = 0;
    public static String pKey = "";
    private final int SELECT_PHOTO = 1;
    Button btnFaultComplete;
    private String defaultImage = "@android:drawable/ic_menu_camera";
    private ImageView imageView;
    Intent intent = null;
    final DBHelper newDb = new DBHelper(this);
    EditText txtDescription;
    EditText txtMake;
    EditText txtMileage;
    EditText txtModel;
    EditText txtRegistrationNumber;
    EditText txtVinNumber;
    EditText txtYear;

    class C02251 implements OnClickListener {
        C02251() {
        }

        public void onClick(View view) {
            Fault.this.dispatchTakePictureIntent();
        }
    }

    class C02262 implements TextWatcher {
        C02262() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Fault.this.readVehicleDetails(Fault.this.txtRegistrationNumber.getText().toString());
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    class C02273 implements DialogInterface.OnClickListener {
        C02273() {
        }

        public void onClick(DialogInterface arg0, int arg1) {
            Fault.this.dispatchTakePictureIntent();
        }
    }

    class C02284 implements DialogInterface.OnClickListener {
        C02284() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_fault);
        this.imageView = (ImageView) findViewById(R.id.faultPanelImage);
        this.txtMake = (EditText) findViewById(R.id.txtFaultMake);
        this.txtModel = (EditText) findViewById(R.id.txtFaultModel);
        this.txtYear = (EditText) findViewById(R.id.txtFaultYear);
        this.txtMileage = (EditText) findViewById(R.id.txtFaultMileage);
        this.txtVinNumber = (EditText) findViewById(R.id.txtfaultVinNumber);
        this.txtRegistrationNumber = (EditText) findViewById(R.id.txtFaultRegistrationNumber);
        this.imageView.setOnClickListener(new C02251());
        this.txtRegistrationNumber.addTextChangedListener(new C02262());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    try {
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                        this.imageView.setImageBitmap(imageBitmap);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imageBitmap.compress(CompressFormat.JPEG, 100, baos);
                        Image_Path.setFault_Panel_Path(Base64.encodeToString(baos.toByteArray(), 0));
                        byte[] bytarray = Base64.decode(Image_Path.getFault_Panel_Path(), 0);
                        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0, bytarray.length);
                        Image_Path.setFault_Panel_Image(bytarray);
                        Image_Path.setRegistration_Number(this.txtRegistrationNumber.getText().toString());
                        confirmMessage();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recording_fault, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewImages:
                Intent faultImagesIntent = new Intent(this, fault_images.class);
                faultImagesIntent.putExtra(pKey, this.txtRegistrationNumber.getText());
                startActivity(faultImagesIntent);
                return true;
            case R.id.mainMenu:
                Intent mainMenuIntent = new Intent(this, MainActivity.class);
                mainMenuIntent.putExtra(pKey, this.txtRegistrationNumber.getText());
                startActivity(mainMenuIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void confirmMessage() {
        if (Boolean.valueOf(this.newDb.Save_Fault()).booleanValue()) {
            Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failed  ", Toast.LENGTH_LONG).show();
        }
        Builder alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setTitle((CharSequence) "Would you like to add more images ?");
        alertDialogBuilder.setNegativeButton((CharSequence) "YES", new C02273());
        alertDialogBuilder.setPositiveButton((CharSequence) "NO", new C02284());
        alertDialogBuilder.create().show();
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
                Image_Path.setRegistration_Number(this.txtRegistrationNumber.getText().toString());
            }
            if (c.getCount() == 0) {
                this.txtMake.setText("");
                this.txtModel.setText("");
                this.txtYear.setText("");
                this.txtMileage.setText("");
                this.txtVinNumber.setText("");
                Image_Path.setRegistration_Number("9080");
            }
            this.newDb.close();
        } catch (Exception e) {
        }
    }
}
