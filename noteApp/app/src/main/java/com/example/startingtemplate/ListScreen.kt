package com.example.startingtemplate

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class ListScreen : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // layout is defined in "res/layouts/fragment_1.xml"
        val root = inflater.inflate(R.layout.list_screen, container, false)
        var importantOn = false
        var searchText = ""
        val nl = root.findViewById<LinearLayout>(R.id.noteList)

        // add UI handles for navigation here
        // ADD button
        val button1 = root.findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            viewModel.removeCurrentNote()
            findNavController().navigate(R.id.action_f1_to_f3)
        }

        // CLEAR button
        var button3 = root.findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            viewModel.clearNotes()
            populateNotes(nl, inflater, importantOn, searchText, button3)
        }

        // RANDOM button
        val button2 = root.findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            viewModel.randomNote()
            populateNotes(nl, inflater, importantOn, searchText, button3)
        }

        // Important toggle
        val importantBtn = root.findViewById<Switch>(R.id.ls_switch)
        importantBtn.setOnCheckedChangeListener { compoundButton, b ->
            importantOn = b
            populateNotes(nl, inflater, importantOn, searchText, button3)
        }

        // Search bar
        val searchBar = root.findViewById<TextInputEditText>(R.id.ls_inputText)
        searchBar.doOnTextChanged { text, start, before, count ->
            searchText = text.toString()
            populateNotes(nl, inflater, importantOn, searchText, button3)
        }

        populateNotes(nl, inflater, importantOn, searchText, button3)

        return root
    }

    private fun populateNotes(nl: LinearLayout, inflater: LayoutInflater, important: Boolean, searchText: String, btn: Button) {
        viewModel.allNotes.observe(this) { allNotes ->
            nl.removeAllViews()
            viewModel.displayedNotesRemoveAll()
            for (n in allNotes) {
                if (searchText != "" && important && (n.value?.important?.value == false
                            || n.value?.containsString(searchText) == false)) {
                    continue
                }
                if (searchText != "" && n.value?.containsString(searchText) == false) {
                    continue
                }
                if (important && n.value?.important?.value == false) {
                    continue
                }
                val view = inflater.inflate(R.layout.note_item, null)
                val title = view.findViewById<TextView>(R.id.textView3)
                val body = view.findViewById<TextView>(R.id.textView4)
                if (n.value?.important?.value == true) {
                    view.setBackgroundColor(Color.rgb(255,225,235))
                }
                title.text = n.value?.title?.value
                body.text = n.value?.body?.value
                nl.addView(view)
                viewModel.displayedNotesAdd(n)
                val button5 = view.findViewById<Button>(R.id.button5)

                // Delete
                button5.setOnClickListener {
                    n.value?.let { it1 -> viewModel.deleteNote(it1.id) }
                    nl.removeView(view)
                    viewModel.displayedNotesRemove(n)
                    btn.isEnabled = viewModel.displayedNotes.value!!.isNotEmpty()
                }

                // Open note
                view.setOnClickListener {
                    viewModel.setCurrentNote(n.value!!.id)
                    findNavController().navigate(R.id.action_f1_to_f2)
                }
            }
        }
        btn.isEnabled = viewModel.displayedNotes.value!!.isNotEmpty()
    }
}