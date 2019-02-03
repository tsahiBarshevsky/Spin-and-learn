package tsahi.and.kostia.spinandlearn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class UserInfo
{
    Bitmap photo;
    String name;
    Integer score;
    String level;
    String date;
    String time;

    public UserInfo() {
        photo = null;
        name = "";
        score = 0;
    }

    public UserInfo(String raw) {
        String[] data = raw.split(";");
        name = new String(data[0]);
        score = Integer.parseInt(data[1]);
        photo = StringToBitMap(data[2]);
        level = new String(data[3]);
        date = new String(data[4]);
        time = new String(data[5]);
    }

    public UserInfo(Bitmap photo, String name, Integer score, String level, String date, String time) {
        this.photo = photo;
        this.name = name;
        this.score = score;
        this.level = level;
        this.date = date;
        this.time = time;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public String getLevel() {
        return level;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String BitMapToString(Bitmap bitmap) {
        if(bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        }
        return "-1";
    }

    public Bitmap StringToBitMap(String encodedString) {
        if (!encodedString.equals("-1")) {
            try {
                byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name + ";" + score.toString() + ";" + BitMapToString(photo) + ";" + level + ";" + date + ";" + time;
    }
}
