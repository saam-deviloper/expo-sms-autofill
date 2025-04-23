import { registerWebModule, NativeModule } from 'expo';

import { ExpoSmsModuleEvents } from './ExpoSms.types';

class ExpoSmsModule extends NativeModule<ExpoSmsModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit('onChange', { value });
  }
  hello() {
    return 'Hello world! 👋';
  }
}

export default registerWebModule(ExpoSmsModule);
