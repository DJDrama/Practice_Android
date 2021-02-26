package com.example.jetpackcomposepractice.interactors.recipe_list

import com.example.jetpackcomposepractice.cache.AppDatabaseFake
import com.example.jetpackcomposepractice.cache.RecipeDaoFake
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.data.MockWebServerResponses
import com.example.jetpackcomposepractice.network.data.MockWebServerResponses.recipeListResponse
import com.example.jetpackcomposepractice.network.model.RecipeDtoMapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchRecipesTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()

    private val DUMMY_TOKEN = "faketokenasdfasdf"
    private val DUMMY_QUERY = "dummyqueryasdfasdf"


    // system in test
    private lateinit var searchRecipes: SearchRecipes

    // dependencies
    private lateinit var recipeService: RecipeService
    private lateinit var recipeDao: RecipeDaoFake
    private val dtoMapper = RecipeDtoMapper()
    private val entityMapper = RecipeEntityMapper()

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/api/recipe/")
        recipeService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)

        recipeDao = RecipeDaoFake(appDatabaseFake = appDatabase)

        // instantiate the system in test
        searchRecipes = SearchRecipes(
            recipeDao = recipeDao,
            recipeService = recipeService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )
    }

    /**
     * 1. Are the recipes retrieved from the network?
     * 2. Are the recipes inserted into the cache?
     * 3. Are the recipes then emitted as a flow from the cache to the ui?
     *
     */

    @Test
    fun getRecipesFromNetwork_emitRecipesFromCache(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(recipeListResponse)
        )
        // confirm the cache if empty to start
        assert(recipeDao.getAllRecipes(1, 30).isEmpty())
        val flowItems = searchRecipes.execute(DUMMY_TOKEN, 1, DUMMY_QUERY, true).toList()

        // confirm the cache is no longer empty
        assert(recipeDao.getAllRecipes(1, 30).isNotEmpty())

        // first emission should be loading
        assert(flowItems[0].loading)

        // second emission should be the list of recipes
        val recipes = flowItems[1].data
        assert(recipes?.size ?: 0 > 0)

        // confirm they are actually recipe objects
        assert(recipes?.get(index = 0) is Recipe)

        // ensure loading is false now
        assert(!flowItems[1].loading)
    }

    @Test
    fun getRecipesFromNetwork_emitHttpError() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )
        val flowItems = searchRecipes.execute(DUMMY_TOKEN, 1, DUMMY_QUERY, true).toList()

        assert(flowItems[0].loading)

        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}