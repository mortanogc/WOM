// Generated by view binder compiler. Do not edit!
package com.womkk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.womkk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentExhibitListBinding implements ViewBinding {
  @NonNull
  private final SwipeRefreshLayout rootView;

  @NonNull
  public final GridView gridView;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshLayout;

  private FragmentExhibitListBinding(@NonNull SwipeRefreshLayout rootView,
      @NonNull GridView gridView, @NonNull SwipeRefreshLayout swipeRefreshLayout) {
    this.rootView = rootView;
    this.gridView = gridView;
    this.swipeRefreshLayout = swipeRefreshLayout;
  }

  @Override
  @NonNull
  public SwipeRefreshLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentExhibitListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentExhibitListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_exhibit_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentExhibitListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.grid_view;
      GridView gridView = ViewBindings.findChildViewById(rootView, id);
      if (gridView == null) {
        break missingId;
      }

      SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView;

      return new FragmentExhibitListBinding((SwipeRefreshLayout) rootView, gridView,
          swipeRefreshLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
