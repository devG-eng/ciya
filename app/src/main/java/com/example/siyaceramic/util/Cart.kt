package com.example.siyaceramic.util

class Cart {
    var id = 0
    var productId: Int? = 0
    var userId = 0
    var cartTitle: String? = ""
    var cartQty: String? = ""
    var cartImg: String? = ""
    var cartSize: String? = ""
    var cartCat: String? = ""


    /*internal constructor(carttitle: String, cartqty: String, cartimg: String) {
        this.cartTitle = carttitle
        this.cartQty = cartqty
        this.cartImg = cartimg

    }

    internal constructor(id: Int, carttitle: String, cartqty: String, cartimg: String) {
        this.id = id
        this.cartTitle = carttitle
        this.cartQty = cartqty
        this.cartImg = cartimg

    }*/


    internal constructor(productid: Int,userid :Int, carttitle: String, cartqty: String, cartimg: String,cartsize: String, cartcat: String) {
        this.productId = productid
        this.userId = userid
        this.cartTitle = carttitle
        this.cartQty = cartqty
        this.cartImg = cartimg
        this.cartSize = cartsize
        this.cartCat = cartcat
    }

    internal constructor(id: Int, productid: Int, userid: Int, carttitle: String, cartqty: String, cartimg: String,cartsize: String,cartcat: String) {
        this.id = id
        this.productId = productid
        this.userId = userid
        this.cartTitle = carttitle
        this.cartQty = cartqty
        this.cartImg = cartimg
        this.cartSize = cartsize
        this.cartCat = cartcat
    }

   /* internal constructor(id: Int,carttitle: String, cartqty: String, cartimg: String,cartsize: String,cartcat: String) {
        this.id = id
        this.cartTitle = carttitle
        this.cartQty = cartqty
        this.cartImg = cartimg
        this.cartSize = cartsize
        this.cartCat = cartcat
    }*/
}