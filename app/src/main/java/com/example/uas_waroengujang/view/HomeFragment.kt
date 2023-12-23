package com.example.uas_waroengujang.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.databinding.FragmentHomeBinding
import com.example.uas_waroengujang.viewmodel.HomeViewModel
import com.example.uas_waroengujang.viewmodel.WaitressViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var waitressModel: WaitressViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        waitressModel = ViewModelProvider(requireActivity()).get(WaitressViewModel::class.java)

        val id = waitressModel.getWaitressId().value
        id?.let {
            Log.d("IDCheck", "ID: $id")
            waitressModel.fetch(id)
        }
        observeViewModel()

        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val txtTableNumber = view.findViewById<TextView>(R.id.txtTableNumber)
            var nomorTable = txtTableNumber.text.toString().toInt()
            if (nomorTable != null && nomorTable > 0 && nomorTable <=12) {
                imageView4.visibility = View.GONE
                txtTableNumber.visibility = View.GONE
                txtNumber.visibility = View.VISIBLE
                btnChange.visibility = View.VISIBLE
                btnSubmit.visibility = View.GONE


                txtNumber.text = "Table ${txtTableNumber.text}"
                txtInfo.text = "Currently Serving"

                val nomorMeja = txtTableNumber.text.toString()
                homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
                homeViewModel.setTableNumber(nomorMeja)
            }
            else {
                Toast.makeText(requireContext(), "Nomor Meja 1-12", Toast.LENGTH_SHORT).show()
            }

        }
        val btnChange = view.findViewById<Button>(R.id.btnChange)
        btnChange.setOnClickListener {
            imageView4.visibility = View.VISIBLE
            txtTableNumber.visibility = View.VISIBLE
            txtNumber.visibility = View.GONE
            btnChange.visibility = View.GONE
            btnSubmit.visibility = View.VISIBLE

            txtNumber.text = "Table"
        }
    }

    fun observeViewModel() {
        waitressModel.waitressLD.observe(viewLifecycleOwner, Observer { waitress ->
            waitress?.let {
                Log.d("WaitressInfo", "Nama: ${waitress.name}, URL: ${waitress.photoUrl}")
                binding.waitress = waitress
            }
        })
    }
}