package mobiledimension.exchangerates;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

public class NetworkChangeReceiver  extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        TextView textView = (TextView) rootView.findViewById(R.id.networkStateTextView);

        if(checkConnect(context))
        {
            textView.setText("");
        }else {
            textView.setText("Нет подключения. Будут получены только сохраненные в БД результаты");
        }
    }

    boolean checkConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if (cm != null){
            networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }else return false;
    }
}
