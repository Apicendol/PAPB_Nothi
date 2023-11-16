package cena.mcs.nothi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
//    DatabaseReference db;
    List<Users> tasks;
    ToDoAdapter adapter;
    RecyclerView recyclerView;

    FirebaseAuth mAuth;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FloatingActionButton add = findViewById(R.id.fabAdd);
        add.setOnClickListener(v -> {
            Intent addNew = new Intent(this, AddNewTask.class);
            this.startActivity(addNew);
        });

        mAuth = FirebaseAuth.getInstance();
//        dbRef = db.getReference("Users").child(mAuth.getCurrentUser().getUid());
//        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbRef = db.getReference("Users").child();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasks = new ArrayList<>();

        ToDoAdapter adapter = new ToDoAdapter(this, tasks);
//        adapter.setNotesList(tasks);
        recyclerView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tasks.clear();

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot note : dataSnapshot.getChildren()) {
                        Users data = note.getValue(Users.class);
                        tasks.add(data);
                    }
                }

                adapter.notifyDataSetChanged();

//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Users data = snapshot.getValue(Users.class);
//                    tasks.add(data);
//                }

//                ToDoAdapter adapter = new ToDoAdapter(HomePage.this, (List<Users>) tasks);
//                ToDoAdapter adapter = new ToDoAdapter(HomePage.this);
//                adapter.setNotesList(tasks);
//                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomePage.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
            }
        });

//        recyclerView.setAdapter(adapter);

//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tasks.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Users user = snapshot.getValue(Users.class);
//                    tasks.add(user);
//                }
////                adapter.notifyDataSetChanged();
//                ToDoAdapter adapter = new ToDoAdapter(HomePage.this, (List<Users>) tasks);
////                adapter.setNote(tasks);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(HomePage.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    protected void onStart() {
//        super.onStart();
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tasks.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Users data = snapshot.getValue(Users.class);
//                    tasks.add(data);
//                }
//
////                ToDoAdapter adapter = new ToDoAdapter(HomePage.this, (List<Users>) tasks);
//                ToDoAdapter adapter = new ToDoAdapter(HomePage.this);
//                adapter.setNotesList(tasks);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(HomePage.this, "Terjadi kesalahan.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}