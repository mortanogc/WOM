package com.womkk.ui.exhibits;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.womkk.R;
import com.womkk.model.Exhibit;
import com.womkk.ui.ar.ARActivity;

import java.util.Locale;

public class ExhibitDetailsFragment extends Fragment {

    private ExhibitsViewModel exhibitsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exhibitsViewModel = new ViewModelProvider(requireActivity()).get(ExhibitsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibit_details, container, false);

        ImageView imageView = view.findViewById(R.id.exhibit_image);
        TextView nameTextView = view.findViewById(R.id.exhibit_name);
        TextView descriptionTextView = view.findViewById(R.id.exhibit_description);
        Button arButton = view.findViewById(R.id.ar_button);
        Button mapButton = view.findViewById(R.id.map_button);  // Новая кнопка

        String exhibitId = getArguments().getString("exhibitId");

        Exhibit selectedExhibit = findExhibitById(exhibitId);
        if (selectedExhibit != null) {
            String language = Locale.getDefault().getLanguage();
            if (language.equals("ru")) {
                nameTextView.setText(selectedExhibit.getNameRu());
                descriptionTextView.setText(selectedExhibit.getDescriptionRu());
            } else {
                nameTextView.setText(selectedExhibit.getNameEn());
                descriptionTextView.setText(selectedExhibit.getDescriptionEn());
            }
            Glide.with(getContext()).load(selectedExhibit.getImageUrl()).into(imageView);

            if (selectedExhibit.isARCapable()) {
                arButton.setVisibility(View.VISIBLE);
                arButton.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), ARActivity.class);
                    intent.putExtra("exhibitId", selectedExhibit.getId());
                    startActivity(intent);
                });
            } else {
                arButton.setVisibility(View.GONE);
            }

            if (selectedExhibit.getMapLink() != null && !selectedExhibit.getMapLink().isEmpty()) {
                mapButton.setVisibility(View.VISIBLE);
                mapButton.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedExhibit.getMapLink()));
                    startActivity(intent);
                });
            } else {
                mapButton.setVisibility(View.GONE);
            }
        }

        return view;
    }

    private Exhibit findExhibitById(String id) {
        for (Exhibit exhibit : exhibitsViewModel.getExhibitList().getValue()) {
            if (exhibit.getId().equals(id)) {
                return exhibit;
            }
        }
        return null;
    }
}
