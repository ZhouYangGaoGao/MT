package com.matao.utils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PackageChangeReceiver extends BroadcastReceiver {

      @Override
      public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
        final String packageName = intent.getData().getSchemeSpecificPart();
        final boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
        // 通知对应的应用
        Intent notifyIntent = new Intent("com.app.action.notifier");
        notifyIntent.setPackage(packageName);
        notifyIntent.putExtra("action", action);
        notifyIntent.putExtra("replace", replacing);
            context.sendBroadcast(notifyIntent);
      }

}