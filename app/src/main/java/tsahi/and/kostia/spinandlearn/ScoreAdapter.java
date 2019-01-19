package tsahi.and.kostia.spinandlearn;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>
{
    private List<UserInfo> users;

    public ScoreAdapter(List<UserInfo> users) {
        this.users = users;
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imageView;
        TextView nameTV, scoreTV;

        public ScoreViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCardCell);
            nameTV = itemView.findViewById(R.id.userNameCardCell);
            scoreTV = itemView.findViewById(R.id.userScoreCardCell);
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
