package chillar.epizy.collegedesk

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList


class User (var uid:String,var name:String,var username:String,var email: String?,var profileImageUrl :String ){
    constructor():this ("","","","","")
}
class viewPagerAdapter(supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
){
    private val mFragmentList= ArrayList<Fragment>()
    private val mFragmentTitleList= ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]}
    override fun getCount(): Int {
        return mFragmentList.size }
    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
    fun addFragment(fragment: Fragment){
        mFragmentList.add(fragment)
        //mFragmentTitleList.add(title)
    }

}

