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

public class Back_Panel extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static ArrayList<String> ServiceArray = new ArrayList();
    public static final String ServiceData = "";
    public static int intPkey = 0;
    public static String pKey = "";
    private final int SELECT_PHOTO = 1;
    Button btnToLeftPanel;
    private String defaultImage = "@android:drawable/ic_menu_camera";
    private ImageView imageView;
    final DBHelper newDb = new DBHelper(this);
    EditText txtDescription;

    class C02211 implements OnClickListener {
        C02211() {
        }

        public void onClick(View view) {
            Back_Panel.this.dispatchTakePictureIntent();
        }
    }

    class C02222 implements OnClickListener {
        C02222() {
        }

        public void onClick(View v) {
            Back_Panel.this.saveToDatabase();
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
        setContentView((int) R.layout.activity_back__panel);
        Intent intent = getIntent();
        intPkey = new Integer(Integer.parseInt(intent.getStringExtra(Right_Panel.pKey))).intValue();
        pKey = intent.getStringExtra(Right_Panel.pKey);
        ServiceArray = new ArrayList(intent.getStringArrayListExtra(""));
        this.imageView = (ImageView) findViewById(R.id.BackPanelImage);
        this.txtDescription = (EditText) findViewById(R.id.txtBackPanelDescription);
        this.btnToLeftPanel = (Button) findViewById(R.id.btnToLeftPanel);
        this.imageView.setOnClickListener(new C02211());
        if (Status.getStatus().toString() != "Add_Vehicle") {
            Cursor c = this.newDb.forYourVehicle(DBHelper.TABLE_NAMEOV, intent.getStringExtra(Client_Details.pKey), ServiceArray, "record");
            while (c.moveToNext()) {
                byte[] image = c.getBlob(13);
                this.txtDescription.setText(c.getString(14));
                Bitmap bmimage = BitmapFactory.decodeByteArray(image, 0, image.length);
                this.imageView.setImageBitmap(bmimage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmimage.compress(CompressFormat.JPEG, 100, baos);
                Image_Path.setBack_Panel_Path(Base64.encodeToString(baos.toByteArray(), 0));
                Image_Path.setBack_Panel_Image(Base64.decode(Image_Path.getBack_Panel_Path(), 0));
            }
        }
        this.btnToLeftPanel.setOnClickListener(new C02222());
        if (intPkey > 0) {
            this.btnToLeftPanel.setEnabled(true);
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
                        Image_Path.setBack_Panel_Path(Base64.encodeToString(baos.toByteArray(), 0));
                        byte[] bytarray = Base64.decode(Image_Path.getBack_Panel_Path(), 0);
                        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0, bytarray.length);
                        Image_Path.setBack_Panel_Image(bytarray);
                        this.btnToLeftPanel.setEnabled(true);
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
        Image_Path.setBack_Panel_Description(this.txtDescription.getText().toString());
        Intent intent = new Intent(this, Left_Panel.class);
        intent.putStringArrayListExtra("", ServiceArray);
        intent.putExtra(pKey, pKey);
        startActivity(intent);
        if (intPkey <= 0) {
            return;
        }
        if (Boolean.valueOf(this.newDb.updateBackPanel(Integer.valueOf(intPkey))).booleanValue()) {
            Toast.makeText(getApplicationContext(), "Saved ", Toast.LENGTH_SHORT).show();
            intent.putStringArrayListExtra("", ServiceArray);
            intent.putExtra(pKey, pKey);
            startActivity(intent);
            return;
        }
        Toast.makeText(getApplicationContext(), "Update Failed  ", Toast.LENGTH_SHORT).show();
    }
}
