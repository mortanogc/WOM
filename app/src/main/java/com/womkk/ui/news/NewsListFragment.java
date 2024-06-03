package com.womkk.ui.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.womkk.R;
import com.womkk.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private List<News> newsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newsList = new ArrayList<>();
        adapter = new NewsListAdapter(newsList, getContext());
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), R.drawable.divider_news);
        recyclerView.addItemDecoration(dividerItemDecoration);

        loadNewsFromFirestore();
    }

    private void loadNewsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        newsList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            News news = document.toObject(News.class);
                            newsList.add(news);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("NewsListFragment", "Error getting documents.", task.getException());
                    }
                });
    }
}
