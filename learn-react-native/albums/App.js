/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import { Image, StyleSheet, TouchableOpacity, View } from 'react-native';
/*
 const instructions = Platform.select({
 ios: 'Press Cmd+R to reload,\n' +
 'Cmd+D or shake for dev menu',
 android: 'Double tap R on your keyboard to reload,\n' +
 'Shake or press menu button for dev menu',
 });

 export default class App extends Component<{}> {
 render() {
 return (
 <View style={styles.container}>
 <Text style={styles.welcome}>
 Welcome to React Native!
 </Text>
 <Text style={styles.instructions}>
 To get started, edit App.js
 </Text>
 <Text style={styles.instructions}>
 {instructions}
 </Text>
 </View>
 );
 }
 }

 const styles = StyleSheet.create({
 container: {
 flex: 1,
 justifyContent: 'center',
 alignItems: 'center',
 backgroundColor: '#F5FCFF',
 },
 welcome: {
 fontSize: 20,
 textAlign: 'center',
 margin: 10,
 },
 instructions: {
 textAlign: 'center',
 color: '#333333',
 marginBottom: 5,
 },
 });
 */
import SideMenu from 'react-native-side-menu';
import firebase from 'firebase';

import Menu from './src/components/Menu';
import Outlay from './src/components/Outlay';
import LoginForm from './src/components/LoginForm';
import { Header, Button, Spinner } from './src/components/common';

const image = require('./assets/menu.png');

const styles = StyleSheet.create({
	button: {
		position: 'absolute',
		top: 20,
		padding: 10,
	},
	caption: {
		fontSize: 20,
		fontWeight: 'bold',
		alignItems: 'center',
	},
	container: {
		flex: 1,
		backgroundColor: '#F5FCFF',
	},
	welcome: {
		fontSize: 20,
		textAlign: 'center',
		margin: 10,
	},
	instructions: {
		textAlign: 'center',
		color: '#333333',
		marginBottom: 5,
	},
});

export default class App extends Component {
	constructor(props) {
		super(props);

		this.toggle = this.toggle.bind(this);

		this.state = {
			isOpen: false,
			selectedItem: 'reviews',
			loggedIn: null
		};
	}

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
			if (user) {
				this.setState({loggedIn: true});
			} else {
				this.setState({loggedIn: false});
			}
		});
	}

	toggle() {
		this.setState({
			isOpen: !this.state.isOpen,
		});
	}

	updateMenuState(isOpen) {
		this.setState({isOpen});
	}

	onMenuItemSelected = (item) => {
		this.setState({
			isOpen: false,
			selectedItem: item,
		});
	};
	renderContent() {
		const menu = <Menu onItemSelected={this.onMenuItemSelected}/>;
		switch(this.state.loggedIn){
			case true:
				return  (
					<SideMenu
						menu={menu}
						isOpen={this.state.isOpen}
						onChange={isOpen => this.updateMenuState(isOpen)}
					>
						<View style={styles.container}>
							<Header headerText={'Browsify Song'}/>
							<Outlay route={this.state.selectedItem}/>
						</View>
						<TouchableOpacity
							onPress={this.toggle}
							style={styles.button}
						>
							<Image
								source={image}
								style={{width: 32, height: 32}}
							/>
						</TouchableOpacity>
					</SideMenu>
				);
			case false:
				return <LoginForm></LoginForm>;
			default:
				return <Spinner size="large"/>
		}
	}
	render() {
		return this.renderContent();
	}
}
