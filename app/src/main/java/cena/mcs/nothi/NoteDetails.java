package cena.mcs.nothi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoteDetails extends AppCompatActivity {

    EditText _title, _context;

    DatabaseReference mDatabase;
    boolean isEditMode = false;
    Button deleteNote;
    String title,content;
    TextView pageTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        _title = findViewById(R.id.notes_title_text);
        _context = findViewById(R.id.notes_content_text);
        deleteNote = findViewById(R.id.btDel);
        pageTitleTextView = findViewById(R.id.tvPage);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        final String docId = getIntent().getStringExtra("id");

        if (docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        _title.setText(title);
        _context.setText(content);
        if (isEditMode) {
            pageTitleTextView.setText("Edit your note");
            deleteNote.setVisibility(View.VISIBLE);
        }

        Button save = this.findViewById(R.id.btSave);

        save.setOnClickListener((v)-> saveNote());

        deleteNote.setOnClickListener((v)-> deleteNoteFromFirebase(docId));
    }

    private void updateData(String id) {
        title = _title.getText().toString();
        content = _context.getText().toString();
        Data listdata = new Data(id, title, content);
        mDatabase.child("Note").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(NoteDetails.this, "Notes Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                    }
                });
    }

    private void deleteNoteFromFirebase(String docId) {
        mDatabase.child("Note").child(docId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NoteDetails.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                    }
                });
    }

    private void saveNote() {
        title = _title.getText().toString();
        content = _context.getText().toString();
        final String id = getIntent().getStringExtra("id");
        if (isEditMode) {
            updateData(id);
        } else {
            AddNotes(title, content);
        }
    }

    private void AddNotes(String title, String content) {
        String id = mDatabase.push().getKey();
        Data listdata = new Data(id, title, content);
        mDatabase.child("Note").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(NoteDetails.this, "Notes Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                    }
                });
    }
}