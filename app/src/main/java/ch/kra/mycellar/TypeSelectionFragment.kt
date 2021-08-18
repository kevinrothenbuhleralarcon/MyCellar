package ch.kra.mycellar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ch.kra.mycellar.databinding.FragmentTypeSelectionBinding

class TypeSelectionFragment: Fragment() {
    private var _binding: FragmentTypeSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentTypeSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerTypeSelection.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerTypeSelection.adapter = TypeSelectionAdapter(this::navigateToWineList, WineType.values())
        binding.recyclerTypeSelection.setHasFixedSize(true)
    }

    private fun navigateToWineList(type: WineType) {
        val action = TypeSelectionFragmentDirections.actionTypeSelectionFragmentToWineListFragment(type.strName)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}