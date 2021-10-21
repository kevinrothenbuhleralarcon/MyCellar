package ch.kra.mycellar.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ch.kra.mycellar.R
import ch.kra.mycellar.WineApplication
import ch.kra.mycellar.WineType
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.databinding.FragmentItemDetailsBinding
import ch.kra.mycellar.other.CellarUtility
import ch.kra.mycellar.ui.viewmodel.WineViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemDetailsFragment : Fragment() {
    private val navigationArgs: ItemDetailsFragmentArgs by navArgs()

    private var _binding: FragmentItemDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WineViewModel by activityViewModels {
        WineViewModel.WineViewModelFactory((activity?.application as WineApplication).database.wineDao())
    }

    private lateinit var wine: Wine

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set the value of the dropdownList
        val items = mutableListOf<String>()
        enumValues<WineType>().forEach { items.add(getString(it.resId)) }
        items.removeAt(items.lastIndex) //remove the last item of the list (ALL) because it's not a wine type, it's used for the wine type selection
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.dropDownType.setAdapter(adapter)

        val wineId = navigationArgs.wineId
        if (wineId > 0) {
            viewModel.getWine(wineId).observe(this.viewLifecycleOwner) { selectedWine ->
                wine = selectedWine
                bind(wine)
            }
            binding.btnSave.setOnClickListener { updateWine() }
            binding.btnDelete.setOnClickListener { showDialogConfirmation() }
        } else {
            binding.btnSave.setOnClickListener { addWine() }
            binding.btnDelete.isGone = true
        }
    }

    private fun bind(wine: Wine) {
        binding.apply {
            textBoxName.setText(wine.wineName)
            textBoxOfferedBy.setText(wine.offeredBy)
            textBoxQuantity.setText(wine.quantity.toString())
            dropDownType.setText(getString(wine.wineType), false)
        }
    }

    private fun addWine() {
        with(binding) {
            if (viewModel.isEntryValid(
                    textBoxName.text.toString(),
                    dropDownType.text.toString(),
                    textBoxQuantity.text.toString()
                )) {
                viewModel.addWine(
                    textBoxName.text.toString(),
                    CellarUtility.getWineTypeFromString(requireContext(), binding.dropDownType.text.toString()).resId,
                    textBoxQuantity.text.toString(),
                    textBoxOfferedBy.text.toString()
                )
            }
        }
        val action = ItemDetailsFragmentDirections.actionItemDetailsFragmentToWineListFragment()
        findNavController().navigate(action)
    }

    private fun showDialogConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_confirmation_title))
            .setMessage(getString(R.string.dialog_confirmation_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.dialog_confirmation_positive)) { _, _ -> deleteWine() }
            .setNegativeButton(getString(R.string.dialog_confirmation_negative)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun deleteWine() {
        viewModel.deleteWine(wine)
        val action = ItemDetailsFragmentDirections.actionItemDetailsFragmentToWineListFragment()
        findNavController().navigate(action)
    }

    private fun updateWine() {
        if (isEntryValid()) {
            viewModel.updateWine(
                wine.id,
                binding.textBoxName.text.toString(),
                CellarUtility.getWineTypeFromString(requireContext(), binding.dropDownType.text.toString()).resId,
                binding.textBoxQuantity.text.toString(),
                binding.textBoxOfferedBy.text.toString()
            )
        }
        val action = ItemDetailsFragmentDirections.actionItemDetailsFragmentToWineListFragment()
        findNavController().navigate(action)
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.textBoxName.text.toString(),
            binding.dropDownType.text.toString(),
            binding.textBoxQuantity.text.toString()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}