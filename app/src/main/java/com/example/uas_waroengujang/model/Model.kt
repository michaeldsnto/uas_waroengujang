package com.example.uas_waroengujang.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Waitress(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "work_since")
    val workSince: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String,
)

@Entity
data class Menu(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "nama_makanan")
    val nama: String,
    @ColumnInfo(name = "kategori")
    val kategori: String,
    @ColumnInfo(name = "harga")
    val harga: Int,
    @ColumnInfo(name = "deskripsi")
    val deskripsi: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl: String
)

@Entity
data class Cart(
    @ColumnInfo(name = "nama")
    val nama: String,
    @ColumnInfo(name = "jumlah")
    var jumlah: Int,
    @ColumnInfo(name = "harga")
    val harga: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
@Entity
data class Orders(
    @ColumnInfo(name = "nomor_table")
    val nomorTable: String,
    @ColumnInfo(name = "total_harga")
    val totalHarga: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}