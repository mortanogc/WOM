package com.womkk.ui.exhibits

import android.os.Bundle
import androidx.navigation.NavDirections
import com.womkk.R
import kotlin.Int
import kotlin.String

public class ExhibitsListFragmentDirections private constructor() {
  private data class ActionExhibitsListFragmentToExhibitDetailsFragment(
    public val exhibitId: String
  ) : NavDirections {
    public override val actionId: Int = R.id.action_exhibitsListFragment_to_exhibitDetailsFragment

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("exhibitId", this.exhibitId)
        return result
      }
  }

  public companion object {
    public fun actionExhibitsListFragmentToExhibitDetailsFragment(exhibitId: String): NavDirections
        = ActionExhibitsListFragmentToExhibitDetailsFragment(exhibitId)
  }
}
