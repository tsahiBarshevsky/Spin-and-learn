package tsahi.and.kostia.spinandlearn;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>
{
    private List<UserInfo> users;
    Context context;

    public ScoreAdapter(List<UserInfo> users, Context context) {

        this.users = users;
        this.context = context;
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imageView;
        TextView nameTV, scoreTV, levelTV, dateTV, timeTV, titleTV;

        public ScoreViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCardCell);
            nameTV = itemView.findViewById(R.id.userNameCardCell);
            scoreTV = itemView.findViewById(R.id.userScoreCardCell);
            levelTV = itemView.findViewById(R.id.userLevelCardCell);
            dateTV = itemView.findViewById(R.id.userDateCardCell);
            timeTV = itemView.findViewById(R.id.userTimeCardCell);
            titleTV = itemView.findViewById(R.id.userScoreTitle);

            Typeface typeface = ResourcesCompat.getFont(context, R.font.stephia);;
            if (Locale.getDefault().toString().equals("iw_IL"))
            {
                typeface = ResourcesCompat.getFont(context, R.font.abraham);
            }
            else if(Locale.getDefault().toString().equals("ru_RU")){
                typeface = ResourcesCompat.getFont(context, R.font.wagnasty);
            }
                nameTV.setTypeface(typeface);
                scoreTV.setTypeface(typeface);
                levelTV.setTypeface(typeface);
                dateTV.setTypeface(typeface);
                timeTV.setTypeface(typeface);
                titleTV.setTypeface(typeface);
        }
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //os calls this function
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_cell, parent, false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        UserInfo userInfo = users.get(position);
        holder.nameTV.setText(userInfo.getName());
        holder.scoreTV.setText(String.valueOf(userInfo.getScore()));
        holder.imageView.setImageBitmap(userInfo.getPhoto());
        holder.levelTV.setText(userInfo.getLevel());
        holder.dateTV.setText(userInfo.getDate());
        holder.timeTV.setText(userInfo.getTime());
    }

    @Override
    public int getItemCount(){ //return how many users
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
