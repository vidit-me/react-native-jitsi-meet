import { NativeModules, requireNativeComponent } from 'react-native';
import type { JitsiMeetType, JitsiMeetViewType } from './types';

const { JitsiMeet } = NativeModules;

const JitsiMeetView: JitsiMeetViewType =
  requireNativeComponent('JitsiMeetView');

export { JitsiMeetView };

export default JitsiMeet as JitsiMeetType;
