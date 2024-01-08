package com.example.uas_waroengujang.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_waroengujang.databinding.MenuItemBinding
import com.example.uas_waroengujang.model.Menu
import com.squareup.picasso.Picasso

class MenuAdapter(val menuList: ArrayList<Menu>) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(), MenuDetailListener, HomeListener {
    class MenuViewHolder(var view: MenuItemBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = MenuItemBinding.inflate(inflater, parent,
            false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.view.menu = menuList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun updateMenuList(newMenuList: ArrayList<Menu>){
        menuList.clear()
        menuList.addAll(newMenuList)
        notifyDataSetChanged()
    }

    override fun onMenuDetailClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = MenuFragmentDirections.actionMenuDetail(uuid)
        Navigation.findNavController(v).navigate(action)
    }

    override fun onChangeClick(v: View) {
        val action = MenuFragmentDirections.actionChange()
        Navigation.findNavController(v).navigate(action)
    }

}