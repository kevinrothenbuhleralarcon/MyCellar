package ch.kra.mycellar

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.databinding.FragmentWineListBinding
import ch.kra.mycellar.viewmodel.WineViewModel

class WineListFragment : Fragment() {

    private var _binding: FragmentWineListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WineViewModel by activityViewModels {
        WineViewModel.WineViewModelFactory((activity?.application as WineApplication).database.wineDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWineListBinding.inflate(inflater, container, false)
        //wineType = navigationArgs.wineType
        changeTitle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatBtnAddWine.setOnClickListener {
            val action = WineListFragmentDirections.actionWineListFragmentToItemDetailsFragment(wineId = 0)
            findNavController().navigate(action)
        }
        val recyclerView = binding.recyclerWineList
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        val adapter = WineListAdapter(this::goToWineDetails, this::changeQuantity)
        recyclerView.adapter = adapter
        viewModel.listWine.observe(this.viewLifecycleOwner) { wines ->
            wines.let {
                adapter.submitList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        enumValues<WineType>().forEach { menu.add(it.strName) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId != android.R.id.home) {
            Log.d("wineType", "Wine type: ${item.title}")
            viewModel.changeWineType(item.title.toString())
            changeTitle()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    fun goToWineDetails(wine: Wine) {
        val action = WineListFragmentDirections.actionWineListFragmentToItemDetailsFragment(wineId = wine.id)
        findNavController().navigate(action)
    }

    fun changeQuantity(wine: Wine, add: Boolean) {
        viewModel.changeQuantity(wine, add)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun changeTitle()
    {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = viewModel.wineType.value
    }
}