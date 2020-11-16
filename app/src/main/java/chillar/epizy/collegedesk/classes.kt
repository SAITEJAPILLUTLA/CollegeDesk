package chillar.epizy.collegedesk

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xwray.groupie.ViewHolder
import java.util.ArrayList


class User (var uid:String,var name:String,var username:String,var email: String?,var profileImageUrl :String ): com.xwray.groupie.Item<ViewHolder>()
{
    constructor():this ("","","","","")

    override fun getLayout(): Int {
        return R.layout.userbar
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}


class viewPagerAdapter(supportFragmentManager: FragmentManager):FragmentPagerAdapter(supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val mFragmentList=ArrayList<Fragment>()
    private val mFragmentTitleList=ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]}

    override fun getCount(): Int {
        return mFragmentList.size }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
    fun addFragment(fragment: Fragment,title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

}


/*
public class post(var currentID:String,
                  var AuthUID:String,
                  var username:String?,
                  var email:String?,
                  var photoUrl:String?,
                  var year: String,
                  var month:String,
                  var day:String,
                  var fromLat: Double,
                  var fromLng: Double,
                  var fromAddress:String,
                  var toLat: Double,
                  var toLng: Double,
                  var ToAddress:String,
                  var seats: Int,
                  var vehicle:String,
                  var point1Lat :Double,
                  var point1Lng :Double,
                  var point2Lat :Double,
                  var point2Lng :Double,
                  var point1Address :String,
                  var point2Address :String,
                  var timestamp:Long ): com.xwray.groupie.Item<ViewHolder>() {
    constructor() : this("","","","","","","","",0.0,0.0,"",0.0,0.0,
        "",0,"",0.0,0.0,0.0,0.0,"","",-1)

    override fun bind(viewHolder: ViewHolder, position: Int) {
        Log.d("TAG","NOW IN POST")
        //val faddrsses =
        //            geocoder.getFromLocation(fromLat,fromLng, 1)
        //        Log.d("adress","get u ::"+faddrsses)
        //        val fcityName = faddrsses[0].getAddressLine(0)
        //        val fstateName = faddrsses[0].getAddressLine(1)
        //        val fcountryName = faddrsses[0].getAddressLine(2)
//        viewHolder.itemView.publicto0.text=ToAddress
//        viewHolder.itemView.publicfrom0.text=fromAddress
        viewHolder.itemView.keyview.text=currentID
        Log.d("TAG","CURRENT ID is "+currentID)

    }
    override fun getLayout(): Int {   return R.layout.testfile    }
*/


