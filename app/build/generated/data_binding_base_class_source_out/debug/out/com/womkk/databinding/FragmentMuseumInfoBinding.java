// Generated by view binder compiler. Do not edit!
package com.womkk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class FragmentMuseumInfoBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextView museumAbout;

  @NonNull
  public final TextView museumAddress;

  @NonNull
  public final TextView museumContactInfo;

  @NonNull
  public final TextView museumInfoTitle;

  @NonNull
  public final TextView museumWorkingHours;

  @NonNull
  public final TextView privacy;

  private FragmentMuseumInfoBinding(@NonNull ScrollView rootView, @NonNull TextView museumAbout,
      @NonNull TextView museumAddress, @NonNull TextView museumContactInfo,
      @NonNull TextView museumInfoTitle, @NonNull TextView museumWorkingHours,
      @NonNull TextView privacy) {
    this.rootView = rootView;
    this.museumAbout = museumAbout;
    this.museumAddress = museumAddress;
    this.museumContactInfo = museumContactInfo;
    this.museumInfoTitle = museumInfoTitle;
    this.museumWorkingHours = museumWorkingHours;
    this.privacy = privacy;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMuseumInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMuseumInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_museum_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMuseumInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.museum_about;
      TextView museumAbout = ViewBindings.findChildViewById(rootView, id);
      if (museumAbout == null) {
        break missingId;
      }

      id = R.id.museum_address;
      TextView museumAddress = ViewBindings.findChildViewById(rootView, id);
      if (museumAddress == null) {
        break missingId;
      }

      id = R.id.museum_contact_info;
      TextView museumContactInfo = ViewBindings.findChildViewById(rootView, id);
      if (museumContactInfo == null) {
        break missingId;
      }

      id = R.id.museum_info_title;
      TextView museumInfoTitle = ViewBindings.findChildViewById(rootView, id);
      if (museumInfoTitle == null) {
        break missingId;
      }

      id = R.id.museum_working_hours;
      TextView museumWorkingHours = ViewBindings.findChildViewById(rootView, id);
      if (museumWorkingHours == null) {
        break missingId;
      }

      id = R.id.privacy;
      TextView privacy = ViewBindings.findChildViewById(rootView, id);
      if (privacy == null) {
        break missingId;
      }

      return new FragmentMuseumInfoBinding((ScrollView) rootView, museumAbout, museumAddress,
          museumContactInfo, museumInfoTitle, museumWorkingHours, privacy);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
