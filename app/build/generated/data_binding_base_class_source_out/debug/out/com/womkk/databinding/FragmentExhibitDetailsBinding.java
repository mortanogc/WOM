// Generated by view binder compiler. Do not edit!
package com.womkk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.womkk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentExhibitDetailsBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button arButton;

  @NonNull
  public final TextView exhibitDescription;

  @NonNull
  public final ImageView exhibitImage;

  @NonNull
  public final TextView exhibitName;

  @NonNull
  public final Button mapButton;

  private FragmentExhibitDetailsBinding(@NonNull ScrollView rootView, @NonNull Button arButton,
      @NonNull TextView exhibitDescription, @NonNull ImageView exhibitImage,
      @NonNull TextView exhibitName, @NonNull Button mapButton) {
    this.rootView = rootView;
    this.arButton = arButton;
    this.exhibitDescription = exhibitDescription;
    this.exhibitImage = exhibitImage;
    this.exhibitName = exhibitName;
    this.mapButton = mapButton;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentExhibitDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentExhibitDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_exhibit_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentExhibitDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ar_button;
      Button arButton = ViewBindings.findChildViewById(rootView, id);
      if (arButton == null) {
        break missingId;
      }

      id = R.id.exhibit_description;
      TextView exhibitDescription = ViewBindings.findChildViewById(rootView, id);
      if (exhibitDescription == null) {
        break missingId;
      }

      id = R.id.exhibit_image;
      ImageView exhibitImage = ViewBindings.findChildViewById(rootView, id);
      if (exhibitImage == null) {
        break missingId;
      }

      id = R.id.exhibit_name;
      TextView exhibitName = ViewBindings.findChildViewById(rootView, id);
      if (exhibitName == null) {
        break missingId;
      }

      id = R.id.map_button;
      Button mapButton = ViewBindings.findChildViewById(rootView, id);
      if (mapButton == null) {
        break missingId;
      }

      return new FragmentExhibitDetailsBinding((ScrollView) rootView, arButton, exhibitDescription,
          exhibitImage, exhibitName, mapButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
