package com.example.testingapp.adapter

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingapp.R
import com.example.testingapp.models.Food

class RecyclerViewAdapter(private val mList: ArrayList<Food>?) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var checkBoxStatus = SparseBooleanArray(mList!!.size)

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            val foodPlate = mList?.get(position)

            checkBox.isChecked = checkBoxStatus.get(position, false)

            // sets the image to the imageview from our itemHolder class
            if (foodPlate != null) {
                when (foodPlate.type) {
                    "pizza" -> {
                        foodPlate.price = getPrice(foodPlate)
                        typeText.text = foodPlate.type
                        priceText.text = "€${foodPlate.price}"
                        ingTopText.text = foodPlate.toppings.toString()
                        sauceText.text =
                            if (foodPlate.sauce != null) foodPlate.sauce.toString() else "Sin salsa"
                        siLeText.text = foodPlate.size

                        staticSauceText.visibility = View.VISIBLE
                        sauceText.visibility = View.VISIBLE
                    }

                    "salad" -> {
                        foodPlate.price = getPrice(foodPlate)
                        typeText.text = foodPlate.type
                        siLeText.text = foodPlate.name
                        priceText.text = "€${foodPlate.price}"
                        ingTopText.text = foodPlate.ingredients.toString()
                    }

                    "bread" -> {
                        foodPlate.price = getPrice(foodPlate)
                        typeText.text = foodPlate.type
                        priceText.text = "€${foodPlate.price}"
                        siLeText.text = foodPlate.shape
                        staticIngText.text = "grain"
                        ingTopText.text = foodPlate.grain
                    }

                    "sausage" -> {
                        foodPlate.price = getPrice(foodPlate)
                        typeText.text = foodPlate.type
                        priceText.text = "€${foodPlate.price}"
                        siLeText.text = foodPlate.length
                        staticIngText.text = "meat"
                        ingTopText.text = foodPlate.meat
                    }
                }
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val typeText: TextView = itemView.findViewById(R.id.textType)
        val ingTopText: TextView = itemView.findViewById(R.id.textTopIng)
        val staticIngText: TextView = itemView.findViewById(R.id.stIngTop)
        val sauceText: TextView = itemView.findViewById(R.id.textSalsa)
        val staticSauceText: TextView = itemView.findViewById(R.id.stSalsa)
        val siLeText: TextView = itemView.findViewById(R.id.textSiLe)
        val priceText: TextView = itemView.findViewById(R.id.textPrice)

        val checkBox: CheckBox = itemView.findViewById(R.id.checkFood)

        init {
            checkBox.setOnClickListener {
                Log.d("Adapteru absolute", absoluteAdapterPosition.toString())
                Log.d("Adapteru boolean", checkBoxStatus.toString())

                if(!checkBoxStatus.get(absoluteAdapterPosition, false)){
                    checkBox.isChecked =  true
                    checkBoxStatus.put(absoluteAdapterPosition, true)
                } else {
                    checkBox.isChecked =  false
                    checkBoxStatus.put(absoluteAdapterPosition, false)
                }
            }
        }
    }

    private fun getPrice(food: Food): Double{

        var price = 0.0
        food.apply {
            when(type) {
                "pizza" -> {
                    when(size){
                        "small" -> price = 5.00
                        "medium" -> price = 10.00
                        "big" -> price = 15.00
                    }
                    for(i in 0 until toppings!!.size){
                        price += 1.00
                    }
                    if(sauce != null){
                        price += 5.00
                    }
                }

                "bread" -> { price = 3.00 }

                "salad" -> {
                    if(ingredients!!.size <= 2){
                        price = 2.00
                    } else {
                        price = 2.00
                        for(i in 2 until ingredients.size){
                            price += 0.50
                        }
                    }
                }

                "sausage" -> {
                    price = if(length.equals("5")) { 1.50 } else { 3.00 }
                }
            }
        }
        return price
    }

    fun getTotalChecked(): Double{
        var total = 0.00

        for(index in 0 until mList!!.size){
            if(checkBoxStatus[index]){
                total += mList[index].price
            }
        }
        return total
    }

}