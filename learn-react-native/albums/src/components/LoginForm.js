import React, { Component } from 'react';
import { TextInput, Text } from 'react-native';
import firebase from 'firebase';
import { Button, Card, CardSection, Input } from './common';

class LoginForm extends Component {
    state = { email: '', password: '', error: '' };
    onButtonPress() {
        const { email, password } = this.state;
        this.setState({ error: '' });
        firebase.auth().signInWithEmailAndPassword(email, password)
            .catch((response) => {
                firebase.auth().createUserWithEmailAndPassword(email, password)
                    .catch((error) => {
                        this.setState({ error: 'Authantication Fails!!!' });
                    });
            });
    }
    render() {
        return (
            <Card>
                <CardSection>
                    <Input
                        label={'Email'}
                        placeholder={'deepak@gmail.com'}
                        value={this.state.email}
                        onChangeText={email => this.setState({ email })}
                    />
                </CardSection>
                <CardSection>
                    <Input
                        secureTextEntry
                        label={'Password'}
                        placeholder={'password'}
                        value={this.state.password}
                        onChangeText={password => this.setState({ password })}
                    />
                </CardSection>
                <Text style={styles.errorTextStyle}>{this.state.error}</Text>
                <CardSection>
                    <Button onPress={this.onButtonPress.bind(this)}>
                        Log In
                    </Button>
                </CardSection>
            </Card>
        );
    }
}
const styles = {
    errorTextStyle: {
        color: 'red',
        fontSize: 20,
        padding: 5,
        alignSelf: 'center'
    }
}
export default LoginForm;