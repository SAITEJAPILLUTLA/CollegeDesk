package chillar.epizy.collegedesk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_user.*

class AddUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        avatarbtn.setOnClickListener{
            Log.d("profile","msg uploading")
            var intent = Intent(Intent.ACTION_PICK)
            intent.type ="image/*"
            //intent.type ="csv/*"
            startActivityForResult(intent,0)
        }
    }



    private fun saveUserToFirebase(){
        val uid= FirebaseAuth.getInstance().uid?:""
        var signinAccount= GoogleSignIn.getLastSignedInAccount(this)
        var name= signinAccount?.displayName.toString()
        var email= signinAccount?.email.toString()
        var profileImageUrl:String="https://firebasestorage.googleapis.com/v0/b/carpool-1ec9c.appspot.com/o/images%2F7adf0706-5378-4e35-88e9-83578d0d8523?alt=media&token=25bd6495-dadf-4872-a501-ed84ad37a8f9"
        var ref= FirebaseDatabase.getInstance().getReference("/users/"+uid+"/profile")
        val user = User(uid,name,email,profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Profile","user Saved to Firebasee")
                //llProgressBar.visibility = View.GONE
                Toast.makeText(baseContext, "Updated :)", Toast.LENGTH_SHORT).show()
            }
    }


}