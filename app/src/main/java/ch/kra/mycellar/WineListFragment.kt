package ch.kra.mycellar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.databinding.FragmentWineListBinding
import ch.kra.mycellar.viewmodel.WineViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WineListFragment : Fragment() {

    private val navigationArgs: WineListFragmentArgs by navArgs()
    private var _binding: FragmentWineListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WineViewModel by activityViewModels {
        WineViewModel.WineViewModelFactory((activity?.application as WineApplication).database.wineDao())
    }

    private lateinit var wineType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWineListBinding.inflate(inflater, container, false)
        wineType = navigationArgs.wineType
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatBtnAddWine.setOnClickListener {
            val action = WineListFragmentDirections.actionWineListFragmentToItemDetailsFragment(wineType = wineType, wineId = 0)
            findNavController().navigate(action)
        }
        val recyclerView = binding.recyclerWineList
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        val adapter = WineListAdapter(this::goToWineDetails, this::addWineBottle, this::substractWineBottle)
        recyclerView.adapter = adapter
        lifecycle.coroutineScope.launch{
            viewModel.getList(wineType).collect() {
                adapter.submitList(it)
            }
        }
    }

    fun goToWineDetails(wine: Wine) {
        val action = WineListFragmentDirections.actionWineListFragmentToItemDetailsFragment(wineType = wineType, wineId = wine.id)
        findNavController().navigate(action)
    }

    fun addWineBottle(wine: Wine) {

    }

    fun substractWineBottle(wine: Wine) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}