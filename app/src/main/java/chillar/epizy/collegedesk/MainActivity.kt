package chillar.epizy.collegedesk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        val USER_KEY ="USER_KEY"
    }
    private var TAG:String = "MyActivity"
    //lateinit var auth: FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient:GoogleSignInClient
    var RC_SIGN_IN:Int = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        btngoolgesignin.setOnClickListener {
            createRequest()
            signIn()
            validate.visibility=View.VISIBLE
        }
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        Toast.makeText(baseContext, "firebaseAuth now",
            Toast.LENGTH_SHORT).show()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")

                    databaseAuthentication()
                    //updateUI(user)
                } else {
                    Log.d(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                }
                // ...
            }
    }
    public override fun onStart() {
        super.onStart()
        var user=auth.currentUser
        if(user!=null)
        {
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun createRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("601325558172-1eosuhik4evdjk45it3qasm5qmkj9qhb.apps.googleusercontent.com")
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    private fun databaseAuthentication(){
    textView6.text="Authenticating with DataBase...."
        val Uuid=FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/"+Uuid+"/profile")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {Log.d("DBerror","error from database $error")            }
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("ProfileMessage",snapshot.toString())
                val user =snapshot.getValue(User::class.java)
                if(FirebaseAuth.getInstance().uid== user?.uid){
                    val intent= Intent(this@MainActivity,HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    val intent= Intent(this@MainActivity,AddUserActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                //Log.d("profile","adapter started")
            }
        })

    }

}