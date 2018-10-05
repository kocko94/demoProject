package com.chochko.xapodemo.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chochko.xapodemo.R
import com.chochko.xapodemo.views.fragments.details.DetailsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DetailsFragment()).addToBackStack(null).commit()

//        GithubHttpClient.githubHttpClient.getUsers().enqueue(object : Callback<Any> {
//            override fun onFailure(call: Call<Any>, t: Throwable) {
//                Ln.e("Failed: request = ${call.request()}, THROW: ${t.message}")
//            }
//
//            override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                Ln.e("Response ${response.body().toString()}")
////                Ln.d("Response: ${response.body()}")
//            }
//
//        })
    }
}
