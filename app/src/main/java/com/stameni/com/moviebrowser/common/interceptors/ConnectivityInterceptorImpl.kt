package com.stameni.com.moviebrowser.common.interceptors

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(private val context: Context) :
    ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline()){
            throw NoInternetConnectionException()
        }else{
            return chain.proceed(chain.request())
        }
    }

    private fun isOnline(): Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}

class NoInternetConnectionException : Exception()
