package com.example.uas_waroengujang.view

import android.content.Context
import com.example.uas_waroengujang.viewmodel.MenuViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.databinding.CartItemBinding
import com.example.uas_waroengujang.databinding.FragmentCartBinding
import com.example.uas_waroengujang.databinding.FragmentMenuBinding
import com.example.uas_waroengujang.model.Cart
import com.example.uas_waroengujang.model.Menu
import com.example.uas_waroengujang.model.Orders
import com.example.uas_waroengujang.viewmodel.CartViewModel
import com.example.uas_waroengujang.viewmodel.HomeViewModel


class CartFragment : Fragment(), CheckoutListener {
    private lateinit var viewModel: CartViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var cartListAdapter: CartAdapter
    private lateinit var dataBinding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.checkoutListener = this
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        cartListAdapter = CartAdapter(arrayListOf(), homeViewModel, viewModel)

        val recView = view.findViewById<RecyclerView>(R.id.recViewCart)
        recView?.layoutManager = LinearLayoutManager(context)
        recView?.adapter = cartListAdapter
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchMenuFromDatabase(homeViewModel.getTableNumber().value.toString())
            swipeRefreshLayout.isRefreshing = false
        }

        observeViewModel()
        val txtTable = view.findViewById<TextView>(R.id.txtTableNum)
        val tableNumber = homeViewModel.getTableNumber().value.toString()
        viewModel.fetchMenuFromDatabase(tableNumber)
        Log.d("Table Number", homeViewModel.getTableNumber().value.toString())
        homeViewModel.getTableNumber().observe(viewLifecycleOwner, Observer { tableNumber ->
            if (!tableNumber.isNullOrBlank()) {
                txtTable.text = "Table Number $tableNumber"
            }
        })
    }
    fun observeViewModel() {
        viewModel.cartLD.observe(viewLifecycleOwner, Observer { cartList ->
            cartListAdapter.updateCartList(cartList as ArrayList<Cart>)
            recalculateTotals(cartList)
        })

        viewModel.menuLoadErrorLD.observe(viewLifecycleOwner, Observer {
            val txtError = view?.findViewById<TextView>(R.id.txtError)
            if (it == true) {
                txtError?.visibility = View.VISIBLE
            } else {
                txtError?.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            val progressLoad = view?.findViewById<ProgressBar>(R.id.progressLoad)
            val recView = view?.findViewById<RecyclerView>(R.id.recViewCart)
            if (it == true) {
                recView?.visibility = View.GONE
                progressLoad?.visibility = View.VISIBLE
            } else {
                recView?.visibility = View.VISIBLE
                progressLoad?.visibility = View.GONE
            }
        })
    }

    private fun recalculateTotals(cartList: List<Cart>) {
        var subtotal = 0
        for (item in cartList) {
            subtotal += item.harga * item.jumlah
        }
        dataBinding.txtSubtotal.text = "Rp. $subtotal"

        val tax = (subtotal * 0.1).toInt()
        dataBinding.txtTax.text = "Rp. $tax"

        val total = subtotal + tax
        dataBinding.txtTotal.text = "Rp. $total"
    }


    override fun onCheckoutClicked() {
        val tableNumber = homeViewModel.getTableNumber().value

        if (!tableNumber.isNullOrBlank()) {
            viewModel.cartLD.observe(viewLifecycleOwner) { cartList ->
                val totalHarga = cartList.sumOf { cart -> cart.harga * cart.jumlah }

                val orders = Orders(
                    tableNumber,
                    totalHarga
                )

                viewModel.checkoutCart(tableNumber,orders)
                Toast.makeText(requireContext(), "Checkout Berhasil", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Table number is not set", Toast.LENGTH_SHORT).show()
        }
    }

}