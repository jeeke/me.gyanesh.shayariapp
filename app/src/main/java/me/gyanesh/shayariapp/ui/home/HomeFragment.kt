package me.gyanesh.shayariapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.nblik.app.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import me.gyanesh.shayariapp.R
import me.gyanesh.shayariapp.data.FirebaseDataProvider
import me.gyanesh.shayariapp.ui.ChooseLanguageActivity
import me.gyanesh.shayariapp.util.hide
import org.kodein.di.direct
import org.kodein.di.generic.instance

class HomeFragment : BaseFragment() {

    private val dataProvider: FirebaseDataProvider by lazy { kodein.direct.instance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val communityAdapter = CategoryAdapter()
        dataProvider.getAllCategories().observe(viewLifecycleOwner, Observer {
            communityAdapter.submitList(it)
            progressBar2.hide()
        })

        recyclerView.apply {
            this.adapter = communityAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.navigation_change_lang) {
                val i = Intent(context, ChooseLanguageActivity::class.java)
                ChooseLanguageActivity.fromMainActivity = true
                startActivity(i)
            }
            true
        }

    }
}