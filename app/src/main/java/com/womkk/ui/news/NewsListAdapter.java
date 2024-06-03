package com.womkk.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.womkk.R;
import com.womkk.model.News;
import java.util.List;
import java.util.Locale;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<News> newsList;
    private Context context;

    public NewsListAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);

        String language = Locale.getDefault().getLanguage();
        if (language.equals("ru")) {
            holder.titleTextView.setText(news.getTitleRu());
            holder.contentTextView.setText(news.getContentRu());
        } else {
            holder.titleTextView.setText(news.getTitleEn());
            holder.contentTextView.setText(news.getContentEn());
        }

        holder.dateTextView.setText(news.getDate());

        Glide.with(context)
                .load(news.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView contentTextView;
        ImageView imageView;
        TextView dateTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.news_title);
            contentTextView = itemView.findViewById(R.id.news_content);
            imageView = itemView.findViewById(R.id.news_image);
            dateTextView = itemView.findViewById(R.id.news_date);
        }
    }
}
