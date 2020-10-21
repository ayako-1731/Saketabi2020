package com.example.saketabi.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.saketabi.Commons
import com.example.saketabi.MainActivity
import com.example.saketabi.R
import com.example.saketabi.view.CustomTwitterLoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import java.util.regex.Pattern


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Commons.user != null) {
            Commons.auth.signOut()
            Commons.user = null
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity)
            parentActivity = context
    }

    lateinit var parentActivity : MainActivity
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        //Twitterログイン
        view.findViewById<CustomTwitterLoginButton>(R.id.twitterLoginButton).callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                val twitterSession = result?.data
                if (twitterSession != null) {
                    val credential = TwitterAuthProvider.getCredential(
                        twitterSession.authToken.token,
                        twitterSession.authToken.secret
                    )
                    Commons.auth.signInWithCredential(credential).addOnCompleteListener {
                        Commons.user = it.result?.user
                        parentActivity.navigateFromLoginToHome()
                    }
                }
            }

            override fun failure(exception: TwitterException?) {
            }
        }

        //Googleログイン
        view.findViewById<MaterialButton>(R.id.googleLoginButton).setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(requireActivity().getString(R.string.web_client_id))
                .build()

            mGoogleSignInClient = GoogleSignIn.getClient(parentActivity,gso)
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent,
                RC_SIGN_IN
            )
        }

        //新規アカウント作成
        view.findViewById<MaterialButton>(R.id.signUpButton).setOnClickListener {
            val emailBox = view.findViewById<TextInputEditText>(R.id.mailAdress)
            val email = emailBox.text.toString()
            val passwordBox = view.findViewById<TextInputEditText>(R.id.password)
            val password = passwordBox.text.toString()
            if(password.length < 8){
                Toast.makeText(parentActivity,"パスワードは８文字以上で構成される必要があります。",Toast.LENGTH_SHORT)
                return@setOnClickListener
            }
            Commons.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(parentActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Commons.user = Commons.auth.currentUser
                        parentActivity.navigateFromLoginToHome()
                    } else {
                        Toast.makeText(parentActivity, "アカウントの作成に失敗しました。",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        //メールアドレスでのログイン
        view.findViewById<MaterialButton>(R.id.loginButton).setOnClickListener {
            val emailBox = view.findViewById<TextInputEditText>(R.id.mailAdress)
            val email = emailBox.text.toString()
            val passwordBox = view.findViewById<TextInputEditText>(R.id.password)
            val password = passwordBox.text.toString()
            if(email == "" || password == "")
                Toast.makeText(parentActivity, "ログインに失敗しました。",
                    Toast.LENGTH_SHORT).show()
            else
            Commons.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(parentActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Commons.user = Commons.auth.currentUser
                        parentActivity.navigateFromLoginToHome()
                    } else {
                        Toast.makeText(parentActivity, "ログインに失敗しました。",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        //パスワードリセット
        view.findViewById<TextView>(R.id.forgotPasswordButton).setOnClickListener {
            val emailAddress = view.findViewById<TextInputEditText>(R.id.mailAdress).text.toString()
            if(!Pattern.compile("^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+\$").matcher(emailAddress).find())
            {
                Toast.makeText(requireContext(),"有効なメールアドレスを入力してください",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Commons.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(),"メールを送信しました",Toast.LENGTH_SHORT).show()
                    }
                }
        }



        return view
    }





    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        view?.findViewById<CustomTwitterLoginButton>(R.id.twitterLoginButton)?.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            //try{
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!,null)
                Commons.auth.signInWithCredential(credential)
                    .addOnCompleteListener(requireActivity()) {
                        task ->
                        if(task.isSuccessful){
                            parentActivity.navigateFromLoginToHome()
                        }
                        else
                            Toast.makeText(requireContext(),"ログインに失敗しました",Toast.LENGTH_SHORT).show()
                    }
            /*}catch(e:ApiException){

            }*/
        }
    }

    companion object{
        private const val RC_SIGN_IN = 123
    }
}