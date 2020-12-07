package com.haohcraft.ktorgraphql.graphql

data class GraphQLRequest(
    val query: String = "",
    val operationName: String = "",
    val variables: Map<String, Any> = emptyMap()
)
