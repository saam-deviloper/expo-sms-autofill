import { useEffect, useState } from 'react';
import { SafeAreaView, Text, Button } from 'react-native';
import { NativeModules, NativeEventEmitter } from 'react-native';

const { ExpoSms } = NativeModules;
const smsEmitter = new NativeEventEmitter(ExpoSms);

export default function App() {
  const [otp, setOtp] = useState<string | null>(null);

  const startListening = () => {
    ExpoSms.startListening();
  };

  const addOtpListener = (callback: ({ otp }: { otp: string }) => void) => {
    return smsEmitter.addListener('onOtpReceived', callback);
  };

  useEffect(() => {
    startListening();
    const subscription = addOtpListener(({ otp }) => {
      setOtp(otp);
    });
    return () => subscription.remove();
  }, []);

  return (
    <SafeAreaView style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text style={{ fontSize: 20 }}>OTP: {otp ?? 'Waiting...'}</Text>
      <Button title="Trigger Native Test" onPress={() => console.log('Just a test')} />
    </SafeAreaView>
  );
}