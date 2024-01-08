package com.example.uas_waroengujang.view

import MenuViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.databinding.FragmentMenuBinding
import com.example.uas_waroengujang.model.Menu
import com.example.uas_waroengujang.viewmodel.HomeViewModel


class MenuFragment : Fragment() {
    private lateinit var viewModel:MenuViewModel
    private lateinit var homeViewModel: HomeViewModel
    private val menuListAdapter = MenuAdapter(arrayListOf())
    private lateinit var dataBinding:FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        viewModel.fetchMenuDataFromJson()
        Log.d("MenuViewModel", "Menu: ${viewModel.fetchMenuDataFromJson()} ")
        val recView = view.findViewById<RecyclerView>(R.id.recViewMenu)
        recView?.layoutManager = GridLayoutManager(requireContext(), 2)
        recView?.adapter = menuListAdapter

        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)

        swipe.setOnRefreshListener {
            viewModel.fetchMenuFromDatabase()
            swipe.isRefreshing = false

        }

        observeViewModel()
        val txtTable = view.findViewById<TextView>(R.id.txtTable)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        homeViewModel.getTableNumber().observe(viewLifecycleOwner, Observer{ tableNumber ->
            if (!tableNumber.isNullOrBlank()) {
                txtTable.text = "Table Number $tableNumber"
            }

        })
        val btnChange = view.findViewById<Button>(R.id.btnChange)
        btnChange.setOnClickListener {
            val action = MenuFragmentDirections.actionChange()
            Navigation.findNavController(it).navigate(action)

        }

    }
    fun observeViewModel() {
        viewModel.menuLD.observe(viewLifecycleOwner, Observer {
            menuListAdapter.updateMenuList(it as ArrayList<Menu>)
        })
        viewModel.menuLoadErrorLD.observe(viewLifecycleOwner, Observer {
            val txtError = view?.findViewById<TextView>(R.id.txtError)
            if(it == true) {
                txtError?.visibility = View.VISIBLE
            } else {
                txtError?.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            val progressLoad = view?.findViewById<ProgressBar>(R.id.progressLoad)
            val recView = view?.findViewById<RecyclerView>(R.id.recViewMenu)
            if(it == true) {
                recView?.visibility = View.GONE
                progressLoad?.visibility = View.VISIBLE
            } else {
                recView?.visibility = View.VISIBLE
                progressLoad?.visibility = View.GONE
            }
        })
    }
}