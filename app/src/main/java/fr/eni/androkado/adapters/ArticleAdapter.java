package fr.eni.androkado.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.eni.androkado.R;
import fr.eni.androkado.bo.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    ArrayList<Article> articles = null;
    private View.OnClickListener monClickListener;

    public ArticleAdapter(ArrayList<Article> articles, View.OnClickListener monClickListener) {
        this.articles = articles;
        this.monClickListener = monClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewNom;
        public RatingBar mRatingBar;

        public ViewHolder(View v, View.OnClickListener monClickListener) {
            super(v);
            mTextViewNom = v.findViewById(R.id.tv_info);
            mRatingBar = v.findViewById(R.id.rating_note);
            v.setOnClickListener(monClickListener);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cards, parent, false);
        ViewHolder vh = new ViewHolder(v, monClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mTextViewNom.setText(articles.get(position).getNom());
        holder.mRatingBar.setRating(articles.get(position).getNote());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }



}
