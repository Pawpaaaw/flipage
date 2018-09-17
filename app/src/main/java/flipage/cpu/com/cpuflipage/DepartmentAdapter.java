package flipage.cpu.com.cpuflipage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;

public class DepartmentAdapter  extends RecyclerView.Adapter<DepartmentAdapter.Holder> {

    private List<Department> departmentList;
    private View.OnClickListener onClickListener;
    private Activity activity;

    public DepartmentAdapter(List<Department> departmentList, Activity activity, View.OnClickListener onClickListener) {
        this.departmentList = departmentList;
        this.activity = activity;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_view_row, parent, false);
        Holder adapter = new Holder(layout);
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Department department = departmentList.get(position);
        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtil.decodeBase64(department.getImage());
                if (bitmap != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.imageView.setImageBitmap(bitmap);
                        }
                    });
                }

            }
        }.start();
        holder.title.setText(department.getName());
        holder.cardView.setOnClickListener(onClickListener);
        holder.cardView.setTag(department);
    }

    @Override
    public int getItemCount() {
        if(departmentList != null){
            return departmentList.size();
        }
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        LinearLayout cardView;

        public Holder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            imageView = itemView.findViewById(R.id.department_img);
            title = itemView.findViewById(R.id.department);
        }
    }
}
