package com.example.evidenta_masina.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.evidenta_masina.ApiInterface
import com.example.evidenta_masina.Data_pr
import com.example.evidenta_masina.R
import com.example.evidenta_masina.databinding.FragmentHomeBinding
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "http://10.0.2.2:8000/"
/**
 * clasa corespunzatoare fragmentului pentru proprietar
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var datatext:TextView?= null

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        datatext = root.findViewById(R.id.date) as TextView
        val nume = root.findViewById<EditText>(R.id.names)
        val cnp = root.findViewById<EditText>(R.id.names1)
        val adresa = root.findViewById<EditText>(R.id.names2)
        val nrTelefon = root.findViewById<EditText>(R.id.names3)
        val email = root.findViewById<EditText>(R.id.names4)
        val tip = root.findViewById<EditText>(R.id.names5)
        val idProprietar = root.findViewById<EditText>(R.id.names6)
        val addButton : Button = root.findViewById(R.id.button)
        addButton.setOnClickListener{
            println( "Am citit datele : ")
            println( nume.text.toString())
            println( cnp.text.toString())
            println( adresa.text.toString())
            println( nrTelefon.text.toString())
            println( email.text.toString())
            println( tip.text.toString())

                val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

                val client = OkHttpClient.Builder()
                    .connectionSpecs(specs)
                    .build()

                val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
                    .baseUrl(BASE_URL).build().create(ApiInterface::class.java)
                val retrofitData = retrofitBuilder.createPr(post = Data_pr(adresa.text.toString(),cnp.text.toString(),email.text.toString(),28,nrTelefon.text.toString(),nume.text.toString()))

                retrofitData.enqueue(object : Callback<Data_pr?> {
                    override fun onResponse(
                        call: Call<Data_pr?>,
                        response: Response<Data_pr?>
                    ) {
                        val responseBody = response.body()!!
                        Toast.makeText(view?.context, "Proprietar adaugat", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Data_pr?>, t: Throwable) {
                        Toast.makeText(view?.context, "Proprietar NU a fost adaugat", Toast.LENGTH_LONG).show()
                        Log.d(ContentValues.TAG,"onFailure: " +t.message)
                    }
                })

        }
        val deleteButton : Button = root.findViewById(R.id.button2)
        deleteButton.setOnClickListener{
            Toast.makeText(view?.context, "Doresti sa stergi un proprietar", Toast.LENGTH_LONG).show()
            val specs = listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)

            val client = OkHttpClient.Builder()
                .connectionSpecs(specs)
                .build()

            val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
                .baseUrl(BASE_URL).build().create(ApiInterface::class.java)
            val retrofitData = retrofitBuilder.deletePr(idProprietar = Integer.parseInt(idProprietar.text.toString()))

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
            Toast.makeText(view?.context, "Doresti sa gasesti un proprietar", Toast.LENGTH_LONG).show()
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
            .baseUrl(BASE_URL).build().create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData_pr()

        retrofitData.enqueue(object : Callback<List<Data_pr>?> {
            override fun onResponse(
                call: Call<List<Data_pr>?>,
                response: Response<List<Data_pr>?>
            ) {
                val responseBody = response.body()!!
                var date : String? = ""
                for(Data_pr in responseBody)
                {
                    Log.d("Mes", "aa"+ Data_pr.toString())
                    date = date + Data_pr.toString()
                }
                datatext?.setText(date)
            }

            override fun onFailure(call: Call<List<Data_pr>?>, t: Throwable) {
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