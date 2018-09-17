package flipage.cpu.com.cpuflipage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.data.User;
import flipage.cpu.com.cpuflipage.forums.ForumsActivity;
import flipage.cpu.com.cpuflipage.premain.WelcomeActivity;
import flipage.cpu.com.cpuflipage.profile.ProfileActivity;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.Callback;
import flipage.cpu.com.cpuflipage.utils.FlipagePrefrences;

public class DepartmentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private static List<Department> depepartments = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    public static Department selectedDepartment;
    private RetrofitImplementation implementation = new RetrofitImplementation();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_activity);
        recyclerView = findViewById(R.id.recycler);
        refreshLayout = findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        syncDepartments();
    }


    private void syncDepartments() {
        implementation.getDepartments(new Callback() {
            @Override
            public void onSuccess(Object object) {
                refreshLayout.setRefreshing(false);
                depepartments = (List<Department>) object;
                DepartmentAdapter adapter = new DepartmentAdapter(depepartments, DepartmentActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DepartmentActivity.this,MainActivity.class);
                        Department department = (Department) view.getTag();
                        selectedDepartment = department;
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onError(String error) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(DepartmentActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        syncDepartments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        switch (id) {
            case R.id.logout: {
                Intent intent = new Intent(DepartmentActivity.this, WelcomeActivity.class);
                FlipagePrefrences.setUser(new User(), false);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.forums: {
                Intent intent = new Intent(DepartmentActivity.this, ForumsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.user: {
                Intent intent = new Intent(DepartmentActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
