import { NativeModules } from 'react-native';

type JitsiMeetType = {
  multiply(a: number, b: number): Promise<number>;
};

const { JitsiMeet } = NativeModules;

export default JitsiMeet as JitsiMeetType;
