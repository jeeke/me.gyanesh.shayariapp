package me.gyanesh.shayariapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nblik.app.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_saved.*
import me.gyanesh.shayariapp.R
import me.gyanesh.shayariapp.data.FirebaseDataProvider
import me.gyanesh.shayariapp.ui.categoryDetail.ShayariAdapter
import org.kodein.di.direct
import org.kodein.di.generic.instance


class SavedFragment : BaseFragment() {

    private val dataProvider: FirebaseDataProvider by lazy { kodein.direct.instance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val shayariAdapter = ShayariAdapter()
        dataProvider.getAllSavedShayaris()
            .observe(viewLifecycleOwner, {
                shayariAdapter.submitList(it)
            })

        recyclerView.apply {
            this.adapter = shayariAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }
}