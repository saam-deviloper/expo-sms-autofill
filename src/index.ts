// Reexport the native module. On web, it will be resolved to ExpoSmsModule.web.ts
// and on native platforms to ExpoSmsModule.ts
export { default } from './ExpoSmsModule';
export { default as ExpoSmsView } from './ExpoSmsView';
export * from  './ExpoSms.types';
