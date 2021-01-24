package com.badoo.ribs.android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.badoo.ribs.android.activitystarter.ActivityStarter
import com.badoo.ribs.android.dialog.DialogLauncher
import com.badoo.ribs.android.integrationpoint.FragmentIntegrationPoint
import com.badoo.ribs.android.permissionrequester.PermissionRequester
import com.badoo.ribs.android.store.RetainedInstanceStoreViewModel
import com.badoo.ribs.core.Rib

/**
 * Helper class for root [Rib] integration.
 *
 * Also offers base functionality to satisfy dependencies of Android-related functionality
 * down the tree:
 * - [DialogLauncher]
 * - [ActivityStarter]
 * - [PermissionRequester]
 *
 * Feel free to not extend this and use your own integration point - in this case,
 * don't forget to take a look here what methods needs to be forwarded to the root Node.
 */
abstract class RibFragment : Fragment() {

    private val retainedInstanceViewModel by viewModels<RetainedInstanceStoreViewModel>()

    lateinit var integrationPoint: FragmentIntegrationPoint
        protected set

    abstract fun createRib(savedInstanceState: Bundle?): Rib

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        integrationPoint = FragmentIntegrationPoint(
            fragment = this,
            savedInstanceState = savedInstanceState
        )
        retainedInstanceViewModel // initialize

        val root = createRib(savedInstanceState)
        integrationPoint.attach(root)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FrameLayout(inflater.context)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        integrationPoint.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        integrationPoint.onLowMemory()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        integrationPoint.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        integrationPoint.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
