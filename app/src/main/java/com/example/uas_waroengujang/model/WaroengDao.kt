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

    @Query("DELETE FROM cart WHERE nama = :nama AND tableNumber = :tableNumber")
    fun deleteCartByNama(nama: String, tableNumber: String)

    @Query("UPDATE cart SET jumlah = :jumlah WHERE nama = :nama AND tableNumber = :tableNumber AND uuid = :id")
    fun updateCart(jumlah: Int, nama: String, tableNumber: String, id :Int)
}