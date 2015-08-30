package be.sushi;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: 30/04/12
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import be.sushi.util.AndroidHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SMSReceiver extends BroadcastReceiver {
  public static String trigger_message = "";
  public final String TAG = "SMSRECEIVER";

  @Override
  public void onReceive(Context context, Intent intent) {
    //---get the SMS message passed in---
    Bundle bundle = intent.getExtras();
    SmsMessage[] msgs = null;
    String str = "";
    if (bundle != null) {
      //---retrieve the SMS message received---
      Object[] pdus = (Object[]) bundle.get("pdus");
      msgs = new SmsMessage[pdus.length];
      for (int i = 0; i < msgs.length; i++) {
        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        str += "SMS from " + msgs[i].getOriginatingAddress();
        str += " :";
        str += msgs[i].getMessageBody().toString();
        str += "\n";
        //---display the new SMS message---
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("number", msgs[i].getOriginatingAddress()));
        nameValuePairs.add(new BasicNameValuePair("message", "v1r7u4l5u5h1 "+msgs[i].getMessageBody().toString()));
        Log.d(TAG, str);
        sendToServer(context, nameValuePairs);
      }
    }
  }

  private void sendToServer(Context context, List<NameValuePair> nameValuePairs){

      //api4bel.appspot.com/application/pushWeather/nl
      URI uri = null;
      if(AndroidHttpClient.haveInternet(context)){
        try {
          //uri = URIUtils.createURI("http", "api4bel.appspot.com", -1, "/sms/listen", "", null);
          uri = URIUtils.createURI("http", "2.api4bel.appspot.com", -1, "/sms/listen", "", null);
          //uri = URIUtils.createURI("http", "192.168.0.11", 9000, "/sms/listen", "", null);
          HttpPost post = new HttpPost(uri);
          post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
          AndroidHttpClient client = AndroidHttpClient.newInstance("");
          BufferedReader r;
          HttpResponse response = client.execute(post);
          r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
          Log.d(TAG, "RESPONSE = "+r.readLine());
        } catch (Exception e){
          Log.e("TAG", "WARNING HERE");
        }
      }
  }
}
