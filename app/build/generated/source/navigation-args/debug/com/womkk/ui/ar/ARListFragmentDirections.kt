package com.womkk.ui.ar

import android.os.Bundle
import androidx.navigation.NavDirections
import com.womkk.R
import kotlin.Int
import kotlin.String

public class ARListFragmentDirections private constructor() {
  private data class ActionArListFragmentToExhibitDetailsFragment(
    public val exhibitId: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_arListFragment_to_exhibitDetailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("exhibitId", this.exhibitId)
        return result
      }
  }

  public companion object {
    public fun actionArListFragmentToExhibitDetailsFragment(exhibitId: String): NavDirections =
        ActionArListFragmentToExhibitDetailsFragment(exhibitId)
  }
}
