package tsahi.and.kostia.spinandlearn;

import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfo
{
    private CircleImageView imageView;
    private String name;
    private int score;

    public UserInfo(CircleImageView imageView, String name, int score) {
        this.imageView = imageView;
        this.name = name;
        this.score = score;
    }

    public CircleImageView getImageView() {
        return imageView;
    }

    public void setImageView(CircleImageView imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
