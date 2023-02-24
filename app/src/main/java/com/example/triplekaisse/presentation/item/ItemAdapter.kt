package com.example.triplekaisse.presentation.item

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.triplekaisse.R
import com.example.triplekaisse.domain.model.Item
import java.util.*
import kotlin.collections.ArrayList

class ItemAdapter(
    var items: List<Item>,
    var allItems: List<Item>,
    private val onUpdateClickListener: OnUpdateClickListener
) : ListAdapter<Item, ItemViewHolder>(ItemDiffUtil()), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_item, parent, false)
        return ItemViewHolder(view, onUpdateClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                items = results.values as List<Item> // has the filtered values
                notifyDataSetChanged() // notifies the data with new filtered values
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var constraint: CharSequence? = constraint
                val results = FilterResults() // Holds the results of a filtering operation in values
                val FilteredArrList: ArrayList<Item> = ArrayList()

                if (constraint == null || constraint.length == 0) {

                    results.count = allItems.size
                    results.values = allItems
                } else {
                    constraint = constraint.toString().toLowerCase()
                    for (item in allItems) {
                        val data: String = item.description
                        if (data.lowercase(Locale.getDefault()).contains(constraint.toString())) {
                            FilteredArrList.add(
                                item
                            )
                        }
                    }
                    results.count = FilteredArrList.size
                    results.values = FilteredArrList
                }
                return results
            }
        }
    }
}

class ItemDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = false

    override fun areContentsTheSame(oldItem: Item, newItem: Item) = false
}

typealias OnUpdateClickListener = (Item) -> Unit