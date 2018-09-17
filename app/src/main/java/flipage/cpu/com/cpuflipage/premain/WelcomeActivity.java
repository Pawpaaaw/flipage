package flipage.cpu.com.cpuflipage.premain;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import flipage.cpu.com.cpuflipage.DepartmentActivity;
import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FlipagePrefrences.getIsLoggedIn()){
            Intent intent = new Intent(WelcomeActivity.this, DepartmentActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.get_started).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
