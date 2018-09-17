package flipage.cpu.com.cpuflipage.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import flipage.cpu.com.cpuflipage.utils.BitmapUtil;
import flipage.cpu.com.cpuflipage.utils.FlipageApp;
import flipage.cpu.com.cpuflipage.R;


public class Mock {

    public static List<News> getNews() {

        List<News> newsList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            News news = new News();
            news.setTitle("Burger");
            Bitmap icon = BitmapFactory.decodeResource(FlipageApp.getContext().getResources(),
                    R.drawable.food);
            Comment comment = new Comment();
            User user = new User();
            user.setImage(BitmapUtil.encodeToBase64(icon, Bitmap.CompressFormat.JPEG, 100));
            comment.setUser(user);
            comment.setMessage("Wow!");
            List<Comment> comments = new ArrayList<>();
            comments.add(comment);
            newsList.add(news);
        }
        return newsList;
    }

}
