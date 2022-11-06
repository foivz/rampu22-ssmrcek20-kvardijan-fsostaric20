package hr.foi.rampu.fridgium.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.ShoppingListaAdapter
import hr.foi.rampu.fridgium.helpers.MockDataLoader

class ShoppingListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_shopping_list)
        recyclerView.adapter = ShoppingListaAdapter(MockDataLoader.getDemoData())
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }
}