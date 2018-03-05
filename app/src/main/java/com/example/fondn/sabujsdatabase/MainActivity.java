package com.example.fondn.sabujsdatabase;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyDataBaseHelper myDataBaseHelper;
    EditText nameEdit, ageEdit, genderEdit,idEdit;
    Button submitButton, showButton,updateButton,deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = (EditText) findViewById(R.id.nameeditTextID);
        ageEdit = (EditText) findViewById(R.id.ageID);
        genderEdit = (EditText) findViewById(R.id.genderTextID);
        idEdit = (EditText) findViewById(R.id.idTextID);
        submitButton = (Button) findViewById(R.id.submitID);
        showButton = (Button) findViewById(R.id.retruveButtonID);
        updateButton = (Button) findViewById(R.id.updateButtonID);
        deleteButton = (Button) findViewById(R.id.deleteButtonID);

        submitButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase database = myDataBaseHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        String name = nameEdit.getText().toString();
        String age = ageEdit.getText().toString();
        String gender = genderEdit.getText().toString();
        String id = idEdit.getText().toString();

        if (v.getId() == R.id.submitID) {
            long r = myDataBaseHelper.insertData(name, age, gender);
            if (r == -1) {
                Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Successfully Inserted . row id: " + r, Toast.LENGTH_SHORT).show();
            }

        }
        if (v.getId() == R.id.retruveButtonID) {
            Cursor cursor = myDataBaseHelper.showDataFromDatabase();
            if (cursor.getCount() == 0) {
                showData("Error", "NO Data Found");
                return;
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    stringBuffer.append("id :" + cursor.getString(0) + " \n ");
                    stringBuffer.append("Name :" + cursor.getString(1) + " \n ");
                    stringBuffer.append("Age :" + cursor.getString(2) + " \n ");
                    stringBuffer.append("Gender :" + cursor.getString(3) + " \n \n\n");
                }
                showData("Resultset", stringBuffer.toString());
            }

        }



        if(v.getId()==R.id.updateButtonID){
            Boolean b = myDataBaseHelper.updateData(id,name,age,gender);
            if(b==true){
                Toast.makeText(this, "Successfully Updatead", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Sorry not Update", Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId()==R.id.deleteButtonID){
            int value =myDataBaseHelper.deleteData(id);
            if(value>0){
                Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Faild to Delete", Toast.LENGTH_SHORT).show();
            }
        }

    }


    void showData(String title, String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();
    }
}
