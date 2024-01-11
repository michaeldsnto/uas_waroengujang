package com.example.uas_waroengujang.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.databinding.FragmentAccountBinding
import com.example.uas_waroengujang.viewmodel.WaitressViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var waitressModel: WaitressViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        waitressModel = ViewModelProvider(requireActivity()).get(WaitressViewModel::class.java)

        // Set waitress sebagai variabel binding
        binding.waitress = waitressModel.waitressLD.value

        // Tetapkan lifecycle owner untuk binding
        binding.lifecycleOwner = viewLifecycleOwner

        // Panggil observeViewModel di sini
        observeViewModel()
    }

    private fun observeViewModel() {
        waitressModel.waitressLD.observe(viewLifecycleOwner, Observer { waitress ->
            waitress?.let {
                // Tampilkan informasi seperti nama, work since, gambar, dan kata sandi (di sini hanya contoh)
                txtNamaAkun.text = waitress.name
                txtWork.text = waitress.workSince
                // Gunakan Picasso atau metode lain untuk memuat gambar
                Picasso.get().load(waitress.photoUrl).into(photoAkun)
            }
        })
    }
}
