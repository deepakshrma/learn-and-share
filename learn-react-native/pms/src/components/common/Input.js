import React, { Component } from 'react';
import { Text, View, TextInput } from 'react-native';

class Input extends Component {
    render(){
       const { label, value, placeholder, secureTextEntry, onChangeText } = this.props;
       const { inputStyle, labelStyle, containerStyle } = styles;
        return (
            <View style={ containerStyle }>
                <Text style={ labelStyle }>{ label }</Text>
                <TextInput
                    autoCorrect= { false }
                    placeholder= { placeholder }
                    value={ value }
                    secureTextEntry= { secureTextEntry }
                    onChangeText={ onChangeText }
                    style={ inputStyle }
                />
            </View>
        );
    }
}
const styles = {
    labelStyle: {
        fontSize: 18,
        flex: 1,
        paddingLeft: 20,
    },
    inputStyle: {
        color: '#000',
        paddingRight: 5,
        paddingLeft: 5,
        lineHeight: 23,
        fontSize: 18,
        flex: 2
    },
    containerStyle: {
        flex: 1,
        height:40,
        flexDirection: 'row',
        alignItems: 'center'
    }
}
export { Input };