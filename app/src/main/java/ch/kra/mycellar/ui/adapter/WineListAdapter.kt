package ch.kra.mycellar.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.databinding.ListWineBinding

class WineListAdapter(
    private val onClickWine: (Wine) -> Unit,
    private val onClickQuantity: (Wine, Boolean) -> Unit
) : ListAdapter<Wine, WineListAdapter.WineListViewHolder>(DiffCallBack) {
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Wine>() {
            override fun areItemsTheSame(oldItem: Wine, newItem: Wine): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Wine, newItem: Wine): Boolean {
                return oldItem == newItem
            }
        }
    }

    class WineListViewHolder(val binding: ListWineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wine: Wine) {
            binding.apply {
                lblWineName.text = wine.wineName
                lblOfferedBy.text = wine.offeredBy
                lblQuantity.text = wine.quantity.toString()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WineListViewHolder {
        val wineListViewHolder =
            WineListViewHolder(ListWineBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        with(wineListViewHolder.binding) {
            layoutWineDetail.setOnClickListener { onClickWine(getItem(wineListViewHolder.adapterPosition)) }
            btnPlus.setOnClickListener { onClickQuantity(getItem(wineListViewHolder.adapterPosition), true) }
            btnMinus.setOnClickListener { onClickQuantity(getItem(wineListViewHolder.adapterPosition), false) }
        }

        return wineListViewHolder
    }

    override fun onBindViewHolder(holder: WineListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}