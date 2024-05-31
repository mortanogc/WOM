package com.womkk.ui.reviews

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.womkk.R

public class ReviewListFragmentDirections private constructor() {
  public companion object {
    public fun actionReviewListFragmentToAddReviewFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_reviewListFragment_to_addReviewFragment)
  }
}
