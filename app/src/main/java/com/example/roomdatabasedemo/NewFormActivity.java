package com.example.roomdatabasedemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewFormActivity extends AppCompatActivity {

    private EditText NameEdt, EmailEdt, PhoneNumberEdt;
    Button SaveForm, ShareForm;


    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PHONENUMBER = "PHONENUMBER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_form);

        NameEdt = findViewById(R.id.idEdtName);
        EmailEdt = findViewById(R.id.idEdtEmail);
        PhoneNumberEdt = findViewById(R.id.idEdtPhoneNumber);
        SaveForm = findViewById(R.id.idBtnSaveForm);
        ShareForm = findViewById(R.id.idBtnShareForm);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            NameEdt.setText(intent.getStringExtra(EXTRA_NAME));
            EmailEdt.setText(intent.getStringExtra(EXTRA_EMAIL));
            PhoneNumberEdt.setText(intent.getStringExtra(EXTRA_PHONENUMBER));

        }
        SaveForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmpName = NameEdt.getText().toString();
                String EmpEmail = EmailEdt.getText().toString();
                String EmpPhoneNumber = PhoneNumberEdt.getText().toString();
                if (EmpName.isEmpty() || EmpEmail.isEmpty() || EmpPhoneNumber.isEmpty()){
                    Toast.makeText(NewFormActivity.this, "Please enter the valid Form details.", Toast.LENGTH_SHORT).show();
                } else {
                    SaveForm(EmpName, EmpEmail, EmpPhoneNumber);
                }
                Log.d("Add", "The Detail: " + EmpName + EmpEmail + EmpPhoneNumber);
            }
        });

        ShareForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String combinedText = "Name: " + NameEdt.getText().toString() + "\n" +
                        "Email: " + EmailEdt.getText().toString() + "\n" +
                        "Phone Number: " + PhoneNumberEdt.getText().toString();

                sendIntent.putExtra(Intent.EXTRA_TEXT, combinedText);
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
        });
    }

    private void SaveForm(String EmpName, String EmpEmail, String EmpPhoneNumber) {
        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, EmpName);
        data.putExtra(EXTRA_EMAIL, EmpEmail);
        data.putExtra(EXTRA_PHONENUMBER, EmpPhoneNumber);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        Toast.makeText(this, "Form has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }
}