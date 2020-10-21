package com.example.saketabi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.saketabi.data.LabelData
import com.example.saketabi.ui.home.HomeAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import java.io.ByteArrayOutputStream
import java.util.concurrent.ExecutorService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Firebase初期化
        Commons.auth = FirebaseAuth.getInstance()

        //Twitter初期化
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(this.getString(R.string.twitter_consumer_key),this.getString(R.string.twitter_consumer_secret)))
            .debug(true)
            .build()
        Twitter.initialize(config)

        //画像ライブラリFresco初期化
        Fresco.initialize(this)

        //権限の確認
        if(!allPermissionsGranted()){
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    //権限承認の確認
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    //権限承認の結果処理
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       if(requestCode == REQUEST_CODE_PERMISSIONS){
           if(!allPermissionsGranted()){
               Toast.makeText(this,"権限が承認されませんでした。",Toast.LENGTH_SHORT).show()
               finish()
           }
       }
    }

    //カメラ画面から戻った時の処理
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK){
            val args = Bundle()
            val capturedImage = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()
            capturedImage?.compress(Bitmap.CompressFormat.JPEG,100,stream)
            args.putByteArray("capture",stream.toByteArray())
            val navController = findNavController(R.id.navHostFragment)
            navController.navigate(R.id.action_homeFragment_to_addFragment,args)
        }
    }

    //以下画面遷移関数
    fun navigateFromSplashToLogin(){
        val navController = findNavController(R.id.navHostFragment)
        navController.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    fun navigateFromLoginToHome(){
        val navController = findNavController(R.id.navHostFragment)
        if(navController.currentDestination?.id == R.id.loginFragment)
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
    }

    fun navigateFromSplashToHome(){
        val navController = findNavController(R.id.navHostFragment)
        navController.navigate(R.id.action_splashFragment_to_homeFragment)
    }

    fun navigateFromHomeToCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE)
    }

    fun navigateFromAddToHome(){
        val navController = findNavController(R.id.navHostFragment)
        navController.navigate(R.id.action_addFragment_to_homeFragment)
    }

    fun navigateHomeToDetails(labelDatas : List<LabelData>, index : Int){
            val navController = findNavController(R.id.navHostFragment)
            val bundle = Bundle()
            val gson = Gson()
            val json = gson.toJson(labelDatas.toTypedArray(),Array<LabelData>::class.java)
        bundle.putString("data",json)
        bundle.putInt("index",index)
            navController.navigate(R.id.action_homeFragment_to_detailsFragment,bundle)
    }

    fun navigateFromDetailsToHome(){
        val navController = findNavController(R.id.navHostFragment)
        navController.navigate(R.id.action_detailsFragment_to_homeFragment)
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val REQUEST_CAPTURE_IMAGE = 229

        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }
}