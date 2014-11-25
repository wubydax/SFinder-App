/*
 * Copyright (c) 2014 Anna Berkovitch
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.sfinderstarter.dax.startsfinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;


public class StartSFinder extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startSFinder("com.samsung.android.app.galaxyfinder");
        finish();


    }




    public void startSFinder(String appName)
    {
        //try catch exeption in case tha app not installed
        try
        {
            Intent intent = new Intent("android.intent.action.MAIN");


            List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(intent, 0);

            for(ResolveInfo info : resolveInfoList)
                if(info.activityInfo.packageName.equalsIgnoreCase(appName))
                {
                    startApp(info.activityInfo.packageName, info.activityInfo.name);
                    return;
                }

            // If the app is not installed, throw a message
            Toast.makeText(this, R.string.not_installed, Toast.LENGTH_LONG).show();
            //Try to find and app on Google Play
            tryGooglePlay(appName);

        }
        catch (Exception e)
        {
            //Try to find an app on Google Play
            tryGooglePlay(appName);
        }
    }
    //Method to start an app in case it's installed
    private void startApp(String appName, String name)
    {
        Intent startApp = new Intent("android.intent.action.MAIN");
        startApp.setComponent(new ComponentName(appName, name));
        startActivity(startApp);
    }

    //Method to try and find and app on Google play
    private void tryGooglePlay(String appName)
    {
        Intent tryOnPlay = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName));
        startActivity(tryOnPlay);
    }
}
