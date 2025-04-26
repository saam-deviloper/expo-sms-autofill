package expo.modules.sms

import android.content.IntentFilter
import com.google.android.gms.auth.api.phone.SmsRetriever
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class SmsModule : Module() {
  private var otpReceiver: OtpReceiver? = null

  override fun definition() = ModuleDefinition {
    Name("ExpoSms")

    Events("onOtpReceived")

    Function("startListening") {
      val client = SmsRetriever.getClient(appContext.reactContext!!)
      client.startSmsRetriever()
    }

    OnCreate {
      otpReceiver = OtpReceiver(this)
      val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
      appContext.reactContext?.registerReceiver(otpReceiver, filter)
    }

    OnDestroy {
      otpReceiver?.let {
        appContext.reactContext?.unregisterReceiver(it)
      }
    }
  }
}
