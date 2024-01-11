package com.example.uas_waroengujang.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_waroengujang.databinding.CartItemBinding
import com.example.uas_waroengujang.model.Cart
import com.example.uas_waroengujang.viewmodel.CartViewModel

import com.squareup.picasso.Picasso

class CartAdapter(val cartList: ArrayList<Cart>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(), CartItemListener, HomeListener {
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
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    fun updateCartList(newCartList: ArrayList<Cart>){
        cartList.clear()
        cartList.addAll(newCartList)
        notifyDataSetChanged()
    }

    override fun onChangeClick(v: View) {
        val action = MenuFragmentDirections.actionChange()
        Navigation.findNavController(v).navigate(action)
    }

    override fun onTambahClick(cart: Cart) {

    }

    override fun onKurangClick(cart: Cart) {
    }
}