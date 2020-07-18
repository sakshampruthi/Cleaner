package com.example.cleaner

import android.animation.Animator
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cleanImage.visibility = View.GONE

        autoStart.setOnClickListener {
            for (intent in AUTO_START_INTENTS){
                if (packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    startActivity(intent)
                    break
                }
            }
        }

        liteMode.setOnClickListener {
            brightness(0.8F)
            onclick()
        }
        smartMode.setOnClickListener{
            brightness(0.5F)
            onclick()
            kill()
            bluetooth()
        }
        extremeMode.setOnClickListener {
            brightness(0F)
            onclick()
            kill()
            bluetooth()
        }
    }

    private fun brightness(bright: Float){
        val layout = window.attributes
        layout.screenBrightness = bright
        window.attributes = layout
    }

    private fun kill(){
        val killBackground = KillBackground(this)
        killBackground.execute()
    }

    private fun onclick(){
        cleanLottie.visibility = View.VISIBLE
        cleanLottie.speed =2.0F
        cleanLottie.playAnimation()

        cleanLottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                cleanLottie.cancelAnimation()
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {
            }
        })

    }
     private fun bluetooth(){
         val adapter = BluetoothAdapter.getDefaultAdapter()
         if (adapter != null) {
             if (adapter.state == BluetoothAdapter.STATE_ON)
                 adapter.disable()
         }
     }
   companion object {
       private val AUTO_START_INTENTS = arrayOf(
           Intent().setComponent(
               ComponentName(
                   "com.samsung.android.lool",
                   "com.samsung.android.sm.ui.battery.BatteryActivity"
               )
           ),
           Intent("miui.intent.action.OP_AUTO_START").addCategory(Intent.CATEGORY_DEFAULT),
           Intent().setComponent(
               ComponentName(
                   "com.miui.securitycenter",
                   "com.miui.permcenter.autostart.AutoStartManagementActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.letv.android.letvsafe",
                   "com.letv.android.letvsafe.AutobootManageActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.huawei.systemmanager",
                   "com.huawei.systemmanager.optimize.process.ProtectActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.coloros.safecenter",
                   "com.coloros.safecenter.permission.startup.StartupAppListActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.coloros.safecenter",
                   "com.coloros.safecenter.startupapp.StartupAppListActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.oppo.safe",
                   "com.oppo.safe.permission.startup.StartupAppListActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.iqoo.secure",
                   "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.iqoo.secure",
                   "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.vivo.permissionmanager",
                   "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
               )
           ),
           Intent().setComponent(
               ComponentName(
                   "com.asus.mobilemanager",
                   "com.asus.mobilemanager.entry.FunctionActivity"
               )
           ).setData(
               Uri.parse("mobilemanager://function/entry/AutoStart")
           )
       )
   }
}