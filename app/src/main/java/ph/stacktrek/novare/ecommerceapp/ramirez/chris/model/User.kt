package ph.stacktrek.novare.ecommerceapp.ramirez.chris.model

import java.time.LocalDateTime

fun add()=0

fun add(value1:Int,value2:Int)=value1+value2

fun add(value1:Float,value2:Float):Float{
    return value1+value2
}

fun Int.add(value1:Int,value2:Int)=value1+value2

fun add(value1:Double=0.0,value2:Double)=value1+value2

fun compute(vararg numbers:Int):Int{
    var result:Int=0
    return result
}

open class User(val username:String, val password:String) {
    lateinit var email:String
        private set
    lateinit var mobileNumber:String
        private set
    lateinit var userId:String
        private set
    lateinit var accountCreated:LocalDateTime
        private set
    lateinit var lastLogin: LocalDateTime
        private set

    init {

    }
    constructor():this("","")
}

class ShopOwner(): User(){
    lateinit var shopName: String
        private set
    lateinit var rating:ArrayList<String>
}

class Buyer : User(){
    lateinit var cart: ArrayList<String>

}