package chillar.epizy.collegedesk

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.StringBuilder
import java.security.AccessController.getContext
import java.util.*


class AddUserActivity : AppCompatActivity() {
    lateinit var bitmap:Bitmap
    val TAG ="TAG"

   companion object{

   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        avatarphoto.setBackgroundResource(R.drawable.ic_baseline_account_circle_24)
        avatarbtn.setOnClickListener{
            Log.d("profile","msg uploading")
            var intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type ="image/*"
            //intent.type ="csv/*"
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent,0)
        }
        var signinAccount= GoogleSignIn.getLastSignedInAccount(this)
        if(signinAccount!=null)
        {
            //gname.text=signinAccount.displayName
            textView2.text= signinAccount.displayName.toString()
            //gmail.text=signinAccount.email
            textView4.text=signinAccount.email.toString()
            //Picasso.get().load(signinAccount.photoUrl).into(profileimage);
            //imgProfile.setImageResource(signinAccount.photoUrl)
        }
        join.setOnClickListener {
            loading.visibility=View.VISIBLE
            join.isClickable=false
            try {
                uploadavatar(bitmap)
            }catch (e : Exception){
                saveUserToFirebase("")
            }
        }
    }
    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            var source=data.data
            if (source != null) {
                    launchCrop(source)
                }
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            var resultUri = data?.let { UCrop.getOutput(it) };
             bitmap= MediaStore.Images.Media.getBitmap(contentResolver, resultUri)
            avatarphoto.setImageBitmap(bitmap)
        } else if (resultCode == UCrop.RESULT_ERROR) {
            var error  = data?.let { UCrop.getError(it) };
            Log.d(TAG,"error in croping :: ${error}")
        }
    }
    private fun launchCrop(suri: Uri) {
        var durii=StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString()
        var image=File.createTempFile("cacheDir.toString()",durii)
        Log.d("image","source uri of image is :${suri}")
        Log.d("image","temp uri of new image is :${Uri.fromFile(image)}")
        UCrop.of(suri,Uri.fromFile(image))
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(880  , 880)
            .start(this);
    }
    private fun uploadavatar(finalbitmap: Bitmap){
        textView6.text="Uploading Profile Image...."
        var filename= FirebaseAuth.getInstance().uid.toString()
        var ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        val result = Bitmap.createScaledBitmap(finalbitmap, 400, 400, false)
        val baos = ByteArrayOutputStream()
        result.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        ref.putBytes(data)
            .addOnSuccessListener {
                Log.d("progile","Image Uploaded Sucessfully at ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("ImageActivity","File Location $it")
                    saveUserToFirebase(it.toString())
                }
            }
    }
    private fun saveUserToFirebase(url:String){
        val context =baseContext
        CoroutineScope(newSingleThreadContext("uploadtoFirebase")).launch {
        textView6.text="Creating Account..."
        val uid= FirebaseAuth.getInstance().uid?:""
        var signinAccount= GoogleSignIn.getLastSignedInAccount(context)
        var name= signinAccount?.displayName.toString()
        var email= signinAccount?.email.toString()
        var profileImageUrl:String="https://firebasestorage.googleapis.com/v0/b/carpool-1ec9c.appspot.com/o/images%2F7adf0706-5378-4e35-88e9-83578d0d8523?alt=media&token=25bd6495-dadf-4872-a501-ed84ad37a8f9"
        var ref= FirebaseDatabase.getInstance().getReference("/users/"+uid+"/profile")
        var Authref= FirebaseDatabase.getInstance().getReference("/authenticate/"+uid+"/profile")
        var username=username.text.toString()
        var user=User()
        if (url!=null){
             user = User(uid,name,username,email,url)
        }else{
             user = User(uid,name,username,email,profileImageUrl)
        }
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Profile","user Saved to Firebasee")
                //llProgressBar.visibility = View.GONE
                Toast.makeText(baseContext, "Updated :)", Toast.LENGTH_SHORT).show()
                saveUsertolocalDB(user)
            }
        textView6.text="Finishing Up..."
        Authref.setValue(user)
            .addOnSuccessListener {
                Log.d("Profile","user Saved to Firebasee")
                //llProgressBar.visibility = View.GONE
                Toast.makeText(baseContext, "Updated :)", Toast.LENGTH_SHORT).show()
                loading.visibility=View.GONE

                val intent= Intent(context,permissionsAvtivity::class.java)
                startActivity(intent,
                    ActivityOptions.makeCustomAnimation(context,R.anim.slide_in_left,R.anim.slide_in_right).toBundle())

            }
    }}
    fun saveUsertolocalDB(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            var context: Context? = baseContext

            var db = context?.let { dbhandler(it) }
            db?.insertUser(user)
        }

    }
}