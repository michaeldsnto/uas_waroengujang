package com.example.uas_waroengujang.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.databinding.FragmentAccountBinding
import com.example.uas_waroengujang.viewmodel.WaitressViewModel
import com.squareup.picasso.Picasso

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

        binding.waitress = waitressModel.waitressLD.value

        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()

        binding.btnSignout.setOnClickListener {
            clearUserSession()
            redirectToLoginActivity()
        }
    }

    private fun observeViewModel() {
        waitressModel.waitressLD.observe(viewLifecycleOwner, Observer { waitress ->
            waitress?.let {
                binding.txtNamaAkun.text = waitress.name
                binding.txtWork.text = waitress.workSince
                Picasso.get().load(waitress.photoUrl).into(binding.photoAkun)
            }
        })
    }

    private fun clearUserSession() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    private fun redirectToLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
