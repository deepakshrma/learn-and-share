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
      apiKey: "AIzaSyAxUaengjxB0FUtx0bnNjx7B177ynVDiLg",
      authDomain: "authentication-efe52.firebaseapp.com",
      databaseURL: "https://authentication-efe52.firebaseio.com",
      projectId: "authentication-efe52",
      storageBucket: "authentication-efe52.appspot.com",
      messagingSenderId: "415146363862"
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