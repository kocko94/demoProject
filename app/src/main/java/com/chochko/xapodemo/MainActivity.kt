package com.chochko.xapodemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chochko.xapodemo.network.http.GithubHttpClient
import com.github.benoitdion.ln.Ln
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GithubHttpClient.githubHttpClient.getUsers().enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Ln.e("Failed: request = ${call.request()}, THROW: ${t.message}")
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Ln.e("Response ${response.body().toString()}")
//                Ln.d("Response: ${response.body()}")
            }

        })
    }
}
