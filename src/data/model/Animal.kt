package com.haohcraft.ktorgraphql.data.model

interface Animal {
    val type: AnimalType
    fun sound(): String
}

enum class AnimalType {
    CAT,
    DOG
}

class Dog : Animal {
    override val type = AnimalType.DOG

    override fun sound() = "bark"

    fun barkAtEveryone(): String = "bark at everyone"
}

class Cat : Animal {
    override val type = AnimalType.CAT

    override fun sound() = "meow"

    fun ignoreEveryone(): String = "ignore everyone"
}
