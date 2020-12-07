package com.haohcraft.ktorgraphql.graphql

import com.expediagroup.graphql.SchemaGeneratorConfig
import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.toSchema
import graphql.ExecutionInput
import graphql.GraphQL
import graphql.execution.AsyncExecutionStrategy
import graphql.schema.GraphQLSchema
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import java.util.logging.Logger

fun Application.installGraphQL() {
    val logger = LoggerFactory.getLogger("Application.installGraphQL")

    fun generateGraphql(): GraphQL {
        logger.info("Building schema ...")
        val config = SchemaGeneratorConfig(
            listOf(
                "com.haohcraft.ktorgraphql.data.model"
            )
        )
        val queries = listOf(TopLevelObject(Query()))
        val schema: GraphQLSchema = toSchema(config = config, queries = queries)

        return GraphQL.newGraphQL(schema)
            .build()
    }

    val graphql = generateGraphql()

    suspend fun ApplicationCall.executeQuery() {
        val request = this.receive<GraphQLRequest>()
        val executionInputInit = ExecutionInput.newExecutionInput()

        try {
            logger.info("Query: ${request.query.replace("\n", "")}")

            val executionInput = executionInputInit
                .query(request.query)
                .operationName(request.operationName)
                .variables(request.variables)
                .build()
            val result = graphql.executeAsync(executionInput).await()
            respond(result.toSpecification())
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    routing {
        post("/graphql") {
            withContext(Dispatchers.IO) {
                call.executeQuery()
            }
        }

        // TODO: need to determine approach to limit to internal traffic (https://jira.walmart.com/browse/HWHOBKN2-1409)
        static("/graphql-playground") {
            defaultResource("static/graphql-playground.html")
        }
    }
}
