import React from 'react';
import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import JitsiMeet from 'react-native-jitsimeet';

export default function App() {
  const onPress = () => {
    JitsiMeet.launch({
      room: 'ReactNativeJitsiRoom',
      userInfo: {
        displayName: 'React Native Jitsi Meet Example',
        email: 'example@test.com',
        avatar: 'https://picsum.photos/200',
      },
    });
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={onPress} style={styles.pressable}>
        <Text style={styles.pressableText}>Launch</Text>
      </TouchableOpacity>
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
    width: '50%',
    borderRadius: 15,
    height: 50,
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
});
