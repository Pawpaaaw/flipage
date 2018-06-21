package flipage.cpu.com.cpuflipage.premain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;
import flipage.cpu.com.cpuflipage.MainActivity;
import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;


public class LoginActivity extends AppCompatActivity {

    private EditText userName, password;
    private LinearLayout progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_ll);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldValid(userName, password)) {
                    showProgress();
                    RetrofitImplementation retro = new RetrofitImplementation();
                    String uName = userName.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    retro.getUser(uName, pass, new Callback() {
                        @Override
                        public void onSuccess(Object object) {
                            User user = (User) object;
                            if (user != null) {
                                FlipagePrefrences.setUser(user, true);
                                hideProgress();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                hideProgress();
                                Snackbar.make(findViewById(android.R.id.content), "User not found", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            hideProgress();
                            Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        findViewById(R.id.view_flip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private boolean fieldValid(EditText... editText) {
        boolean valid = true;
        for (EditText et : editText) {
            if (et.getText().toString().trim().isEmpty()) {
                et.setError("Field is required");
                valid = false;
            }
        }
        return valid;
    }

    private void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgress(){
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}
