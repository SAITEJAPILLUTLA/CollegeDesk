package chillar.epizy.collegedesk

//import com.google.firebase.storage.FirebaseStorage
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chillar.epizy.collegedesk.fragments.chatFragment
import chillar.epizy.collegedesk.fragments.feedFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpTabs()
    }
    private fun setUpTabs(){
        CoroutineScope(newSingleThreadContext("adding TABS")).launch {
            val adapter = viewPagerAdapter(supportFragmentManager)
            adapter.addFragment(chatFragment())
            adapter.addFragment(feedFragment())
            viewPager.adapter=adapter
            tabs.setupWithViewPager(viewPager)
            tabs.getTabAt(0)!!.setIcon(R.drawable.chat)
            tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_dynamic_feed_24)
        }
    }
}