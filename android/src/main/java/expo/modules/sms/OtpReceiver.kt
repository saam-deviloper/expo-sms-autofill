package expo.modules.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import expo.modules.kotlin.modules.Module

class OtpReceiver(private val module: Module) : BroadcastReceiver() {

  override fun onReceive(context: Context?, intent: Intent?) {
    if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
      val extras = intent.extras
      val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? Bundle
      val message = extras?.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String

      message?.let {
        val otp = Regex("\\d{4,6}").find(it)?.value
        otp?.let {
          module.sendEvent("onOtpReceived", mapOf("otp" to it))
        }
      }
    }
  }
}
