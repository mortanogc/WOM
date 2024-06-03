package com.womkk.ui.exhibits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.womkk.model.Exhibit;
import java.util.ArrayList;
import java.util.List;

public class ExhibitsViewModel extends ViewModel {
    private MutableLiveData<List<Exhibit>> exhibitList;

    public ExhibitsViewModel() {
        exhibitList = new MutableLiveData<>();
        loadExhibits(null);
    }

    public LiveData<List<Exhibit>> getExhibitList() {
        return exhibitList;
    }

    public void loadExhibits(Runnable callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("exhibits")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Exhibit> exhibits = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Exhibit exhibit = document.toObject(Exhibit.class);
                            exhibits.add(exhibit);
                        }
                        exhibitList.setValue(exhibits);
                    }
                    if (callback != null) {
                        callback.run();
                    }
                });
    }
}
