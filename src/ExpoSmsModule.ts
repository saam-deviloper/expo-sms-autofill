import { NativeModule, requireNativeModule } from 'expo';

import { ExpoSmsModuleEvents } from './ExpoSms.types';

declare class ExpoSmsModule extends NativeModule<ExpoSmsModuleEvents> {
  PI: number;
  hello(): string;
  setValueAsync(value: string): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoSmsModule>('ExpoSms');
