package com.example.leagueoflegendsapk.ui.patch_notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.leagueoflegendsapk.R


class PatchNotesFragment : Fragment() {

    companion object {
        fun newInstance() = PatchNotesFragment()
    }

    private lateinit var viewModel: PatchNotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patch_notes, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        view.findViewById<WebView>(R.id.webPatchNotes).loadUrl("https://www.leagueoflegends.com/es-mx/news/game-updates/notas-de-la-version-12-12/");

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PatchNotesViewModel::class.java)
    }

}