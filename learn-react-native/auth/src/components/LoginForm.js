import React, { Component } from 'react';
import { TextInput, Text } from 'react-native';
import firebase from 'firebase';
import { Button, Card, CardSection, Input, Spinner } from './common';

class LoginForm extends Component {
    state = { email: '', password: '', error: '', loading: false };
    onButtonPress() {
        const { email, password } = this.state;
        this.setState({ error: '', loading: true });
        firebase.auth().signInWithEmailAndPassword(email, password)
            .then(this.onLoginSuccess.bind(this))
            .catch((response) => {
                firebase.auth().createUserWithEmailAndPassword(email, password)
                    .then(this.onLoginSuccess.bind(this))
                    .catch(this.onLoginFail.bind(this));
            });
    }
    onLoginSuccess() {
        this.setState({ email: '', password: '', error: '', loading: false });
    }
    onLoginFail() {
        this.setState({ error: 'Authantication Fails!!!', loading: false });
    }
    renderButton() {
        if (this.state.loading) {
            return (<Spinner size="small" />);
        }
        return (<Button onPress={this.onButtonPress.bind(this)}>Log In</Button>);
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
                    {this.renderButton()}
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