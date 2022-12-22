package com.peterchege.scrollmall.api

import com.peterchege.scrollmall.api.requests.*
import com.peterchege.scrollmall.api.responses.*
import com.peterchege.scrollmall.models.Message
import com.peterchege.scrollmall.util.Constants
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ScrollmallApi {
    @POST("/user/login")
    suspend fun loginUser(@Body loginUser: LoginUser): LoginResponse

    @POST("/user/signup")
    suspend fun signUpUser(@Body signUpUser: SignUpUser): SignUpResponse

    @GET("/product/search")
    suspend fun searchItems(@Query("query") query :String):SearchItemResponse

    @GET("/user/search")
    suspend fun searchUsers(@Query("query") query :String):SearchUserResponse

    @PUT("/product/update/{id}")
    suspend fun updateItem(@Path("id") id:String, @Body updateBody:UpdateBody):UpdateItemResponse

    @POST("/user/logout")
    suspend fun logoutUser(@Body logoutUser: LogoutUser): SignUpResponse

    @GET("/product/allProducts")
    suspend fun getAllProducts(): AllProductsResponse

    @GET("/product/single/{id}")
    suspend fun getProductById(@Path("id") id: String): ProductResponse

    @DELETE("/product/delete/{id}")
    suspend fun deleteProduct(@Path("id") id: String): DeleteProductResponse


    @GET("/product/all")
    suspend fun getProductByUser(@Query("username") username: String): GetProductsByUsernameResponse

    @GET("/product/category")
    suspend fun getProductByCategory(@Query("category") category: String): GetProductsByCategoryResponse


    @Multipart
    @POST("/product/add")
    suspend fun addProduct(
        @Part photos:List<MultipartBody.Part> ,
        @Part("product") product: AddProduct
    ): SignUpResponse

    @POST("/order/neworder")
    suspend fun placeOrder(@Body placeOrder: OrderBody): SignUpResponse


    @POST("/chat/sendMessage/{senderId}")
    suspend fun sendMessage(
        @Path("senderId") senderId: String,
        @Body message: Message
    ): SendMessageResponse

    @GET("/user/single/{id}")
    suspend fun getUserById(@Path("id") id: String): GetUserByIdResponse

    @GET("/chat/message/{senderId}/{receiverId}")
    suspend fun getMessages(
        @Path("senderId") senderId: String,
        @Path("receiverId") receiverId: String
    ): GetChatMessageResponse


    @GET("/chat/mychats/{senderId}")
    suspend fun getMyChats(@Path("senderId") senderId: String): MyChatsResponse


    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(ScrollmallApi::class.java)
        }
    }
}