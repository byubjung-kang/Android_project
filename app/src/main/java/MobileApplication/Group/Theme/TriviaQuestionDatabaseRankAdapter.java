package MobileApplication.Group.Theme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import MobileApplication.Group.R;

public class TriviaQuestionDatabaseRankAdapter extends RecyclerView.Adapter<TriviaQuestionDatabaseRankAdapter.ViewHolder> {


    private List<TriviaQuestionDatabaseRankItem> rankingList = new ArrayList<>();


    @NonNull
    @Override
    public TriviaQuestionDatabaseRankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_rank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TriviaQuestionDatabaseRankItem item = rankingList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {

        return rankingList.size();
    }

    public void submitList(List<TriviaQuestionDatabaseRankItem> list) {
        rankingList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rankTextView;
        private TextView nameTextView;
        private TextView scoreTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.rankTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }

        public void bind(TriviaQuestionDatabaseRankItem item) {
            rankTextView.setText(String.valueOf(item.getRank()));
            nameTextView.setText(item.getName());
            scoreTextView.setText(String.valueOf(item.getScore()));
        }
    }
}
