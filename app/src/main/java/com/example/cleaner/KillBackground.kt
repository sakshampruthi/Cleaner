package com.example.cleaner

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.util.Log
import java.lang.Exception

class KillBackground(private val context: Context) : AsyncTask<String, String, String>() {

    override fun doInBackground(vararg params: String?): String {
        var s:String
        try {
            val packages: List<ApplicationInfo>
            val pm: PackageManager = context.packageManager
            //get a list of installed apps.
            packages = pm.getInstalledApplications(0)

            val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val myPackage = context.applicationContext.packageName
            for (packageInfo in packages) {
                if (packageInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1)
                    continue
                if (packageInfo.packageName == myPackage)
                    continue
                activityManager.killBackgroundProcesses(packageInfo.packageName)
                Log.d("TAG", "doInBackground: Process Killed")
            }
            s="complete"
        }
        catch (e:Exception){ s = e.message.toString() }

        return s
    }
}