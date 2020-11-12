package chillar.epizy.collegedesk

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_permissions_avtivity.*

class permissionsAvtivity : AppCompatActivity() {
    val TAG="TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions_avtivity)
        button.setOnClickListener {
            requestPermissions()
        }

    }

    fun requestPermissions(){

        //Check Storage Permissions
        var lol = Manifest.permission.MANAGE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE), 1);
        }else{
            Log.d(TAG,"Storage Permissions are granted")
            progressBar2.visibility= View.GONE
            imageView.visibility=View.VISIBLE
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
        }else{
            Log.d(TAG,"Permissions are granted")
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !==
            PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1);
        }else{
            Log.d(TAG,"Permissions are granted")
        }

        val intent= Intent(this,HomeActivity::class.java)
        startActivity(intent,ActivityOptions.makeCustomAnimation(this,R.anim.slide_in_left,R.anim.slide_in_right).toBundle())
    }

}