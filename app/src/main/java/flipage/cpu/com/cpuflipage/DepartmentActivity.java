package flipage.cpu.com.cpuflipage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.retrofit.RetrofitImplementation;
import flipage.cpu.com.cpuflipage.utils.Callback;

public class DepartmentActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private static List<Department> depepartments = new ArrayList<>();

    private RetrofitImplementation implementation = new RetrofitImplementation();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.recycler);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        implementation.getDepartments(new Callback() {
            @Override
            public void onSuccess(Object object) {
                depepartments = (List<Department>) object;
                DepartmentAdapter adapter = new DepartmentAdapter(depepartments, DepartmentActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }

            @Override
            public void onError(String error) {
                Toast.makeText(DepartmentActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
