package flipage.cpu.com.cpuflipage.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;


public class ProfileEditActivity extends AppCompatActivity {

    private static final int PICTURE_CODE_2 = 9874;

    private User user = FlipagePrefrences.getUser();
    private EditText mEmail, mUserName, mIDNumber;
    private TextInputLayout mPass;
    private ImageView mImage;
    private Bitmap image;
    private Button mSave;
    private LinearLayout progressBar;
    private TextView progressText;
    private Spinner spinner;
    private List<Department> departments = new ArrayList<>();
    RetrofitImplementation implementation = new RetrofitImplementation();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Edit Profile");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPass = findViewById(R.id.pass_ti);
        mEmail = findViewById(R.id.email);
        mUserName = findViewById(R.id.user_name);
        mIDNumber = findViewById(R.id.id_number);
        mImage = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_ll);
        spinner = findViewById(R.id.department);
        progressText = findViewById(R.id.progress_text);
        mSave = findViewById(R.id.create);
        mSave.setText("Update User");

        progressText.setText("Updating User");
        mUserName.setText(user.getUsername());
        mIDNumber.setText(user.getIdNumber());
        mEmail.setText(user.getEmail());
        mPass.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progress_ll);


        findViewById(R.id.capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICTURE_CODE_2);
            }
        });
        setImage();
        updateUser();
    }

    private void updateUser() {
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                if (fieldValid(mEmail, mUserName, mIDNumber)) {
                    if (isEmailValid(email)) {
                        showProgress();
                        User user = new User();
                        user.setImage(BitmapUtil.encodeToBase64(image, Bitmap.CompressFormat.PNG, 50));
                        user.setEmail(mEmail.getText().toString().trim());
                        user.setUsername(mUserName.getText().toString().trim());
                        user.setIdNumber(mIDNumber.getText().toString().trim());
                        String value = (String) spinner.getSelectedItem();
                        if (value.isEmpty()) {
                            Toast.makeText(ProfileEditActivity.this, "Can't create account. No Departments available", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        if (departments != null) {
                            for (Department department : departments) {
                                if (value.equalsIgnoreCase(department.getName())) {
                                    user.setDepartment(department);
                                    break;
                                }
                            }
                        }
                        implementation.updateUser(user, new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                hideProgress();
                                Toast.makeText(ProfileEditActivity.this, "Creation successful", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }

                            @Override
                            public void onError(String error) {
                                hideProgress();
                                Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        mEmail.setError("Invalid Email");
                    }

                }
            }
        });
    }

    private void setImage() {
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.decodeBase64(user.getImage());
                if (bitmap != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image = bitmap;
                            mImage.setImageBitmap(bitmap);
                        }
                    });
                }

            }
        }.start();
    }

    @Override
    protected void onResume() {
        showProgress();
        implementation.getDepartments(new Callback() {
            @Override
            public void onSuccess(Object object) {
                departments = (List<Department>) object;
                ArrayList<String> departmentStrings = new ArrayList<>();
                if (departments != null) {
                    for (Department department : departments) {
                        if (department.equals(user.getDepartment())) {
                            departmentStrings.add(0, department.getName());
                        } else {
                            departmentStrings.add(department.getName());
                        }
                    }
                }
                ArrayAdapter<String> departments = new ArrayAdapter(ProfileEditActivity.this, R.layout.spinner_cell, departmentStrings);
                spinner.setAdapter(departments);
                hideProgress();
            }

            @Override
            public void onError(String error) {
                hideProgress();
                Toast.makeText(ProfileEditActivity.this, "Failed retrieving departments", Toast.LENGTH_SHORT).show();
            }
        });
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_CODE_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            CropImage.activity(selectedImage).start(this);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null) {
                Uri imageUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    image = bitmap;
                    mImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(ProfileEditActivity.this, "Failed adding of image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
