package com.example.basicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txtWelcome;
    EditText edtName, edtAge, edtWeight, edtHeight;
    RadioGroup radGen;
    RadioButton radMale, radFemale;
    Button btnDone;
    ListView lvUser;


    ArrayAdapter userArrayAdapter;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtWelcome = findViewById(R.id.txtWelcome);

        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);

        radGen = findViewById(R.id.radGen);
        radMale = findViewById(R.id.radMale);
        radFemale = findViewById(R.id.radFemale);

        btnDone = findViewById(R.id.btnDone);

        lvUser = findViewById(R.id.lvUser);

        dataBase = new DataBase(MainActivity.this);

        ShowUser();


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel;
                try {
                    userModel = new UserModel(0, edtName.getText().toString(), Integer.parseInt(edtAge.getText().toString()), radMale.isChecked(), radFemale.isChecked(), Float.parseFloat(edtWeight.getText().toString()), Float.parseFloat(edtHeight.getText().toString()));
                    Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel(-1, "error", 0, false, false, 0, 0);
                }

                DataBase dataBase = new DataBase(MainActivity.this);

                boolean success = dataBase.addOne(userModel);

                Toast.makeText(MainActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
                ShowUser();
            }
        });


        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                UserModel clickedUser = (UserModel) parent.getItemAtPosition(i);
                dataBase.deleteOne(clickedUser);
                ShowUser();
                Toast.makeText(MainActivity.this, "Deleted " + clickedUser.toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                UserModel clickedUser = (UserModel) parent.getItemAtPosition(i);

                String name = clickedUser.getName();
                int age = clickedUser.getAge();
                boolean isMale = clickedUser.isMale();
                boolean isFemale = clickedUser.isFemale();
                float weight = clickedUser.getWeight();
                float height = clickedUser.getHeight();

                double BMR = 0;
                if(isMale){
                    BMR = (10*weight + 6.25*height - 5*age + 5)*1.2;
                }
                else if(isFemale){
                    BMR = (10*weight + 6.25*height - 5*age - 161)*1.2;
                }
                txtWelcome.setText("For " + name + " to: \n" +
                                "Gain weight: " + (BMR+500) + "Cal/day \n" +
                         "Maintain weight: " + BMR + "Cal/day \n" +
                        "Loss weight: " + (BMR-500) + "Cal/day \n");
                ShowUser();

                //clickedUser = null;
            }
        });
    }

    private void ShowUser() {
        userArrayAdapter = new ArrayAdapter<UserModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBase.getAll());
        lvUser.setAdapter(userArrayAdapter);
    }
}