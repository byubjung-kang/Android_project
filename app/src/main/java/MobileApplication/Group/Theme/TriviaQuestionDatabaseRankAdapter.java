package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import MobileApplication.Group.R;


/**
 * A custom RecyclerView adapter for displaying a list of ranking items.
 * @author byubjung kang
 * @version 1.0
 */
public class TriviaQuestionDatabaseRankAdapter extends RecyclerView.Adapter<TriviaQuestionDatabaseRankAdapter.ViewHolder> {

    /**
     * List to store the ranking items
     */
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

    /**
     * Submits a new list of ranking items to be displayed in the RecyclerView.
     *
     * @param list The new list of ranking items.
     */
    public void submitList(List<TriviaQuestionDatabaseRankItem> list) {
        rankingList = list;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class to hold and display individual ranking items in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rankTextView;
        private TextView nameTextView;
        private TextView scoreTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            rankTextView = itemView.findViewById(R.id.rankTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        TriviaQuestionDatabaseRankItem currentItem = rankingList.get(position);


                        TriviaRankDetailFragment userDetailFragment = new TriviaRankDetailFragment();
                        FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();


                        Bundle bundle = new Bundle();
                        bundle.putString("rank", String.valueOf(currentItem.getRank()));
                        bundle.putString("name", currentItem.getName());
                        bundle.putString("score", String.valueOf(currentItem.getScore()));
                        userDetailFragment.setArguments(bundle);


                        transaction.replace(R.id.fragmentFrame3, userDetailFragment);
                        transaction.addToBackStack(null);


                        transaction.commit();
                    }
                }
        });

        }

        /**
         * Binds the ranking item data to the ViewHolder's views.
         *
         * @param item The ranking item to bind.
         */
        public void bind(TriviaQuestionDatabaseRankItem item) {
            rankTextView.setText(String.valueOf(item.getRank()));
            nameTextView.setText(item.getName());
            scoreTextView.setText(String.valueOf(item.getScore()));
        }
    }}
