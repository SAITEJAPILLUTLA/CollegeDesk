package chillar.epizy.collegedesk.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import chillar.epizy.collegedesk.R
import kotlinx.android.synthetic.main.permissions.view.*

class permissionsFragment  : DialogFragment() {
    val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    val TAG ="permissions"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View= inflater.inflate(R.layout.permissions,container,false)
        rootView.grantpermissions.setOnClickListener {
            permissions()
            dismiss()
        }
        return rootView
    }
    fun permissions(){
        requestPermissions()
        if (!checkPermissions()) {

                requestPermissions()
        }
    }
    fun checkPermissions(): Boolean {
        Log.d("HomeActivity","checkPermissions thread Started  ${Thread.currentThread().name}")
        val permissionState = context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
        return permissionState == PackageManager.PERMISSION_GRANTED
    }
    fun startLocationPermissionRequest() {
        Log.d("HomeActivity","startLocationPermissionRequest thread Started  ${Thread.currentThread().name}")
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }
    fun requestPermissions() {
        Log.d("HomeActivity","requestPermissions thread Started  ${Thread.currentThread().name}")

        val shouldProvideRationale = activity?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                it, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (shouldProvideRationale!!) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        }
        else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray    ) {
        Log.d("HomeActivity","onRequestPermissionsResult thread Started  ${Thread.currentThread().name}")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    Log.d(TAG,"LOCATION GRANTED")
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
    fun showSnackbar(mainTextStringId: String, actionStringId: String,listener: View.OnClickListener) {
        Toast.makeText(activity, mainTextStringId, Toast.LENGTH_LONG).show()
        Log.d("HomeActivity","showSnackbar thread Started  ${Thread.currentThread().name}")
    }

}