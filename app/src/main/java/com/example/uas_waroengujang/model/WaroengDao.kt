package com.example.uas_waroengujang.model
import androidx.room.*

@Dao
interface WaroengDao {

    @Query("SELECT * FROM waitress")
    fun selectAllWaitress(): List<Waitress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenu(vararg menu: Menu)

    @Query("SELECT * FROM menu")
    fun selectAllMenu(): List<Menu>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(vararg cart: Cart)

    @Query("SELECT * FROM cart")
    fun selectAllCart(): List<Cart>

    @Query("DELETE FROM cart WHERE nama = :nama")
    fun deleteCartByNama(nama: String)

    @Query("UPDATE cart SET jumlah = :jumlah WHERE nama = :nama")
    fun updateCartJumlah(jumlah: Int, nama: String)
}