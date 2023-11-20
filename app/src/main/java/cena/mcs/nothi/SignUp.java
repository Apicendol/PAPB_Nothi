package cena.mcs.nothi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    EditText _txtEmail, _txtUsername, _txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        _txtEmail = findViewById(R.id.etEmail);
        _txtUsername = findViewById(R.id.etUser);
        _txtPassword = findViewById(R.id.etPass);
        ImageView _back = this.findViewById(R.id.btnBack);
        TextView _Signin = this.findViewById(R.id.txtSignIn);
        Button _Register = this.findViewById(R.id.btCreate);

        _back.setOnClickListener(v -> {
            startActivity(new Intent(this, Welcome.class));
            finish();
        });

        _Signin.setOnClickListener(v -> {
            Intent signin = new Intent(this, SignIn.class);
            this.startActivity(signin);
        });

        _Register.setOnClickListener(v -> {
            String email = _txtEmail.getText().toString();
            String username = _txtUsername.getText().toString();
            String pass = _txtPassword.getText().toString();
            createAccountInFirebase(email, pass);
        });
    }

    private void createAccountInFirebase(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignUp.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "Email verifikasi dikirim ke " + email, Toast.LENGTH_LONG).show();
                            auth.getCurrentUser().sendEmailVerification();
                            auth.signOut();
                            finish();
                        }else{
                            Toast.makeText(SignUp.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}