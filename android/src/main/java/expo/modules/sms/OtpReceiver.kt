package expo.modules.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import expo.modules.kotlin.AppContext

class OtpReceiver(private val module: SmsModule) : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
      val extras = intent.extras
      val status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status?

      when (status?.statusCode) {
        CommonStatusCodes.SUCCESS -> {
          val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String?
          message?.let {
            // Extract OTP from message (you might need to adjust this regex)
            val otpRegex = "\\d{4,6}".toRegex()
            val otp = otpRegex.find(it)?.value
            otp?.let { code ->
              module.sendEvent("onOtpReceived", mapOf("otp" to code))
            }
          }
        }
        CommonStatusCodes.TIMEOUT -> {
          // Handle timeout
        }
      }
    }
  }
}