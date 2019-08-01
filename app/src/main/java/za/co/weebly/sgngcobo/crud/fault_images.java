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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class fault_images extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static ArrayList<String> ServiceArray = new ArrayList();
    private final int SELECT_PHOTO = 1;
    public Cursor f11c;
    public Integer count = Integer.valueOf(1);
    private String defaultImage = "@android:drawable/ic_menu_camera";
    private ImageView imageView_1;
    final DBHelper newDb = new DBHelper(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_fault_images);
        this.f11c = this.newDb.forYourVehicle(DBHelper.TABLE_NAME_FAULT, "", ServiceArray, "record");
        this.imageView_1 = (ImageView) findViewById(R.id.image_1);
        Toast.makeText(getApplicationContext(), this.f11c.getCount() + "\n\n Image(s) ", Toast.LENGTH_LONG).show();
        try {
            this.f11c.moveToNext();
            byte[] image = this.f11c.getBlob(2);
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            if (this.f11c.getCount() > 0) {
                this.imageView_1.setImageBitmap(imageBitmap);
            }
        } catch (Exception aa) {
            Toast.makeText(getApplicationContext(), "ERROR  \n\n" + aa.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fault_images, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nextImage:
                nextImage();
                return true;
            case R.id.previousImage:
                previousImage();
                return true;
            case R.id.back:
                startActivity(new Intent(this, Fault.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void nextImage() {
        Integer num = this.count;
        this.count = Integer.valueOf(this.count.intValue() + 1);
        this.imageView_1 = (ImageView) findViewById(R.id.image_1);
        try {
            if (this.f11c.moveToNext()) {
                this.f11c.moveToNext();
                byte[] image = this.f11c.getBlob(2);
                this.imageView_1.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
                Toast.makeText(getApplicationContext(), "Image  Number : \n\n" + this.count.toString(), Toast.LENGTH_LONG).show();
            } else if (!this.f11c.moveToNext()) {
                Toast.makeText(getApplicationContext(), "NO MORE IMAGES", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        }
    }

    public void previousImage() {
        Integer num = this.count;
        this.count = Integer.valueOf(this.count.intValue() - 1);
        this.imageView_1 = (ImageView) findViewById(R.id.image_1);
        try {
            if (this.f11c.moveToPrevious()) {
                this.f11c.moveToPrevious();
                byte[] image = this.f11c.getBlob(2);
                this.imageView_1.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
                Toast.makeText(getApplicationContext(), "Image  Number : \n\n" + this.count.toString(), Toast.LENGTH_LONG).show();
            } else if (!this.f11c.moveToPrevious()) {
                Toast.makeText(getApplicationContext(), "NO MORE IMAGES", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }
}
