package za.co.weebly.sgngcobo.crud;
/*
 * copyright @2017 By   : Siyabonga Gregory
 * Developer's Website  : www.sgngcobo.weebly.com
 * Developer's Number   : 084-492-1275
 * Developer's E-mail   : huge.fuze@gmail.com
 * */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper 
{
    private static final String Back_Panel_Description = "Back_Panel_Description";
    private static final String Back_Panel_Image = "Back_Panel_Image";
    public static final String CREATE_TABLE = "CREATE TABLE Clients(Record_Number INTEGER PRIMARY KEY AUTOINCREMENT, Client_Name TEXT, Client_Surname TEXT, Email TEXT, Work_Tel_No TEXT,Home_Tel_No TEXT, Cell_Phone_Number TEXT);";
    public static final String CREATE_TABLE1 = "CREATE TABLE Vehicle(Record_Number INTEGER PRIMARY KEY AUTOINCREMENT, Make TEXT, Model TEXT, Year TEXT, Mileage TEXT, Vin_Number TEXT,Date_Booked_In TEXT, Service_Date TEXT, Returning_Date TEXT,Registration_Number TEXT,Front_Panel_Image BLOB,Front_Panel_Description TEXT,Right_Panel_Image BLOB , Right_Panel_Description TEXT,Back_Panel_Image BLOB,Back_Panel_Description TEXT,Left_Panel_Image BLOB,Left_Panel_Description TEXT);";
    public static final String CREATE_TABLE_FAULT = "CREATE TABLE Fault(Record_Number INTEGER PRIMARY KEY AUTOINCREMENT,Registration_Number TEXT, Fault_Image BLOB);";
    public static final String Cell_Phone_Number = "Cell_Phone_Number";
    public static final String Client_Name = "Client_Name";
    public static final String Client_Surname = "Client_Surname";
    public static final String DATABASE_NAME = "sgngcobo.db";
    public static final int DATABASE_VERSION = 3;
    public static final String Date_Booked_In = "Date_Booked_In";
    public static final String Email = "Email";
    public static final String Fault_Image = "Fault_Image";
    private static final String Front_Panel_Description = "front_Panel_Description";
    private static final String Front_Panel_Image = "Front_Panel_Image";
    public static final String Home_Tel_No = "Home_Tel_No";
    private static final String LOG = "DBHelper";
    private static final String Left_Panel_Description = "Left_Panel_Description";
    private static final String Left_Panel_Image = "Left_Panel_Image";
    public static final String Make = "Make";
    public static final String Mileage = "Mileage";
    public static final String Model = "Model";
    public static final String Record_Number_1 = "Record_Number";
    public static final String Record_Number_2 = "Record_Number";
    public static final String Registration_Number = "Registration_Number";
    public static final String Returning_Date = "Returning_Date";
    private static final String Right_Panel_Description = "Right_Panel_Description";
    private static final String Right_Panel_Image = "Right_Panel_Image";
    public static final String Service_Date = "Service_Date";
    public static final String TABLE_NAME = "Clients";
    public static final String TABLE_NAMEOV = "Vehicle";
    public static final String TABLE_NAME_FAULT = "Fault";
    public static final String Vin_Number = "Vin_Number";
    public static final String Work_Tel_No = "Work_Tel_No";
    public static final String Year = "Year";
    SQLiteDatabase db;
    DBHelper helper;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE1);
        sqLiteDatabase.execSQL(CREATE_TABLE_FAULT);
        Log.w("Tables ", " Created");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Clients");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Vehicle");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Fault");
        onCreate(sqLiteDatabase);
    }

    public boolean SaveClient(String C_Name, String C_Surname, String C_Email, String H_Tel_No, String W_Tel_No, String C_Phone_No) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Client_Name, C_Name);
        contentValues.put(Client_Surname, C_Surname);
        contentValues.put(Email, C_Email);
        contentValues.put(Work_Tel_No, W_Tel_No);
        contentValues.put(Home_Tel_No, H_Tel_No);
        contentValues.put(Cell_Phone_Number, C_Phone_No);
        if (sqLiteDatabase.insert(TABLE_NAME, null, contentValues) > 0) {
            return true;
        }
        return false;
    }

    public boolean SaveVehicle(String V_Make, String V_Model, String V_Year, String V_Mileage, String V_Vin_Number, String V_Date_Booked_In, String V_Service_Date, String V_Returning_Date, String V_Registration_Number) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Make, V_Make);
        contentValues.put(Model, V_Model);
        contentValues.put(Year, V_Year);
        contentValues.put(Mileage, V_Mileage);
        contentValues.put(Vin_Number, V_Vin_Number);
        contentValues.put(Date_Booked_In, V_Date_Booked_In);
        contentValues.put(Service_Date, V_Service_Date);
        contentValues.put(Returning_Date, V_Returning_Date);
        contentValues.put(Registration_Number, V_Registration_Number);
        contentValues.put(Front_Panel_Image, Image_Path.getFront_Panel_Image());
        contentValues.put(Front_Panel_Description, Image_Path.getFront_Panel_Description());
        contentValues.put(Right_Panel_Image, Image_Path.getRight_Panel_Image());
        contentValues.put(Right_Panel_Description, Image_Path.getRight_Panel_Description());
        contentValues.put(Back_Panel_Image, Image_Path.getBack_Panel_Image());
        contentValues.put(Back_Panel_Description, Image_Path.getBack_Panel_Description());
        contentValues.put(Left_Panel_Image, Image_Path.getLeft_Panel_Image());
        contentValues.put(Left_Panel_Description, Image_Path.getLeft_Panel_Description());
        if (sqLiteDatabase.insert(TABLE_NAMEOV, null, contentValues) > 0) {
            return true;
        }
        return false;
    }

    public boolean Save_Fault() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Registration_Number, Image_Path.getRegistration_Number());
        contentValues.put(Fault_Image, Image_Path.getFault_Panel_Image());
        if (sqLiteDatabase.insert(TABLE_NAME_FAULT, null, contentValues) > 0) {
            return true;
        }
        return false;
    }

    public Cursor getClientDetails() {
        return getWritableDatabase().rawQuery("SELECT * FROM Clients", null);
    }

    public Cursor getVehicleDetails() {
        return getWritableDatabase().rawQuery("SELECT * FROM Vehicle", null);
    }

    public DBHelper openDB() {
        try {
            this.db = this.helper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public DBHelper closeDB() {
        this.helper.close();
        return this;
    }

    public boolean updateClientDetails(Integer pkey, ArrayList mainList) {
        try {
            getWritableDatabase().execSQL("UPDATE Clients SET Client_Name ='" + mainList.get(0) + "',Client_Surname ='" + mainList.get(1) + "',Email ='" + mainList.get(2) + "',Work_Tel_No ='" + mainList.get(3) + "',Home_Tel_No ='" + mainList.get(4) + "',Cell_Phone_Number ='" + mainList.get(5) + "' WHERE Record_Number=" + pkey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateVehicle(Integer pkey, ArrayList mainList) {
        try {
            getWritableDatabase().execSQL("UPDATE Vehicle SET Make ='" + mainList.get(6) + "',Model ='" + mainList.get(7) + "',Year ='" + mainList.get(8) + "',Mileage ='" + mainList.get(9) + "',Vin_Number ='" + mainList.get(10) + "',Date_Booked_In ='" + mainList.get(11) + "',Service_Date ='" + mainList.get(12) + "',Returning_Date ='" + mainList.get(13) + "',Registration_Number ='" + mainList.get(14) + "' WHERE Record_Number=" + pkey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateFrontPanel(Integer pkey) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Front_Panel_Image, Image_Path.getFront_Panel_Image());
            contentValues.put(Front_Panel_Description, Image_Path.getFront_Panel_Description());
            sqLiteDatabase.update(TABLE_NAMEOV, contentValues, "Record_Number='" + pkey + "'", null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateRightPanel(Integer pkey) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Right_Panel_Image, Image_Path.getRight_Panel_Image());
            contentValues.put(Right_Panel_Description, Image_Path.getRight_Panel_Description());
            sqLiteDatabase.update(TABLE_NAMEOV, contentValues, "Record_Number='" + pkey + "'", null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateBackPanel(Integer pkey) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Back_Panel_Image, Image_Path.getBack_Panel_Image());
            contentValues.put(Back_Panel_Description, Image_Path.getBack_Panel_Description());
            sqLiteDatabase.update(TABLE_NAMEOV, contentValues, "Record_Number='" + pkey + "'", null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateLeftPanel(Integer pkey) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Left_Panel_Image, Image_Path.getLeft_Panel_Image());
            contentValues.put(Left_Panel_Description, Image_Path.getLeft_Panel_Description());
            sqLiteDatabase.update(TABLE_NAMEOV, contentValues, "Record_Number='" + pkey + "'", null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteRecord(Integer pkey) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("Delete From Clients WHERE Record_Number=" + pkey);
            sqLiteDatabase.execSQL("Delete From Vehicle WHERE Record_Number=" + pkey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor forYourVehicle(String tableName, String pkey, ArrayList mainList, String action) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] yourVehicleColumns = new String[]{"Record_Number", Client_Name, Client_Surname, Email, Work_Tel_No, Home_Tel_No, Cell_Phone_Number};
        String[] otherVehicleColumns = new String[]{Make, Model, Year, Mileage, Vin_Number, Date_Booked_In, Service_Date, Returning_Date, Registration_Number, Front_Panel_Image, "Front_Panel_Description", Right_Panel_Image, Right_Panel_Description, Back_Panel_Image, Back_Panel_Description, Left_Panel_Image, Left_Panel_Description};
        String[] faultColumns = new String[]{"Record_Number", Registration_Number, Fault_Image};
        StringBuilder stringBuilder;
        DBHelper dBHelper;
        if (tableName == TABLE_NAME) {
            stringBuilder = new StringBuilder();
            dBHelper = this.helper;
            return sqLiteDatabase.query(tableName, yourVehicleColumns, stringBuilder.append("Record_Number").append(" = ").append(pkey).toString(), null, null, null, null);
        } else if (tableName == TABLE_NAMEOV && action.toString() != "Searching") {
            stringBuilder = new StringBuilder();
            dBHelper = this.helper;
            return sqLiteDatabase.query(tableName, otherVehicleColumns, stringBuilder.append("Record_Number").append(" = ").append(pkey).toString(), null, null, null, null);
        } else if (tableName == TABLE_NAMEOV && action.toString() == "Searching") {
            stringBuilder = new StringBuilder();
            dBHelper = this.helper;
            return sqLiteDatabase.query(tableName, otherVehicleColumns, stringBuilder.append(Registration_Number).append(" = ").append(pkey).toString(), null, null, null, null);
        } else if (tableName != TABLE_NAME_FAULT) {
            return null;
        } else {
            if (Image_Path.getRegistration_Number().toString() == "") {
                return null;
            }
            stringBuilder = new StringBuilder();
            dBHelper = this.helper;
            return sqLiteDatabase.query(tableName, faultColumns, stringBuilder.append(Registration_Number).append(" = ").append(Image_Path.getRegistration_Number()).toString(), null, null, null, null);
        }
    }

    public Cursor getClientDetailsByReference(String referenceNumber) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] yourVehicleColumns = new String[]{Client_Name, Client_Surname, Email, Work_Tel_No, Home_Tel_No, Cell_Phone_Number};
        String str = TABLE_NAME;
        StringBuilder stringBuilder = new StringBuilder();
        DBHelper dBHelper = this.helper;
        return sqLiteDatabase.query(str, yourVehicleColumns, stringBuilder.append("Record_Number").append(" =").append(referenceNumber).toString(), null, null, null, null);
    }
}
