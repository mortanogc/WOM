package com.womkk.ui.fullscreenImage

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class FullscreenImageFragmentArgs(
  public val imageUrl: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("imageUrl", this.imageUrl)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("imageUrl", this.imageUrl)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): FullscreenImageFragmentArgs {
      bundle.setClassLoader(FullscreenImageFragmentArgs::class.java.classLoader)
      val __imageUrl : String?
      if (bundle.containsKey("imageUrl")) {
        __imageUrl = bundle.getString("imageUrl")
        if (__imageUrl == null) {
          throw IllegalArgumentException("Argument \"imageUrl\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"imageUrl\" is missing and does not have an android:defaultValue")
      }
      return FullscreenImageFragmentArgs(__imageUrl)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        FullscreenImageFragmentArgs {
      val __imageUrl : String?
      if (savedStateHandle.contains("imageUrl")) {
        __imageUrl = savedStateHandle["imageUrl"]
        if (__imageUrl == null) {
          throw IllegalArgumentException("Argument \"imageUrl\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"imageUrl\" is missing and does not have an android:defaultValue")
      }
      return FullscreenImageFragmentArgs(__imageUrl)
    }
  }
}
