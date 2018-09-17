package flipage.cpu.com.cpuflipage.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import flipage.cpu.com.cpuflipage.R;
import flipage.cpu.com.cpuflipage.data.News;
import flipage.cpu.com.cpuflipage.utils.BitmapUtil;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> implements Filterable {

    private List<News> newsList;
    private Activity mContext;
    private View.OnClickListener listener;
    private List<News> filteredList = new ArrayList<>();
    public static News newsSelected;

    public NewsAdapter(List<News> newsList, Activity mContext) {
        super();
        this.newsList = newsList;
        this.mContext = mContext;
        filteredList.addAll(newsList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filteredList.clear();
                String name = constraint.toString();


                final FilterResults results = new FilterResults();

                for (News news : newsList) {

                    if (news.getFileName().equals(name))
                        filteredList.add(news);
                }
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_cell, parent, false);
        Holder adapter = new Holder(layout);
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final News news = filteredList.get(position);
        holder.title.setText(news.getTitle());
        if (news.getDepartment() != null) {
            holder.college.setText(news.getDepartment().getName());
        }

        new Thread() {
            @Override
            public void run() {
                if(news.getImage()!= null) {
                    Bitmap bitmap = BitmapUtil.decodeBase64(news.getImage()
                    );
                    if (bitmap != null) {
                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }

            }
        }.start();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final News news = filteredList.get(position);
                Intent intent = new Intent(mContext, NewsPageActivity.class);
                newsSelected = news;
                mContext.startActivity(intent);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final News news = filteredList.get(position);
                Intent intent = new Intent(mContext, NewsPageActivity.class);
                intent.putExtra(NewsPageActivity.NEWS_DETAIL, news);
                mContext.startActivity(intent);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final News news = filteredList.get(position);
                Intent intent = new Intent(mContext, NewsPageActivity.class);
                intent.putExtra(NewsPageActivity.NEWS_DETAIL, news);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (filteredList == null) {
            return 0;
        }
        return filteredList.size();
    }

    public long getFirstDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public long getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis();
    }

    boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView college;
        RelativeLayout layout;

        public Holder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            college = view.findViewById(R.id.college);
            layout = view.findViewById(R.id.layout);
        }
    }
}

