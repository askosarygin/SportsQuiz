package com.example.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

open class SportsQuizTwoButtonsDialogFragment(
    private val manager: FragmentManager,
    private val dialogTag: String = "",
    @StringRes private val title: Int = 0,
    private val message: String = "",
    private val backPressedCancelable: Boolean = false,
    private val onTouchOutsideCancelable: Boolean = false,
    @StringRes private val positiveButtonText: Int = 0,
    private val positiveButtonAction: (DialogInterface, Int) -> Unit = { _, _ -> },
    @StringRes private val negativeButtonText: Int = 0,
    private val negativeButtonAction: (DialogInterface, Int) -> Unit = { _, _ -> }
) : DialogFragment() {

    fun showDialog() {
        this.show(manager, dialogTag)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = backPressedCancelable
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText) { dialog, id ->
                    positiveButtonAction(dialog, id)
                }
                .setNegativeButton(negativeButtonText) { dialog, id ->
                    negativeButtonAction(dialog, id)
                }
                .create()
                .apply {
                    setCanceledOnTouchOutside(onTouchOutsideCancelable)
                }
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}