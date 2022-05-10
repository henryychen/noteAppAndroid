package com.example.startingtemplate

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class EditScreen : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // layout is defined in "res/layouts/fragment_2.xml"
        val root = inflater.inflate(R.layout.edit_screen, container, false)

        // add UI handlers that call your viewmodel here
        var header = root.findViewById<TextView>(R.id.es_textView1)
        var title = root.findViewById<TextView>(R.id.es_textView2)
        var body = root.findViewById<TextView>(R.id.es_textView3)
        var important = root.findViewById<Switch>(R.id.es_toggle)

        if (viewModel.currentNote.value != null) {
            header.text = "Edit Note #${viewModel.currentNote.value?.id}"
            title.text = viewModel.currentNote.value?.title?.value
            body.text = viewModel.currentNote.value?.body?.value
            if (viewModel.currentNote.value?.important?.value == true) {
                important.isChecked = true
            }
        }

        val saveBtn = root.findViewById<Button>(R.id.es_button)
        saveBtn.setOnClickListener {
            val title1 = title.text
            val body1 = body.text
            val important1 = important.isChecked
            if (viewModel.currentNote.value == null) {
                findNavController().popBackStack(R.id.fragment1, false)
                viewModel.addNote(title1.toString(), body1.toString(), important1)
            } else {
                findNavController().popBackStack(R.id.fragment2, false)
                viewModel.updateNote(viewModel.currentNote.value!!.id, title1.toString(), body1.toString(), important1)
            }
        }

        return root
    }
}