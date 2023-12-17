package bhaskar.charles.cabbage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class page2 extends AppCompatActivity {


    Button topostButton, logoutButton, loadButton;
ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        topostButton = findViewById(R.id.topostButton);
        listView = findViewById(R.id.listView);
        logoutButton = findViewById(R.id.logoutButton);
        loadButton = findViewById(R.id.loadButton);

        topostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainactivity();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(page2.this, "Logged out ;(", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(page2.this, login.class));
                finish(); // Optional: Close the current activity after logging out
            }
        });





        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("detailsData");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> list = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String value = dataSnapshot.child("details").getValue(String.class);
                            String emailValue = dataSnapshot.child("email").getValue(String.class);

                            // Concatenate details and email into a single string
                            String combinedValue = value + "\nemail: " + emailValue;
                            list.add(combinedValue);
                        }

                        // Update UI on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(page2.this, android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);

                                ArrayAdapter<String> adapteremail = new ArrayAdapter<>(page2.this, android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapteremail);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error
                    }
                });
            }
        });


        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(page2.this, R.layout.list_item, list);
        listView.setAdapter(adapter);
        final ArrayList<String> emailList = new ArrayList<>();
        final ArrayAdapter<String> adapteremail = new ArrayAdapter<>(page2.this, R.layout.list_item, emailList);
        listView.setAdapter(adapteremail);


    }



    private void openMainactivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}