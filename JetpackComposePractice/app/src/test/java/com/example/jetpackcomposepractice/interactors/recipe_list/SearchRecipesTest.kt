package com.example.jetpackcomposepractice.interactors.recipe_list

import com.example.jetpackcomposepractice.cache.AppDatabaseFake
import com.example.jetpackcomposepractice.cache.RecipeDaoFake
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.data.MockWebServerResponses
import com.example.jetpackcomposepractice.network.data.MockWebServerResponses.recipeListResponse
import com.example.jetpackcomposepractice.network.model.RecipeDtoMapper
import com.google.gson.GsonBuilder
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

    @Test
    fun mockWebServerSetup() {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(recipeListResponse)
        )
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}