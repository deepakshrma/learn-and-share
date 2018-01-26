import React, { Component } from 'react';
import { View, StyleSheet } from 'react-native';
import firebase from 'firebase';
import { Header, Button, Spinner } from './components/common';
import LoginForm from './components/LoginForm';

class App extends Component {
    state = { loggedIn: null };
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
        firebase.auth().onAuthStateChanged((user) => {
            if(user){
                this.setState({ loggedIn: true });
            }else {
                this.setState({ loggedIn: false });
            }
        });
    }
    renderContent() {
        switch(this.state.loggedIn){
            case true:
        return  (
            <Button onPress={ ()=> firebase.auth().signOut() }>
                Logout
            </Button>);
            case false: 
            return <LoginForm></LoginForm>;
            default: 
            return <Spinner size="large"/>
        }
    }
    render(){
        return (
            <View>
                <Header headerText="Auth App" />
                { this.renderContent() }
            </View>
        );
    }
}

export default App;