package com.example.roomdatabasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class NewFormActivity extends AppCompatActivity {

    private EditText NameEdt, EmailEdt, PhoneNumberEdt;
    Button SaveForm;

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
                    return;
                }
                SaveForm(EmpName, EmpEmail, EmpPhoneNumber);
            }
        });
    }

    private void SaveForm(String EmpName, String EmpEmail, String EmpPhoneNumber){
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