package com.example.evidenta_masina.ui.dashboard

import android.content.ContentValues
import android.os.Bundle;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import android.widget.EditText
import android.widget.TextView;
import android.widget.Toast
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.evidenta_masina.ApiInterface
import com.example.evidenta_masina.Data_ma
import com.example.evidenta_masina.R
import com.example.evidenta_masina.databinding.FragmentDashboardBinding
import com.example.evidenta_masina.databinding.FragmentHomeBinding;
import com.example.evidenta_masina.databinding.FragmentNotificationsBinding
import com.example.evidenta_masina.ui.home.HomeViewModel
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Array


const val BASE_URL = "http://10.0.2.2:8000/"
/**
 * clasa corespunzatoare fragmentului pentru masina
 */
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    var date = String()
    var datatext:TextView?= null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    /**
     * metoda pentru crearea propriu-zisa a fragmentului
     * @return root
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var serie = root.findViewById<EditText>(R.id.names)
        var idProprietar = root.findViewById<EditText>(R.id.names1)
        var marca = root.findViewById<EditText>(R.id.names2)
        var model = root.findViewById<EditText>(R.id.names3)
        var an_fabricatie = root.findViewById<EditText>(R.id.names4)
        var culoare = root.findViewById<EditText>(R.id.names5)
        var motor = root.findViewById<EditText>(R.id.names6)
        var combustibil = root.findViewById<EditText>(R.id.names7)
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        datatext = root.findViewById(R.id.date) as TextView
        val addButton : Button = root.findViewById(R.id.button)
        addButton.setOnClickListener{
            Toast.makeText(view?.context, "Doresti sa adaugi o masina", Toast.LENGTH_LONG).show()
            val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

            val client = OkHttpClient.Builder()
                .connectionSpecs(specs)
                .build()

            val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
                .baseUrl(com.example.evidenta_masina.ui.home.BASE_URL).build().create(ApiInterface::class.java)
            val retrofitData = retrofitBuilder.createMa(post = Data_ma(
                serie.text.toString(),0,marca.text.toString(),model.text.toString(),
                Integer.parseInt(an_fabricatie.text.toString()),culoare.text.toString(),
                Integer.parseInt(motor.text.toString()),combustibil.text.toString()
            )
            )
            retrofitData.enqueue(object : Callback<Data_ma?> {
                override fun onResponse(
                    call: Call<Data_ma?>,
                    response: Response<Data_ma?>
                ) {
                    val responseBody = response.body()!!
                    Toast.makeText(view?.context, "Masina adaugata", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<Data_ma?>, t: Throwable) {
                    Toast.makeText(view?.context, "Masina NU a fost adaugata", Toast.LENGTH_LONG).show()
                    Log.d(ContentValues.TAG,"onFailure: " +t.message)
                }
            })
        }
        val deleteButton : Button = root.findViewById(R.id.button2)
        deleteButton.setOnClickListener{
            val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

            val client = OkHttpClient.Builder()
                .connectionSpecs(specs)
                .build()

            val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
                .baseUrl(com.example.evidenta_masina.ui.home.BASE_URL).build().create(ApiInterface::class.java)
            val retrofitData = retrofitBuilder.deleteMa(serie = serie.text.toString())

            retrofitData.enqueue(object : Callback<Unit?> {
                override fun onResponse(
                    call: Call<Unit?>,
                    response: Response<Unit?>
                ) {
                    //val responseBody = response.body()!!

                }

                override fun onFailure(call: Call<Unit?>, t: Throwable) {
                    Log.d(ContentValues.TAG,"onFailure: " +t.message)
                }
            })
        }
        val getButton : Button = root.findViewById(R.id.button3)
        getButton.setOnClickListener{
            Toast.makeText(view?.context, "Doresti sa gasesti o masina", Toast.LENGTH_LONG).show()
            getMyData()
            dashboardViewModel.text.observe(viewLifecycleOwner) {
            }
        }
        return root
    }
    /**
     * metoda pentru realizarea interogarii pentru a extrage toate datele din bazele de date
     */
    private fun getMyData() {
        val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

        val client = OkHttpClient.Builder()
            .connectionSpecs(specs)
            .build()

        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
            .baseUrl(com.example.evidenta_masina.ui.home.BASE_URL).build().create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData_ma()

        retrofitData.enqueue(object : Callback<List<Data_ma>?> {
            override fun onResponse(
                call: Call<List<Data_ma>?>,
                response: Response<List<Data_ma>?>
            ){
                val responseBody = response.body()!!
                var date : String? = ""
                for(Data_ma in responseBody)
                {
                    Log.d("Mes", "aa"+ Data_ma.toString())
                    date = date + Data_ma.toString()
                }
                datatext?.setText(date)
            }

            override fun onFailure(call: Call<List<Data_ma>?>, t: Throwable) {
                Log.d(ContentValues.TAG,"onFailure: " +t.message)
            }
        })
    }

    /**
     * metoda pentru distrugerea fragmentului
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}