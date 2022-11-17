package hr.foi.rampu.fridgium.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.ShoppingListaAdapter
import hr.foi.rampu.fridgium.helpers.MockDataLoader

class ShoppingListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var emptyImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_shopping_list)
        emptyTextView = view.findViewById(R.id.empty_text_view)
        emptyImageView = view.findViewById(R.id.empty_image_view)
        val probniPodaci = MockDataLoader.DajProbnePodatke()
        if(probniPodaci.isEmpty()){
            recyclerView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
            emptyImageView.visibility = View.VISIBLE
        }
        else{
            recyclerView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
            emptyImageView.visibility = View.GONE
            recyclerView.adapter = ShoppingListaAdapter(MockDataLoader.DajProbnePodatke())
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            val divider = MaterialDividerItemDecoration(view.context,LinearLayoutManager.VERTICAL)
            divider.dividerInsetStart = 20
            divider.dividerInsetEnd = 20
            divider.isLastItemDecorated = false
            recyclerView.addItemDecoration(divider)
        }

    }
}