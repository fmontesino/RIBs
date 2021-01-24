package com.badoo.ribs.android.integrationpoint

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.badoo.ribs.android.activitystarter.ActivityBoundary
import com.badoo.ribs.android.activitystarter.ActivityStarter
import com.badoo.ribs.android.permissionrequester.PermissionRequestBoundary
import com.badoo.ribs.android.permissionrequester.PermissionRequester

class ActivityIntegrationPoint(
    private val activity: AppCompatActivity,
    savedInstanceState: Bundle?,
    rootViewGroup: ViewGroup
) : IntegrationPoint(
    lifecycleOwner = activity,
    viewLifecycleOwner = MutableLiveData<LifecycleOwner>(activity),
    savedInstanceState = savedInstanceState,
    rootViewGroup = rootViewGroup
) {
    private val activityBoundary = ActivityBoundary(activity, requestCodeRegistry)
    private val permissionRequestBoundary = PermissionRequestBoundary(activity, requestCodeRegistry)

    override val activityStarter: ActivityStarter
        get() = activityBoundary
    override val permissionRequester: PermissionRequester
        get() = permissionRequestBoundary

    override val isFinishing: Boolean
        get() = activity.isFinishing

    override fun handleUpNavigation() {
        activity.onNavigateUp()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        activityBoundary.onActivityResult(requestCode, resultCode, data)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionRequestBoundary.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
