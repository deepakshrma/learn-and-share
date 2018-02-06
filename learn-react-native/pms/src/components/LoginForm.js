import React, {
    Component
} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View
} from 'react-native';
import { connect } from 'react-redux';
import { emailChanged, passwordChanged, loginUser } from '../actions';
import { Card, CardSection, Input, Button, Spinner } from './common';
import TestComponent from 'rn-core';

class LoginForm extends Component {
    onEmailChange(text) {
        this.props.emailChanged(text);
    }
    onPasswordChange(text) {
        this.props.passwordChanged(text);
    }
    onLoginUser() {
        const { email, password } = this.props;
        this.props.loginUser({ email, password });
    }
    renderError() {
        if(this.props.error){
            return (
            <View style={{backgroundColor: 'white'}}>
                <Text style={ styles.errorTextStyle }>
                    { this.props.error }
                </Text>
            </View>
            );
        }
    }
    renderContent() {
        if(this.props.loading){
            return <Spinner size="large"/>
        }
        return (
            <Button onPress={ this.onLoginUser.bind(this) }>login</Button>
        );
    }
    render() {
        return (
            <Card>
                <CardSection>
                    <TestComponent/>
                    <Input label="Email"
                        placeholder="John" 
                        onChangeText = {  this.onEmailChange.bind(this) }
                        value = { this.props.email }
                        />
                </CardSection>
                <CardSection>
                    <Input label="password"
                        secureTextEntry
                        placeholder="deepak@dbs.com" 
                        onChangeText = {  this.onPasswordChange.bind(this) }
                        value = { this.props.password }
                        />
                </CardSection>
                { this.renderError() }
                <CardSection>
                    { this.renderContent() }
                </CardSection>
            </Card>
        );
    }
}
const styles = {
    errorTextStyle: {
        fontSize: 20,
        alignSelf: 'center',
        color: 'red'
    }
}
const mapStateToProps = state => {
    return {
        email: state.auth.email,
        password: state.auth.password,
        error: state.auth.error,
        loading: state.auth.loading
    }
}
export default connect(mapStateToProps, { emailChanged, passwordChanged, loginUser })(LoginForm);