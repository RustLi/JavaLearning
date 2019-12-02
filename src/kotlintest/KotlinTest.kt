package kotlintest

var a: String = "init"
class MyClass

fun main() {
    println("Hello, World!")
    printMsg("lwl")
    println(a)

    cases("Hello")
    cases(1)
    cases(0L)
    cases(MyClass())
    cases("hello")

    DoAuth.takeParams("aaa", "bbb")

    BigBen.getBongs(12)
}

fun printMsg(msg: String): Unit{
    println(msg)
}

fun cases(obj: Any) {
    when (obj) {                                     // 1
        1 -> println("One")                          // 2
        "Hello" -> println("Greeting")               // 3
        is Long -> println("Long")                   // 4
        !is String -> println("Not a string")        // 5
        else -> println("Unknown")                   // 6
    }
}

//object对象，单例,懒加载
object DoAuth {                                                 //1
    fun takeParams(username: String, password: String){         //2
        println("input Auth parameters = $username:$password")
    }
}

//companion 静态方法
class BigBen{
    companion object Bonger{
        fun getBongs(nTimes: Int){
            for (i in 1..nTimes){
                print("BONG")
            }
        }
    }
}
