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

public final class FragmentAppInfoBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextView aboutAuthor;

  @NonNull
  public final TextView appDescription;

  @NonNull
  public final TextView appDescription2;

  @NonNull
  public final TextView appInfoTitle;

  @NonNull
  public final TextView appVersion;

  @NonNull
  public final TextView contactInfo;

  private FragmentAppInfoBinding(@NonNull ScrollView rootView, @NonNull TextView aboutAuthor,
      @NonNull TextView appDescription, @NonNull TextView appDescription2,
      @NonNull TextView appInfoTitle, @NonNull TextView appVersion, @NonNull TextView contactInfo) {
    this.rootView = rootView;
    this.aboutAuthor = aboutAuthor;
    this.appDescription = appDescription;
    this.appDescription2 = appDescription2;
    this.appInfoTitle = appInfoTitle;
    this.appVersion = appVersion;
    this.contactInfo = contactInfo;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAppInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAppInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_app_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAppInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.about_author;
      TextView aboutAuthor = ViewBindings.findChildViewById(rootView, id);
      if (aboutAuthor == null) {
        break missingId;
      }

      id = R.id.app_description;
      TextView appDescription = ViewBindings.findChildViewById(rootView, id);
      if (appDescription == null) {
        break missingId;
      }

      id = R.id.app_description_2;
      TextView appDescription2 = ViewBindings.findChildViewById(rootView, id);
      if (appDescription2 == null) {
        break missingId;
      }

      id = R.id.app_info_title;
      TextView appInfoTitle = ViewBindings.findChildViewById(rootView, id);
      if (appInfoTitle == null) {
        break missingId;
      }

      id = R.id.app_version;
      TextView appVersion = ViewBindings.findChildViewById(rootView, id);
      if (appVersion == null) {
        break missingId;
      }

      id = R.id.contact_info;
      TextView contactInfo = ViewBindings.findChildViewById(rootView, id);
      if (contactInfo == null) {
        break missingId;
      }

      return new FragmentAppInfoBinding((ScrollView) rootView, aboutAuthor, appDescription,
          appDescription2, appInfoTitle, appVersion, contactInfo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
