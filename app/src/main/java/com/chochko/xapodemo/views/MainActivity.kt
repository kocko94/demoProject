package com.chochko.xapodemo.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.GithubApiModel
import com.chochko.xapodemo.network.http.GithubHttpClient
import com.chochko.xapodemo.views.fragments.details.DetailsFragment
import com.github.benoitdion.ln.Ln
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DetailsFragment()).addToBackStack(null).commit()

        GithubHttpClient.githubHttpClient.getUsers().enqueue(object : Callback<GithubApiModel> {
            override fun onFailure(call: Call<GithubApiModel>, t: Throwable) {
                Ln.e("Failed: request = ${call.request()}, THROW: ${t.message}")
            }

            override fun onResponse(call: Call<GithubApiModel>, response: Response<GithubApiModel>) {
                Ln.e("Call ${call.request()}")
                Ln.e("Response ${response.body().toString()}")
//                Ln.d("Response: ${response.body()}")
            }

        })
    }
}
