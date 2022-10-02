package uz.jahongir.data_base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.jahongir.data_base.databinding.ItemRvBinding
import uz.jahongir.data_base.models.MyContact

class MyRvAdapter(var list: List<MyContact> = emptyList(), val rvEvent: RvEvent) : RecyclerView.Adapter<MyRvAdapter.VH>() {

    inner class VH(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(myContact: MyContact, position: Int) {
            itemRvBinding.id.text = myContact.id.toString()
            itemRvBinding.name.text = myContact.name
            itemRvBinding.number.text = myContact.number
            itemRvBinding.menuImage.setOnClickListener {
                rvEvent.menuClick(myContact, itemRvBinding.menuImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}


