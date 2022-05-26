package com.example.evidenta_masina.ui.notifications;

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
import com.example.evidenta_masina.*
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

const val BASE_URL = "http://10.0.2.2:8000/"
/**
 * clasa corespunzatoare fragmentului pentru acte
 */
class NotificationsFragment : Fragment() {
private var _binding: FragmentNotificationsBinding? = null
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
        val homeViewModel =
        ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        homeViewModel.text.observe(viewLifecycleOwner) {
        textView.text = it
        }
                datatext = root.findViewById(R.id.date) as TextView
                var tipAct = root.findViewById<EditText>(R.id.names)
                var serie = root.findViewById<EditText>(R.id.names1)
                var valabil_de_la = root.findViewById<EditText>(R.id.names2)
                var valabil_pana_la = root.findViewById<EditText>(R.id.names3)
                var pret = root.findViewById<EditText>(R.id.names4)
                var idAct = root.findViewById<EditText>(R.id.names5)
                val addButton : Button = root.findViewById(R.id.button)
                addButton.setOnClickListener{
                        Toast.makeText(view?.context, "Doresti sa adaugi un act", Toast.LENGTH_LONG).show()
                        val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

                        val client = OkHttpClient.Builder()
                                .connectionSpecs(specs)
                                .build()

                        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
                                .baseUrl(com.example.evidenta_masina.ui.home.BASE_URL).build().create(ApiInterface::class.java)
                        val retrofitData = retrofitBuilder.createAct(post = Data_act
                                (tipAct.text.toString(),serie.text.toString(),valabil_de_la.text.toString(),
                                valabil_pana_la.text.toString(),(pret.text.toString()).toDouble())
                        )
                        retrofitData.enqueue(object : Callback<Data_act?> {
                                override fun onResponse(
                                        call: Call<Data_act?>,
                                        response: Response<Data_act?>
                                ) {
                                        val responseBody = response.body()!!
                                        Toast.makeText(view?.context, "Masina adaugata", Toast.LENGTH_LONG).show()
                                }

                                override fun onFailure(call: Call<Data_act?>, t: Throwable) {
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
                        val retrofitData = retrofitBuilder.deleteAct(idAct = Integer.parseInt(idAct.text.toString()))

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
                        Toast.makeText(view?.context, "Doresti sa gasesti un act", Toast.LENGTH_LONG).show()
                        getMyData()
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
                        .baseUrl(com.example.evidenta_masina.ui.home.BASE_URL).build().create(
                                ApiInterface::class.java)
                val retrofitData = retrofitBuilder.getData_act()

                retrofitData.enqueue(object : Callback<List<Data_act>> {
                        override fun onResponse(
                                call: Call<List<Data_act>?>,
                                response: Response<List<Data_act>?>
                        ) {
                                val responseBody = response.body()!!
                                var date : String? = ""
                                for(Data_act in responseBody)
                                {
                                        Log.d("Mes", "aa"+ Data_act.toString())
                                        date = date + Data_act.toString()
                                }
                                datatext?.setText(date)
                        }

                        override fun onFailure(call: Call<List<Data_act>?>, t: Throwable) {
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

