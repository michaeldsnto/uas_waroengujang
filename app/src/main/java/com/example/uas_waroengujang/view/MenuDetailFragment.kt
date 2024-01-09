package com.example.uas_waroengujang.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.databinding.FragmentMenuDetailBinding
import com.example.uas_waroengujang.viewmodel.CartViewModel
import com.example.uas_waroengujang.viewmodel.DetailViewModel

class MenuDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var cartView: CartViewModel
    private lateinit var binding: FragmentMenuDetailBinding // Replace with your actual binding class name

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuId = arguments?.getString("menuId")

        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        cartView = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        if (menuId != null) {
            viewModel.getMenuById(menuId)
        }
        viewModel.menuLD.observe(viewLifecycleOwner) { menu ->
            if (menu != null) {
                binding.menu = menu
                binding.btnTambah.setOnClickListener {
                    var quantity = binding.txtJumlah.text.toString().toInt()
                    val harga = menu.harga
                    val photoUrl = menu.photoUrl
                    quantity++
                    binding.txtJumlah.text = quantity.toString()
                    val totalHarga = harga * quantity
                    binding.txtHargaDetail.text = "IDR $totalHarga"

                    cartView.addMenuToCart(menu.nama, quantity, totalHarga, photoUrl)
                    Toast.makeText(requireContext(), "Ditambahkan ke keranjang", Toast.LENGTH_SHORT)
                        .show()
                }

                binding.btnKurang.setOnClickListener {
                    var quantity = binding.txtJumlah.text.toString().toInt()
                    val harga = menu.harga

                    if (quantity > 0) {
                        quantity--
                        binding.txtJumlah.text = quantity.toString()

                        val totalHarga = harga * quantity
                        binding.txtHargaDetail.text = "IDR $totalHarga"

                    }
                }
            }
        }
    }
}