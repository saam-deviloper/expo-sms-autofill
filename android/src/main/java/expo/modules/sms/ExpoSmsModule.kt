package expo.modules.sms

import android.content.*
import android.os.Bundle
import android.util.Log
import com.facebook.react.bridge.*
import expo.modules.kotlin.modules.*
import expo.modules.kotlin.types.*
import android.app.Activity
import android.app.PendingIntent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.Task
import android.content.IntentFilter

class SmsModule : Module() {
  override fun definition() = ModuleDefinition {
    Name("ExpoSms")

    Events("onOtpReceived")

    Function("startListening") {
      val client = SmsRetriever.getClient(appContext.reactContext!!)
      val task = client.startSmsRetriever()

      task.addOnSuccessListener {
        Log.d("ExpoSms", "SMS Retriever started")
      }

      task.addOnFailureListener {
        Log.e("ExpoSms", "Failed to start SMS Retriever", it)
      }
    }

    OnCreate {
      val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
          if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Bundle
            val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String

            message?.let {
              val otp = Regex("\\d{4,6}").find(it)?.value
              otp?.let {
                sendEvent("onOtpReceived", mapOf("otp" to it))
              }
            }
          }
        }
      }

      val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
      appContext.reactContext?.registerReceiver(receiver, filter)
    }
  }
}
