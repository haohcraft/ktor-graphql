package com.haohcraft.ktorgraphql.graphql

import com.haohcraft.ktorgraphql.data.model.Animal
import com.haohcraft.ktorgraphql.data.model.AnimalType
import com.haohcraft.ktorgraphql.data.model.Cat
import com.haohcraft.ktorgraphql.data.model.Dog

class Query {
    fun animal(type: AnimalType): List<Animal> = listOf(
        Dog(),
        Cat()
    )
}
