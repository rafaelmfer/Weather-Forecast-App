package com.rafaelmfer.weatherforecast.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafaelmfer.weatherforecast.databinding.ItemCitiesBinding
import com.rafaelmfer.weatherforecast.domain.model.SearchAutoCompleteModelItem
import com.rafaelmfer.weatherforecast.extensions.addRippleEffect
import com.rafaelmfer.weatherforecast.extensions.onSingleClick
import com.rafaelmfer.weatherforecast.extensions.sectionTextBold
import com.rafaelmfer.weatherforecast.extensions.toSpannableStringBuilder

class CitiesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val cityList: MutableList<SearchAutoCompleteModelItem> = mutableListOf()
    private var cityListener: (city: String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CitiesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CitiesViewHolder)?.bind(cityList[position], cityListener)
    }

    override fun getItemCount(): Int = cityList.size

    class CitiesViewHolder(private val binding: ItemCitiesBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CitiesViewHolder {
                val view = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return CitiesViewHolder(view)
            }
        }

        fun bind(item: SearchAutoCompleteModelItem, cityListener: (city: String) -> Unit) {
            binding.apply {
                tvCityNameAndRegion.text = "${item.name} - ${item.region}".toSpannableStringBuilder().sectionTextBold(item.name)
                root.apply {
                    addRippleEffect(android.R.color.transparent)
                    onSingleClick {
                        cityListener.invoke(item.name)
                    }
                }
            }
        }
    }

    class CitiesDiffUtil(
        private val oldList: List<SearchAutoCompleteModelItem>,
        private val newList: List<SearchAutoCompleteModelItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].name == newList[newItemPosition].name &&
                    oldList[oldItemPosition].region == newList[newItemPosition].region)
        }
    }

    fun updateCityList(newCityList: List<SearchAutoCompleteModelItem>) {
        val diffUtilCallback = CitiesDiffUtil(
            oldList = cityList,
            newList = newCityList
        )
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

        cityList.clear()
        cityList.addAll(newCityList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setActionListener(action: (city: String) -> Unit) {
        cityListener = action
    }
}