import { NativeModules } from 'react-native';
import type { JitsiMeetInterface } from './types';

const { JitsiMeet } = NativeModules;

export default JitsiMeet as JitsiMeetInterface;
