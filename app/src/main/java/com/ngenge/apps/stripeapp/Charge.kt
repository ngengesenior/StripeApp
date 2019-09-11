package com.ngenge.apps.stripeapp

import com.google.gson.annotations.SerializedName


data class Charge(
    @SerializedName("token")val token:String,
    @SerializedName("amount")val amount:Int,
    @SerializedName("currency")val currency:String
)



data class StripeDataResponse(
    @SerializedName("id") val id:String,
    @SerializedName("created_at") val dateCreated:Long,
    @SerializedName("amount") val amount: Int,
    @SerializedName("balance_transaction") val balanceTransaction:String,
    @SerializedName("receipt_url") val receiptURL:String?=null,
    @SerializedName("status") val status:Boolean,
    @SerializedName("currency") val currencyCode: String
)