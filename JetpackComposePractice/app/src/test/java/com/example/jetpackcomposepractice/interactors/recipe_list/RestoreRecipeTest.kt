package com.example.jetpackcomposepractice.interactors.recipe_list

import com.example.jetpackcomposepractice.cache.AppDatabaseFake
import com.example.jetpackcomposepractice.cache.RecipeDaoFake
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.data.MockWebServerResponses
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

class RestoreRecipeTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()

    private val DUMMY_TOKEN = "faketokenasdfasdf"
    private val DUMMY_QUERY = "dummyqueryasdfasdf"


    // system in test
    private lateinit var restoreRecipes: RestoreRecipes

    // dependencies
    private lateinit var searchRecipes: SearchRecipes
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

        searchRecipes = SearchRecipes(
            recipeDao = recipeDao,
            recipeService = recipeService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )

        // instantiate the system in test
        restoreRecipes = RestoreRecipes(
            recipeDao = recipeDao,
            entityMapper = entityMapper
        )
    }

    /**
     * 1. Get some recipes from the network and insert into the cache
     * 2. Restore and show recipes are retrieved from cache
     */
    @Test
    fun getRecipesFromNetwork_restoreFromCache(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.recipeListResponse)
        )

        assert(recipeDao.getAllRecipes(1, 30).isEmpty())
        val searchResults = searchRecipes.execute(DUMMY_TOKEN, 1, DUMMY_QUERY, true).toList()

        assert(recipeDao.getAllRecipes(1, 30).isNotEmpty())

        // run our use case
        val flowItems = restoreRecipes.execute(1, DUMMY_QUERY).toList()

        assert(flowItems[0].loading)

        val recipes = flowItems[1].data
        assert(recipes?.size ?: 0 >0)
        assert(recipes?.get(0) is Recipe)
        assert(!flowItems[1].loading)

    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}