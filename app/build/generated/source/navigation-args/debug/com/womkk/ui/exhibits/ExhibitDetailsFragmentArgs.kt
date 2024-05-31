package com.womkk.ui.exhibits

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class ExhibitDetailsFragmentArgs(
  public val exhibitId: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("exhibitId", this.exhibitId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("exhibitId", this.exhibitId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ExhibitDetailsFragmentArgs {
      bundle.setClassLoader(ExhibitDetailsFragmentArgs::class.java.classLoader)
      val __exhibitId : String?
      if (bundle.containsKey("exhibitId")) {
        __exhibitId = bundle.getString("exhibitId")
        if (__exhibitId == null) {
          throw IllegalArgumentException("Argument \"exhibitId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"exhibitId\" is missing and does not have an android:defaultValue")
      }
      return ExhibitDetailsFragmentArgs(__exhibitId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle):
        ExhibitDetailsFragmentArgs {
      val __exhibitId : String?
      if (savedStateHandle.contains("exhibitId")) {
        __exhibitId = savedStateHandle["exhibitId"]
        if (__exhibitId == null) {
          throw IllegalArgumentException("Argument \"exhibitId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"exhibitId\" is missing and does not have an android:defaultValue")
      }
      return ExhibitDetailsFragmentArgs(__exhibitId)
    }
  }
}
