package hr.foi.rampu.fridgium.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.helpers.MockDataLoader

class FridgeFragment : Fragment() {

    private val probneNamirnice = MockDataLoader.DajProbnePodatke()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        probneNamirnice.forEach { Log.i("PROBNA NAMIRNICA", it.naziv) }
        return inflater.inflate(R.layout.fragment_fridge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_namirnice_hladnjaka)
    }
}