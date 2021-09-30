import JitsiMeet, { JitsiMeetView } from '@bortolilucas/react-native-jitsimeet';
import React, { useState } from 'react';
import { StyleSheet, View, Pressable, Text } from 'react-native';

const conferenceOptions = {
  room: 'ReactNativeJitsiRoom',
  userInfo: {
    displayName: 'React Native Jitsi Meet Example',
    email: 'example@test.com',
    avatar: 'https://picsum.photos/200',
  },
  pipEnabled: false,
};

function App() {
  const [showJitsiView, setShowJitsiView] = useState(false);

  const startJitsiViewOnTop = () => {
    /* 
      Use it to start a Jitsi Meet View on top of RN Application (outside of JS).
      It doesn't require rendering JitsiMeetView Component.  
    */
    JitsiMeet.launch(conferenceOptions);
  };

  const startJitsiViewAsRNView = () => {
    setShowJitsiView(true);
  };

  const onConferenceTerminated = () => setShowJitsiView(false);

  if (showJitsiView) {
    /* 
      Use it to start a Jitsi Meet View as a RN View (inside JS).
    */
    return (
      <JitsiMeetView
        style={styles.jitsiMeetView}
        options={conferenceOptions}
        onConferenceTerminated={onConferenceTerminated}
      />
    );
  }

  return (
    <View style={styles.container}>
      <Pressable
        onPress={startJitsiViewOnTop}
        style={({ pressed }) => [
          styles.pressable,
          { opacity: pressed ? 0.5 : 1 },
        ]}
      >
        <Text style={styles.pressableText}>
          Start Jitsi on top of RN Application
        </Text>
      </Pressable>
      <Pressable
        onPress={startJitsiViewAsRNView}
        style={({ pressed }) => [
          styles.pressable,
          { opacity: pressed ? 0.5 : 1 },
        ]}
      >
        <Text style={styles.pressableText}>Start Jitsi as a RN View</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  pressable: {
    width: '80%',
    borderRadius: 15,
    height: 50,
    marginVertical: 10,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'blue',
  },
  pressableText: {
    fontSize: 17,
    fontWeight: 'bold',
    textAlign: 'center',
    color: '#fff',
  },
  jitsiMeetView: {
    flex: 1,
  },
});

export default App;
