package flipage.cpu.com.cpuflipage.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = FlipagePrefrences.getUser();
        ((TextView) findViewById(R.id.user_name)).setText(user.getUsername());
        ((TextView) findViewById(R.id.user_number)).setText(user.getIdNumber());
        ((TextView) findViewById(R.id.user_email)).setText(user.getEmail());
        ((TextView) findViewById(R.id.user_department)).setText(user.getDepartment());

        ImageView imageView = findViewById(R.id.user_image);

        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.decodeBase64(user.getImage());
                if (bitmap != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }

            }
        }.start();

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                startActivity(intent);
            }
        });

    }
}
