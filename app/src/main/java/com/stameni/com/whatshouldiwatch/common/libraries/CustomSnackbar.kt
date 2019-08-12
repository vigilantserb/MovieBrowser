package com.stameni.com.whatshouldiwatch.common.libraries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.findSuitableParent

class CustomSnackbar(
    parent: ViewGroup,
    content: CustomSnackbarView
) : BaseTransientBottomBar<CustomSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(view: View): CustomSnackbar {
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_custom_snackbar,
                parent,
                false
            ) as CustomSnackbarView

            return CustomSnackbar(
                parent,
                customView
            )
        }

    }

}