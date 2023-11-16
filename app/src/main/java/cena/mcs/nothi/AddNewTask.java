package cena.mcs.nothi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddNewTask extends AppCompatActivity {

    EditText _txtDate, _txtNote;

    Users user;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        _txtDate = findViewById(R.id.etDate);
        _txtNote = findViewById(R.id.etNote);

        Button save = this.findViewById(R.id.btSave);

        user = new Users();

        save.setOnClickListener(v -> {
            saveNote();
        });
    }

    private void saveNote() {
        String date = _txtDate.getText().toString();
        String note = _txtNote.getText().toString();

        Toast.makeText(this, "Saving Data...", Toast.LENGTH_SHORT).show();

        DatabaseReference dbNotes =  mDatabase.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        String id = dbNotes.push().getKey();
        user.setTanggal(date);
        user.setIsi(note);

        Map<String, Object> newData = new HashMap<>();
        newData.put("tanggal", date);
        newData.put("isi", note);

        dbNotes.child(id).setValue(user);
        dbNotes.updateChildren(newData);
        finish();
    }
}