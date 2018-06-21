package flipage.cpu.com.cpuflipage.premain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import flipage.cpu.com.cpuflipage.utils.BitmapUtil;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;


public class RegistrationActivity extends AppCompatActivity {

    private static final int PICTURE_CODE = 9873;

    private EditText mPass, mEmail, mUserName, mIDNumber;
    private ImageView mImage;
    private Bitmap image;
    private LinearLayout progressBar;
    private Spinner spinner;
    RetrofitImplementation implementation = new RetrofitImplementation();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mPass = findViewById(R.id.password);
        mEmail = findViewById(R.id.email);
        mUserName = findViewById(R.id.user_name);
        mIDNumber = findViewById(R.id.id_number);
        mImage = findViewById(R.id.image);
        progressBar = findViewById(R.id.progress_ll);
        spinner = findViewById(R.id.department);


        findViewById(R.id.capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICTURE_CODE);
            }
        });

        findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                if (fieldValid(mPass, mEmail, mUserName, mIDNumber)) {
                    if (isEmailValid(email)) {
                        if (image == null) {
                            Toast.makeText(RegistrationActivity.this, "Please add profile image", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        showProgress();
                        User user = new User();
                        user.setImage(BitmapUtil.encodeToBase64(image, Bitmap.CompressFormat.PNG, 50));
                        user.setPassword(mPass.getText().toString().trim());
                        user.setEmail(mEmail.getText().toString().trim());
                        user.setUsername(mUserName.getText().toString().trim());
                        user.setIdNumber(mIDNumber.getText().toString().trim());
                        String value = (String) spinner.getSelectedItem();
                        if (value.isEmpty()) {
                            Toast.makeText(RegistrationActivity.this, "Can't create account. No Departments available", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        user.setDepartment(value);
                        implementation.createUser(user, new Callback() {
                            @Override
                            public void onSuccess(Object object) {
                                hideProgress();
                                Toast.makeText(RegistrationActivity.this, "Creation successful", Toast.LENGTH_SHORT).show();
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

        showProgress();
        implementation.getDepartments(new Callback() {
            @Override
            public void onSuccess(Object object) {
                List<Department> departmentList = (List<Department>) object;
                ArrayList<String> departmentStrings = new ArrayList<>();
                if (departmentList != null) {
                    for (Department department : departmentList) {
                        departmentStrings.add(department.getName());
                    }
                }
                ArrayAdapter<String> departments = new ArrayAdapter(RegistrationActivity.this, R.layout.support_simple_spinner_dropdown_item, departmentStrings);
                spinner.setAdapter(departments);
                hideProgress();
            }

            @Override
            public void onError(String error) {
                hideProgress();
                Toast.makeText(RegistrationActivity.this, "Failed retrieving departments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
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
                Toast.makeText(RegistrationActivity.this, "Failed adding of image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
