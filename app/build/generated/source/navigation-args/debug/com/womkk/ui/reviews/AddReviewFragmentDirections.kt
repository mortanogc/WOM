package com.womkk.ui.reviews

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.womkk.R

public class AddReviewFragmentDirections private constructor() {
  public companion object {
    public fun actionAddReviewFragmentToReviewListFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_addReviewFragment_to_reviewListFragment)
  }
}
