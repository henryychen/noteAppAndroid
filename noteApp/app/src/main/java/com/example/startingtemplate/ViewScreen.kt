package com.example.startingtemplate

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip

class ViewScreen : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // layout is defined in "res/layouts/view_screen_fragment.xml"
        val root = inflater.inflate(R.layout.view_screen, container, false)

        // add UI handlers that call your viewmodel here
        var header = root.findViewById<TextView>(R.id.vs_textView1)
        header.text = "Note #${viewModel.currentNote.value?.id}"
        var title = root.findViewById<TextView>(R.id.vs_textView2)
        title.text = viewModel.currentNote.value?.title?.value
        var body = root.findViewById<TextView>(R.id.vs_textView3)
        body.text = viewModel.currentNote.value?.body?.value
        var important = root.findViewById<Chip>(R.id.vs_chip)
        if (viewModel.currentNote.value?.important?.value == false) {
            important.isInvisible = true
        }

        var editBtn = root.findViewById<Button>(R.id.vs_button)
        editBtn.setOnClickListener {
            findNavController().navigate(R.id.action_f2_to_f3)
        }

        // observe viewModel properties here

        return root
    }
}