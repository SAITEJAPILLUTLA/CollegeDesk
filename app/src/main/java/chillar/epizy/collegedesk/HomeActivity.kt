package chillar.epizy.collegedesk

//import com.google.firebase.storage.FirebaseStorage
import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import chillar.epizy.collegedesk.fragments.chatFragment
import chillar.epizy.collegedesk.fragments.feedFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class HomeActivity : AppCompatActivity() {
    val TAG ="HomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpTabs()
        add.setOnClickListener {
            requestPermissions()
            }
        profile.setOnClickListener {
            val intent= Intent(this,HomeActivity::class.java)
            startActivity(intent,
                ActivityOptions.makeCustomAnimation(this,R.anim.bounce,R.anim.bounce).toBundle())
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
            Log.d(TAG,"Permissions are granted")
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
    }
    private fun setUpTabs(){
        CoroutineScope(newSingleThreadContext("adding TABS")).launch {
            val adapter = viewPagerAdapter(supportFragmentManager)
            adapter.addFragment(chatFragment(),"")
            adapter.addFragment(feedFragment(),"")
            viewPager.adapter=adapter
            tabs.setupWithViewPager(viewPager)
            tabs.getTabAt(0)!!.setIcon(R.drawable.chat)
            tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_dynamic_feed_24)
        }
    }
}