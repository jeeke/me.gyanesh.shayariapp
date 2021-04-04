package me.gyanesh.shayariapp.ui.categoryDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nblik.app.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_category_detail.*
import me.gyanesh.shayariapp.R
import me.gyanesh.shayariapp.data.FirebaseDataProvider
import org.kodein.di.direct
import org.kodein.di.generic.instance


class CategoryDetailFragment : BaseFragment() {

    private val dataProvider: FirebaseDataProvider by lazy { kodein.direct.instance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_category_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args by navArgs<CategoryDetailFragmentArgs>()
        val category = args.category
        toolbar.title = category
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val shayariAdapter = ShayariAdapter()
        dataProvider.getShayariByCategory(category)
            .observe(viewLifecycleOwner, {
                shayariAdapter.submitList(it)
            })

        recyclerView.apply {
            this.adapter = shayariAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }
}