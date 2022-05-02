package com.example.testingapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testingapp.adapter.RecyclerViewAdapter
import com.example.testingapp.api.FoodApiCheck
import com.example.testingapp.databinding.FragmentPreviewBinding
import com.example.testingapp.models.ExchangeRate
import com.example.testingapp.models.Food
import com.example.testingapp.models.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DecimalFormat

class PreviewFragment: Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val foodApiCheck = retrofit.create(FoodApiCheck::class.java)

    var foodOrder: ArrayList<Food>? = null
    private var exchanges: ExchangeRate? = null
    private var adapter: RecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)

        val orData = foodApiCheck.order
        orData.enqueue(object: Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if(response.code() != 200){
                    Log.d("Error ${response.code()}: ", "Error. Checar URL")
                    return
                }

                foodOrder = response.body()!!.order
                Log.d("JSIN inside callback", response.body().toString())
                adapter = RecyclerViewAdapter(foodOrder)
                binding.recyclerFood.adapter = adapter
                binding.recyclerFood.layoutManager = LinearLayoutManager(activity?.applicationContext)

            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        getExchangeRates()

        return binding.root
    }

    override fun onResume() {
        binding.apply {
            buttonOk.setOnClickListener {
                val df = DecimalFormat("#.##")

                val totalEuros = adapter?.getTotalChecked()
                val totalUSD = df.format(totalEuros?.div(exchanges!!.USD))
                Log.d("Adapteru Total USD:", totalUSD.toString())

                binding.apply {
                    priceEURO.text = "€$totalEuros"
                    priceUSD.text = "$${totalUSD}"

                    priceUSD.visibility = View.VISIBLE
                    priceEURO.visibility = View.VISIBLE
                }
            }
        }
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getExchangeRates(){


        val exData = foodApiCheck.exchangeRate
        exData.enqueue(object: Callback<ExchangeRate>{
            override fun onResponse(call: Call<ExchangeRate>, response: Response<ExchangeRate>) {
                if(response.code() != 200){
                    Log.d("Error ${response.code()}: ", "Error. Checar URL")
                    return
                }
                exchanges = response.body()
            }

            override fun onFailure(call: Call<ExchangeRate>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        Log.d("JSIN exhange method", exchanges.toString())
    }

}