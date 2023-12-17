package bhaskar.charles.cabbage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.UiAutomation;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextInputEditText detailField, emailField;
    Button postButton, browseButton;
    DatabaseReference databaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailField = findViewById(R.id.detailField);
        emailField=findViewById(R.id.emailField);
        postButton = findViewById(R.id.postButton);
        browseButton = findViewById(R.id.browseButton);

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpage2();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("detailsData");
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushDetails();
            }
        });

    }

    private void openpage2(){
        Intent intent = new Intent(this, page2.class);
        startActivity(intent);
    }

    private void pushDetails(){
        String detailsField = Objects.requireNonNull(detailField.getText()).toString();
        String emailfield = Objects.requireNonNull(emailField.getText()).toString();

        details Details = new details(detailsField,emailfield);

        databaseReference.push().setValue(Details);
        Toast.makeText(this, "Posted!", Toast.LENGTH_SHORT).show();
    }

}