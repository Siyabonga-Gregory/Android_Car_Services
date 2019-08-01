package za.co.weebly.sgngcobo.crud;
/*
 * copyright @2017 By   : Siyabonga Gregory
 * Developer's Website  : www.sgngcobo.weebly.com
 * Developer's Number   : 084-492-1275
 * Developer's E-mail   : huge.fuze@gmail.com
 * */
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Left_Panel extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static ArrayList<String> ServiceArray = new ArrayList();
    public static final String ServiceData = "";
    public static int intPkey = 0;
    public static String pKey = "";
    private final int SELECT_PHOTO = 1;
    Button btnComplete;
    private String defaultImage = "@android:drawable/ic_menu_camera";
    private ImageView imageView;
    final DBHelper newDb = new DBHelper(this);
    EditText txtDescription;

    class C02311 implements OnClickListener {
        C02311() {
        }

        public void onClick(View view) {
            Left_Panel.this.dispatchTakePictureIntent();
        }
    }

    class C02322 implements OnClickListener {
        C02322() {
        }

        public void onClick(View v) {
            Left_Panel.this.saveToDatabase();
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
        setContentView((int) R.layout.activity_left__panel);
        Intent intent = getIntent();
        intPkey = new Integer(Integer.parseInt(intent.getStringExtra(Back_Panel.pKey))).intValue();
        pKey = intent.getStringExtra(Back_Panel.pKey);
        ServiceArray = new ArrayList(intent.getStringArrayListExtra(""));
        this.imageView = (ImageView) findViewById(R.id.leftPanelImage);
        this.txtDescription = (EditText) findViewById(R.id.txtLeftPanelDescription);
        this.btnComplete = (Button) findViewById(R.id.btnComplete);
        this.imageView.setOnClickListener(new C02311());
        if (Status.getStatus().toString() != "Add_Vehicle") {
            Cursor c = this.newDb.forYourVehicle(DBHelper.TABLE_NAMEOV, intent.getStringExtra(Client_Details.pKey), ServiceArray, "record");
            while (c.moveToNext()) {
                byte[] image = c.getBlob(15);
                this.txtDescription.setText(c.getString(16));
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                this.imageView.setImageBitmap(imageBitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(CompressFormat.JPEG, 100, baos);
                Image_Path.setLeft_Panel_Path(Base64.encodeToString(baos.toByteArray(), 0));
                byte[] bytarray = Base64.decode(Image_Path.getLeft_Panel_Path(), 0);
                Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0, bytarray.length);
                Image_Path.setLeft_Panel_Image(bytarray);
            }
        }
        this.btnComplete.setOnClickListener(new C02322());
        if (intPkey > 0) {
            this.btnComplete.setEnabled(true);
        }
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
                        Image_Path.setLeft_Panel_Path(Base64.encodeToString(baos.toByteArray(), 0));
                        byte[] bytarray = Base64.decode(Image_Path.getLeft_Panel_Path(), 0);
                        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0, bytarray.length);
                        Image_Path.setLeft_Panel_Image(bytarray);
                        this.btnComplete.setEnabled(true);
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
        return true;
    }

    public void saveToDatabase() {
        Image_Path.setLeft_Panel_Description(this.txtDescription.getText().toString());
        Intent intent = new Intent(this, Details.class);
        if (intPkey <= 0) {
            boolean clientResult = this.newDb.SaveClient(((String) ServiceArray.get(0)).toString(), ((String) ServiceArray.get(1)).toString(), ((String) ServiceArray.get(2)).toString(), ((String) ServiceArray.get(3)).toString(), ((String) ServiceArray.get(4)).toString(), ((String) ServiceArray.get(5)).toString());
            boolean vehicleResult = this.newDb.SaveVehicle(((String) ServiceArray.get(6)).toString(), ((String) ServiceArray.get(7)).toString(), ((String) ServiceArray.get(8)).toString(), ((String) ServiceArray.get(9)).toString(), ((String) ServiceArray.get(10)).toString(), ((String) ServiceArray.get(11)).toString(), ((String) ServiceArray.get(12)).toString(), ((String) ServiceArray.get(13)).toString(), ((String) ServiceArray.get(14)).toString());
            if (clientResult && vehicleResult) {
                Toast.makeText(getApplicationContext(), "Record Successfully Created", Toast.LENGTH_SHORT).show();
                intent.putStringArrayListExtra("", ServiceArray);
                intent.putExtra(pKey, pKey);
                startActivity(intent);
                return;
            }
            Toast.makeText(getApplicationContext(), "Transaction Failed", Toast.LENGTH_SHORT).show();
        } else if (Boolean.valueOf(this.newDb.updateLeftPanel(Integer.valueOf(intPkey))).booleanValue()) {
            Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_SHORT).show();
            intent.putStringArrayListExtra("", ServiceArray);
            intent.putExtra(pKey, pKey);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Update Failed  ", Toast.LENGTH_SHORT).show();
        }
    }
}
