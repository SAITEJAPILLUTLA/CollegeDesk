package chillar.epizy.collegedesk.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import chillar.epizy.collegedesk.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_feed.*
import java.io.File

class feedFragment: Fragment()  {
    var adapter: GroupAdapter<*> = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var context: Context? = getContext()
    }
    override fun onStart() {
        super.onStart()
        fragment_feed_recycler.setAdapter(adapter)

        button221.setOnClickListener {
            Log.d("Open","clicked")



            dir()

        }
    }
    fun dir(){
        val folder = File("/storage/emulated/0/","nona")
        folder.mkdir()
        textView13.text=Environment.getStorageDirectory().toString()
        textView15.text=folder.path
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()    }
}