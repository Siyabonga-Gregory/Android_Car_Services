package za.co.weebly.sgngcobo.crud;
/*
 * copyright @2017 By   : Siyabonga Gregory
 * Developer's Website  : www.sgngcobo.weebly.com
 * Developer's Number   : 084-492-1275
 * Developer's E-mail   : huge.fuze@gmail.com
 * */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class Details extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    public static ArrayList<String> ServiceArray = new ArrayList();
    public static final String ServiceData = "";
    public static int intPkey = 0;
    public static String pKey = "";
    Button cancelBtn;
    String message;
    final DBHelper newDb = new DBHelper(this);
    String phoneNo;
    Button sendBtn;
    EditText txtMessage;
    EditText txtPhoneNo;

    class C02231 implements OnClickListener {
        C02231() {
        }

        public void onClick(View view) {
            Details.this.sendSMSMessage();
        }
    }

    class C02242 implements OnClickListener {
        C02242() {
        }

        public void onClick(View view) {
            Details.this.backToMainMenu();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_details);
        Intent intent = getIntent();
        intPkey = new Integer(Integer.parseInt(intent.getStringExtra(Left_Panel.pKey))).intValue();
        pKey = intent.getStringExtra(Left_Panel.pKey);
        ServiceArray = new ArrayList(intent.getStringArrayListExtra(""));
        this.sendBtn = (Button) findViewById(R.id.btnSendMessage);
        this.cancelBtn = (Button) findViewById(R.id.btnCancel);
        this.txtPhoneNo = (EditText) findViewById(R.id.txtFaultVinNumber);
        this.txtMessage = (EditText) findViewById(R.id.txtMessage);
        this.txtPhoneNo.setText(((String) ServiceArray.get(5)).toString());
        this.sendBtn.setOnClickListener(new C02231());
        this.cancelBtn.setOnClickListener(new C02242());
        if (intPkey == 0) {
            Cursor clientDetails = this.newDb.getClientDetails();
            Cursor vehicleDetails = this.newDb.getVehicleDetails();
            clientDetails.moveToLast();
            vehicleDetails.moveToLast();
            this.txtMessage.setText("Dear : " + clientDetails.getString(1) + "  " + clientDetails.getString(2) + "\n\n" + "Your vehicle booking details are as follows  . \n\n" + "Reference  Number  : " + vehicleDetails.getInt(0) + "\n" + "Vin Number                : " + vehicleDetails.getString(5) + "\n" + "Date Booked In          : " + vehicleDetails.getString(6) + "\n" + "Service Date               : " + vehicleDetails.getString(7) + "\n" + "Returning Date          : " + vehicleDetails.getString(8) + "\n\n" + "Thank you for using our service.");
        } else if (intPkey > 0) {
            this.txtMessage.setText("Dear : " + ((String) ServiceArray.get(0)) + "  " + ((String) ServiceArray.get(1)) + "\n\n" + "Your vehicle booking details are as follows. \n\n" + "Reference  Number  : " + intPkey + "\n" + "Vin Number                : " + ((String) ServiceArray.get(10)) + "\n" + "Date Booked In          : " + ((String) ServiceArray.get(11)) + "\n" + "Service Date               : " + ((String) ServiceArray.get(12)) + "\n" + "Returning Date          : " + ((String) ServiceArray.get(13)) + "\n\n" + "Thank you for using our service.");
        }
        this.newDb.close();
    }

    protected void sendSMSMessage() {
        this.phoneNo = this.txtPhoneNo.getText().toString();
        this.message = this.txtMessage.getText().toString();
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.SEND_SMS"}, 0);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendMultipartTextMessage(this.phoneNo, null, smsManager.divideMessage(this.message), null, null);
                Toast.makeText(getApplicationContext(), "SMS has been sent.", Toast.LENGTH_LONG).show();
                backToMainMenu();
                return;
            default:
                return;
        }
    }

    protected void sendSMS() {
        this.phoneNo = this.txtPhoneNo.getText().toString();
        this.message = this.txtMessage.getText().toString();
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent("android.intent.action.VIEW");
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String(this.phoneNo));
        smsIntent.putExtra("sms_body", this.message);
        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendEmail() {
        Intent emailing = new Intent("android.intent.action.SEND");
        this.message = this.txtMessage.getText().toString();
        emailing.setData(Uri.parse("mailto:"));
        emailing.setType("text/plain");
        emailing.putExtra("android.intent.extra.EMAIL", (String) ServiceArray.get(2));
        emailing.putExtra("android.intent.extra.CC", "huge.fuze@gmail.com");
        emailing.putExtra("android.intent.extra.SUBJECT", "Vehicle Book in Details");
        emailing.putExtra("android.intent.extra.TEXT", this.message);
        try {
            startActivity(Intent.createChooser(emailing, "Sending emailing"));
            finish();
            Toast.makeText(this, "EMAIL HAS BEEN SENT", Toast.LENGTH_LONG).show();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "EMAIL failed, please try again later.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    public void backToMainMenu() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
