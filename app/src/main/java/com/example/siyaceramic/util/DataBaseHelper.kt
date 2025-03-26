package com.example.siyaceramic.util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(private val mContext: Context) : SQLiteOpenHelper(
    mContext,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    // Creating Tables
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CART_TABLE = ("CREATE TABLE " + TABLE_Cart + "("
                + CR_ID
                + " INTEGER PRIMARY KEY,"
                + CR_PID + " Int,"
                + CR_UID + " Int,"
                + CR_TITLE + " TEXT,"
                + CR_QTY + " TEXT,"
                + CR_IMG + " TEXT,"
                + CR_SIZE + " TEXT,"
                + CR_CAT + " TEXT" + ")")
        db?.execSQL(CREATE_CART_TABLE)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_Cart)

        // Create tables again
        onCreate(db)
    }
    fun listCart(): ArrayList<Cart> {
        val sql = "select * from $TABLE_Cart"
        val db = this.readableDatabase
        val storeExperience =
            ArrayList<Cart>()
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0).toInt()
                val pid = cursor.getInt(1)
                val userid = cursor.getInt(2)
                val title = cursor.getString(3)
                val quantity = cursor.getString(4)
                val image = cursor.getString(5)
                val size = cursor.getString(6)
                val category = cursor.getString(7)


                storeExperience.add(Cart(id,pid, userid ,title, quantity, image,size,category))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        return storeExperience
    }

    fun listCarts(fieldValue: Int): ArrayList<Cart> {
        val sql = "select * from $TABLE_Cart where $CR_UID = $fieldValue "
        val db = this.readableDatabase
        val storeExperience =
            ArrayList<Cart>()
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0).toInt()
                val pid = cursor.getInt(1)
                val userid = cursor.getInt(2)
                val title = cursor.getString(3)
                val quantity = cursor.getString(4)
                val image = cursor.getString(5)
                val size = cursor.getString(6)
                val category = cursor.getString(7)


                storeExperience.add(Cart(id,pid, userid ,title, quantity, image,size,category))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        return storeExperience
    }

    fun getItemCart(fieldValue: Int): Cart {
        val sql = "select * from $TABLE_Cart where $CR_PID = $fieldValue"
        val db = this.readableDatabase
        val cursor = db.rawQuery(sql, null)
        var storeExp = Cart(0,0,0,"","","","","")
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0).toInt()
                val pid = cursor.getInt(1)
                val userid = cursor.getInt(2)
                val title = cursor.getString(3)
                val quantity = cursor.getString(4)
                val image = cursor.getString(5)
                val size = cursor.getString(6)
                val category = cursor.getString(7)


              storeExp = Cart(id,pid,userid,title, quantity, image,size,category)
            }
            while (cursor.moveToNext())
        }
        cursor.close()

        return storeExp
    }

    fun addCart(cart: Cart) {
        val values = ContentValues()
        values.put(CR_TITLE, cart.cartTitle)
        values.put(CR_PID, cart.productId)
        values.put(CR_UID, cart.userId)
        values.put(CR_QTY, cart.cartQty)
        values.put(CR_IMG, cart.cartImg)
        values.put(CR_SIZE, cart.cartSize)
        values.put(CR_CAT, cart.cartCat)
        val db = this.writableDatabase
        db.insert(TABLE_Cart, null, values)
        db.close()
    }

    fun deleteCart(id: Int) {
        val db = this.writableDatabase
        db.delete(
            TABLE_Cart,
            "$CR_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
    }



    fun totalCount(fieldValue: Int): Int {
        val db = this.writableDatabase
        var sum = 0
        val cur: Cursor = db.rawQuery(
            "SELECT SUM(" + CR_QTY + ") FROM  "+ TABLE_Cart + " WHERE +(" + CR_UID + ") = $fieldValue" ,
            null
        )

        if (cur.moveToFirst()) {
            sum = cur.getInt(0)
        }
        cur.close()
        db.close()
        return sum
    }


    fun deleteAllCart(){
        val db = this.writableDatabase
        db.delete(TABLE_Cart,null,null)
        db.close()
    }

    fun checkForCartTablesEmptyOrNot(): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_Cart, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val count = cursor.getInt(0)
            if (count > 0) {
                return true
            }
            cursor.close()
        }
        return false
    }

    //Update cart
    fun updateCartItem(cart: Cart,position:Int?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CR_TITLE, cart.cartTitle)
        values.put(CR_PID, cart.productId)
        values.put(CR_QTY, cart.cartQty)
        values.put(CR_IMG, cart.cartImg)
        values.put(CR_SIZE, cart.cartSize)
        values.put(CR_CAT, cart.cartCat)
        db.update(
            TABLE_Cart, values, "cart_id="
                    + cart.id, null
        )
    }

    //Update cart
    fun updateCart(cart: Cart) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CR_TITLE, cart.cartTitle)
        values.put(CR_PID, cart.productId)
        values.put(CR_QTY, cart.cartQty)
        values.put(CR_IMG, cart.cartImg)
        values.put(CR_SIZE, cart.cartSize)
        values.put(CR_CAT, cart.cartCat)
        db.update(
            TABLE_Cart, values, "cart_id="
                    + cart.id, null
        )
        db.close()
    }

    fun updateCarts(id: Int?, cartQty: String?) {
        val db = this.writableDatabase
        val valueToChange = cartQty
        val values = ContentValues().apply {
            put(CR_QTY, valueToChange)
        }
        db.update(TABLE_Cart, values, "cart_pid=$id", null)
        db.close() // Closing database connection
    }

    fun CheckIsDataAlreadyInDBorNot(
        fieldValue: Int
    ): Boolean {
        val db = this.writableDatabase
        val Query = "Select * from $TABLE_Cart where $CR_PID = $fieldValue"
        val cursor = db.rawQuery(Query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun CheckUserExistorNot(
        fieldValue: Int
    ): Boolean {
        val db = this.writableDatabase
        val Query = "Select * from $TABLE_Cart where $CR_UID = $fieldValue"
        val cursor = db.rawQuery(Query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }


    companion object {
        // All Static variables
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "siyaCeramics"

        // Cart table name
        private const val TABLE_Cart = "cart"

        //Cart
        private const val CR_ID = "cart_id"
        private const val CR_PID = "cart_pid"
        private const val CR_TITLE = "cart_title"
        private const val CR_QTY = "cart_quantity"
        private const val CR_IMG = "cart_image"
        private const val CR_SIZE = "cart_size"
        private const val CR_CAT = "cart_cat"
        private const val CR_UID = "cart_userid"
    }
}