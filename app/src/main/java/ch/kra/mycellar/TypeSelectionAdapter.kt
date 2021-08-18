package ch.kra.mycellar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ch.kra.mycellar.databinding.ListTypeBinding

class TypeSelectionAdapter(private val onClick: (WineType) -> Unit, private val dataset: Array<WineType>):
    RecyclerView.Adapter<TypeSelectionAdapter.TypeSelectionViewHolder>() {

    class TypeSelectionViewHolder(private val binding: ListTypeBinding): RecyclerView.ViewHolder(binding.root) {
        var type: WineType = WineType.RED_WINE
        fun bind(value: WineType) {
            binding.btnType.text = value.strName
            type = value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeSelectionViewHolder {
        val typeSelectionViewHolder = TypeSelectionViewHolder(ListTypeBinding.inflate(LayoutInflater.from(parent.context)))
        typeSelectionViewHolder.itemView.findViewById<Button>(R.id.btn_type).setOnClickListener { onClick(typeSelectionViewHolder.type) }
        return typeSelectionViewHolder
    }

    override fun onBindViewHolder(holder: TypeSelectionViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}