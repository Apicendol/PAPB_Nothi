package cena.mcs.nothi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    EditText _txtEmail, _txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            if (user.isEmailVerified()) {
                Intent home = new Intent(this, HomePage.class);
                home.putExtra("email", user.getEmail());
                startActivity(home);
            }
        }

        _txtEmail = findViewById(R.id.etEmail2);
        _txtPassword = findViewById(R.id.etPass2);

        ImageView _back = this.findViewById(R.id.btnBack);
        Button _signIn = this.findViewById(R.id.btSave);

        _back.setOnClickListener(v -> {
            startActivity(new Intent(this, Welcome.class));
            finish();
        });

        _signIn.setOnClickListener(v -> {
            String email = _txtEmail.getText().toString();
            String pass = _txtPassword.getText().toString();
            Login(email, pass);
        });
    }

    private void Login(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (auth.getCurrentUser().isEmailVerified()) {
                                startActivity(new Intent(SignIn.this, HomePage.class));
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Email Not Verified", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SignIn.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}