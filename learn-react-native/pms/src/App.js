/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {
  Component
} from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View
} from 'react-native';
import { Provider } from "react-redux";
import { createStore, applyMiddleware } from "redux";
import Reduxthunk from 'redux-thunk';
import firebase from "firebase";

import reducers from './reducers';

import LoginForm from './components/LoginForm';
import Router from './Router';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component<{}> {
  componentWillMount() {
    // Initialize Firebase
    var config = {
      apiKey: "AIzaSyBJ9u_2Ipp27A4Vtepibi0Sr6D2dvJ0k1Y",
      authDomain: "machineking-79690.firebaseapp.com",
      databaseURL: "https://machineking-79690.firebaseio.com",
      projectId: "machineking-79690",
      storageBucket: "machineking-79690.appspot.com",
      messagingSenderId: "191780701556"
    };
    firebase.initializeApp(config);
  }
  render() {
    const store = createStore(reducers, {}, applyMiddleware(Reduxthunk));
    return (
      <Provider store={ store }>
        <Router/>
      </Provider>
    );
  }
}