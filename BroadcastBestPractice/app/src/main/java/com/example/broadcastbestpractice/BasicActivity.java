package com.example.broadcastbestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BasicActivity extends AppCompatActivity {

    private ForceOfflineReceiver receiver;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityManager.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intent_filter = new IntentFilter();
        intent_filter.addAction("com.example.bestpractice.FORCE_OFFLINE");
        receiver =  new ForceOfflineReceiver();
        registerReceiver(receiver, intent_filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver {
        public void onReceive(final Context context, Intent intent){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("You are forced to be offline. Please try to login again.");
            builder.setCancelable(false);   // set the dialog to be not be canceled
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityManager.finishall();    // destroy all activities
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    context.startActivity(intent1); //  restart login activity
                }
            });
            builder.show();
        }


    }
}

