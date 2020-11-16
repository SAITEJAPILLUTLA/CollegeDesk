package chillar.epizy.collegedesk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.AccessController.getContext
class profileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        readUser()
        signOutbtn.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            val googleSignInClient = GoogleSignIn.getClient(this,gso)
            googleSignInClient.signOut()
            FirebaseAuth.getInstance().signOut()
            var db = dbhandler(this)
            db.signout()
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    fun readUser(){
        CoroutineScope(Dispatchers.IO).launch {
        var context: Context? = baseContext
        var db = context?.let { dbhandler(it) }
        var data = db?.fetchUser()
        if ((data != null)) {
            withContext(Dispatchers.Main) {
                Log.d("rdata", "addidataMethod" + data.size.toString() + "  adddata")
                gname.text= data.get(0).name
                gmail.text= data.get(0).email
                username.text=data.get(0).username
            }
        }}

    }

}