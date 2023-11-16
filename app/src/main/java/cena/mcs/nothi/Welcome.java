package cena.mcs.nothi;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class Welcome extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient googleSignInClient;

    int RC_SIGN_IN = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("830193951053-c46la5jn3rjgopdflu1kgk89bqpjdb8e.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        Button _btnGooogle = this.findViewById(R.id.btnGoogle);
        Button _btnCreateAcc = this.findViewById(R.id.btnCreateAcc);
        Button _btnSignIn = this.findViewById(R.id.btnSignIn);

        _btnGooogle.setOnClickListener(v -> {
            Intent google = googleSignInClient.getSignInIntent();
            startActivityForResult(google, RC_SIGN_IN);
//            this.startActivity(regist);
        });

        _btnCreateAcc.setOnClickListener(v -> {
            Intent regist = new Intent(this, SignUp.class);
            this.startActivity(regist);
        });

        _btnSignIn.setOnClickListener(v -> {
            Intent login = new Intent(this, SignIn.class);
            this.startActivity(login);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                FirebaseAuth(acc.getIdToken());
            } catch (ApiException e) {
                throw new RuntimeException();
            }
        }
    }

    private void FirebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();
                            users.setUserId(users.getUserId());
                            users.setName(users.getName());
                            users.setProfile(users.getProfile().toString());

                            database.getReference().child("Users").child(users.getUserId()).setValue(users);

                            Intent home = new Intent(Welcome.this, HomePage.class);
                            startActivity(home);
                        } else {
                            Toast.makeText(Welcome.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}