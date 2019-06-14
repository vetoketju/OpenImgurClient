package com.villevalta.imgur.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.villevalta.imgur.R
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Section
import com.villevalta.imgur.model.Sort
import com.villevalta.imgur.model.Window

class ListFilterEditDialog : DialogFragment() {

  var old: ListFilter? = null
  var listener: ListFilterEditDialogListener? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    old = arguments?.getParcelable(KEY_LIST_FILTER)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder = AlertDialog.Builder(requireActivity())

    val contentView =
      requireActivity().layoutInflater.inflate(R.layout.dialog_edit_listfilter, null)

    val spinnerSection =
      contentView.findViewById<Spinner>(R.id.dialog_edit_listfilter_section_spinner)
    val spinnerWindow =
      contentView.findViewById<Spinner>(R.id.dialog_edit_listfilter_window_spinner)
    val spinnerSort = contentView.findViewById<Spinner>(R.id.dialog_edit_listfilter_sort_spinner)
    val switchMature = contentView.findViewById<Switch>(R.id.dialog_edit_listfilter_mature_switch)

    old?.let {
      spinnerSection.setSelection(it.section?.ordinal?.plus(1) ?: 0)
      spinnerWindow.setSelection(it.window?.ordinal?.plus(1) ?: 0)
      spinnerSort.setSelection(it.sort?.ordinal?.plus(1) ?: 0)
      switchMature.isChecked = old?.mature ?: false
    }

    builder.setView(contentView)
      .setPositiveButton("Set") { dialog, id ->

        val newFilter = ListFilter(
          section = when (val pos = spinnerSection.selectedItemPosition) {
            0 -> null; else -> Section.values()[pos - 1]
          },
          window = when (val pos = spinnerWindow.selectedItemPosition) {
            0 -> null; else -> Window.values()[pos - 1]
          },
          sort = when (val pos = spinnerSort.selectedItemPosition) {
            0 -> null; else -> Sort.values()[pos - 1]
          },
          showViral = old?.showViral,
          mature = switchMature.isChecked,
          albumPreviews = old?.albumPreviews
        )

        if (newFilter != old) {
          listener?.onListFilterApply(newFilter)
        }
      }
      .setNegativeButton(requireActivity().getString(R.string.cancel)) { dialog, id ->
        dialog.cancel()
      }
    return builder.create()
  }

  companion object {
    private const val KEY_LIST_FILTER = "listfilter"

    fun newInstance(filter: ListFilter?): ListFilterEditDialog {
      return ListFilterEditDialog().apply {
        arguments = bundleOf(
          KEY_LIST_FILTER to filter
        )
      }
    }

  }

}

interface ListFilterEditDialogListener {
  fun onListFilterApply(newFilter: ListFilter)
}