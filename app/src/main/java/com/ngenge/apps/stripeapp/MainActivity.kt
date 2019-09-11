package com.ngenge.apps.stripeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.NotNull
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var stripe: Stripe
    private lateinit var stripeService:StripeService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Replace your publishable key here
        stripe = Stripe(this, "pk_test_c2zO3BZJbMZ0Yp4gougsNRzm")
        stripeService = StripeService.create()

        buttonPay.setOnClickListener {
            val card = cardMultilineWidget.card
            if (card != null) {
                tokenizeCard(card)
            } else {
                Toast.makeText(this@MainActivity,"Your card has errors",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
    }


    private fun tokenizeCard(@NotNull card: Card) {
        progressBar.visibility = View.VISIBLE

        stripe.createToken(card, object : ApiResultCallback<Token> {

            override fun onSuccess(result: Token) {

                progressBar.visibility = View.GONE

                GlobalScope.launch(Dispatchers.IO) {
                    val response = stripeService.createCharge(Charge(result.id,500,"xaf"))
                    Log.d("TOKEN",result.id)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                Toast.makeText(this@MainActivity,"Payment made successfully",Toast.LENGTH_LONG).show()
                                Log.d("CHARGE","$it")
                            }

                        } else {
                            Toast.makeText(this@MainActivity,"Payment was not  successfully. Please check your details",Toast.LENGTH_LONG).show()
                            Log.e("TAG--","${response.errorBody()?.string()} ${response.code()}")
                        }
                    }

                }


            }

            override fun onError(e: Exception) {
                Toast.makeText(this@MainActivity,"Error creating token",Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
                return
            }
        }
        )
    }
}
