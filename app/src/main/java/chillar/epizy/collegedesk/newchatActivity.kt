package chillar.epizy.collegedesk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import chillar.epizy.collegedesk.fragments.chatFragment
import chillar.epizy.collegedesk.fragments.feedFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_newchat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

class newchatActivity : AppCompatActivity() {

    val TAG ="newchatActivity"
    var adapter: GroupAdapter<*> = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newchat)
        searchhere.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var searchStr=p0.toString().toLowerCase()
                var currentUser =FirebaseAuth.getInstance().currentUser?.uid
                val nameref=FirebaseDatabase.getInstance().reference
                    .child("authenticate").orderByChild("name")
                    .startAt(searchStr)
                    .endAt(searchStr+"\uf8ff")
                val usernameref=FirebaseDatabase.getInstance().reference
                    .child("authenticate").orderByChild("name")
                    .startAt(searchStr)
                    .endAt(searchStr+"\uf8ff")
                nameref.addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user=snapshot.getValue(User::class.java)
                        if (user != null) {
                            if (!(user.uid).equals(currentUser))
                                adapter.add(user)
                            adapter.setOnItemClickListener { item, view ->


                            }

                        }
                    }

                })
                usernameref.addValueEventListener(object :ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user=snapshot.getValue(User::class.java)
                        if (user != null) {
                            if (!(user.uid).equals(currentUser))
                                adapter.add(user)
                            adapter.setOnItemClickListener { item, view ->


                            }

                        }
                    }

                })
            }
        })
        CoroutineScope(newSingleThreadContext("searchUser")).launch {


            //
        }
    }

}