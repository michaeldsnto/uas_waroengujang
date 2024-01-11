package com.example.uas_waroengujang.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_waroengujang.databinding.CartItemBinding
import com.example.uas_waroengujang.model.Cart
import com.example.uas_waroengujang.viewmodel.CartViewModel
import com.example.uas_waroengujang.viewmodel.HomeViewModel

class CartAdapter(val cartList: ArrayList<Cart>, private val homeViewModel: HomeViewModel, private val viewModel: CartViewModel) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(var view: CartItemBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CartItemBinding.inflate(inflater, parent,
            false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.view.cartData = cartList[position]
        holder.view.btnTambah.setOnClickListener {
            updateQuantity(1, holder.view, position)
        }

        holder.view.btnKurang.setOnClickListener {
            updateQuantity(-1, holder.view, position)
        }

//        holder.view.btnAdd.setOnClickListener {
//            val tableNumber = homeViewModel.getTableNumber().value
//
//            if (!tableNumber.isNullOrBlank()) {
//                cartView.addMenuCart(
//                    Cart(
//                        menu.nama,
//                        holder.view.txtJumlah.text.toString().toInt(),
//                        menu.harga,
//                        menu.photoUrl,
//                        tableNumber
//                    )
//                )
//                Toast.makeText(requireContext(), "Ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
//                val action = MenuDetailFragmentDirections.actionMenuCart()
//                Navigation.findNavController(it).navigate(action)
//            } else {
//                // Handle the case where the table number is null or blank
//                Toast.makeText(requireContext(), "Table number is not set", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
    private fun updateQuantity(change: Int, binding: CartItemBinding, position: Int) {
        val currentQuantity = binding.txtQuantity.text.toString().toInt()
        val newQuantity = currentQuantity + change

        if (newQuantity >= 0) {
            val harga = binding.cartData?.harga ?: 0
            val totalHarga = harga * newQuantity

            binding.txtQuantity.setText(newQuantity.toString())
            binding.txtHarga.text = "IDR $totalHarga"

            // Update quantity in the database
            val tableNumber = homeViewModel.getTableNumber().value.toString()
            val menu = binding.cartData
            val cartId = cartList[position].uuid

            if (!tableNumber.isBlank() && menu != null && cartId != null) {
                viewModel.updateCartJumlah(cartId, newQuantity, tableNumber)
            }
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun updateCartList(newCartList: ArrayList<Cart>){
        cartList.clear()
        cartList.addAll(newCartList)
        notifyDataSetChanged()
    }
}