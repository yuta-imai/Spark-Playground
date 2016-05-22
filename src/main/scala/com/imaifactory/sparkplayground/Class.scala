package com.imaifactory.sparkplayground

/**
  * Created by yimai on 12/4/15.
  */


class MyClass(_t:String) {
  val t = _t
}

object Class {
  def main(args: Array[String]): Unit = {
    val a = new MyClass("test")
    print(a)
  }
}
