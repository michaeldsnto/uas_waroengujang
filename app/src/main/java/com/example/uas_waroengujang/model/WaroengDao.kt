package com.example.uas_waroengujang.model
import androidx.room.*

@Dao
interface WaroengDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWaitress(vararg waitress: Waitress)

    @Query("SELECT * FROM waitress")
    fun selectAllWaitress(): List<Waitress>
    @Query("SELECT * FROM waitress WHERE id = :id")
    fun selectWaitress(id: String) :Waitress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(menus: List<Menu>)

    @Query("SELECT * FROM menu")
    fun selectAllMenu(): List<Menu>

    @Query("SELECT * FROM menu WHERE id = :id")
    fun selectMenuById(id: String) :Menu

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(vararg cart: Cart)

    @Query("SELECT * FROM cart WHERE tableNumber = :tableNumber")
    fun selectCartByTableNumber(tableNumber: String): List<Cart>

    @Query("UPDATE cart SET jumlah = :newJumlah WHERE uuid = :id")
    fun updateCartJumlah(id: Int, newJumlah: Int)

    @Query("DELETE FROM cart WHERE tableNumber = :tableNumber")
    fun deleteCartByTableNumber(tableNumber: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrders(vararg orders: Orders)

    @Query("DELETE FROM cart WHERE uuid = :cartId")
    fun deleteCartItem(cartId: Int)
}