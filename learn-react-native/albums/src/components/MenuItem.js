import React from 'react';
import { Text, TouchableOpacity } from 'react-native';

const MenuItem = ({ children, onPress}) => {
	const { buttonStyle, textStyle } = styles;
	return (
		<TouchableOpacity onPress={ onPress } style={ buttonStyle }>
			<Text style={ textStyle }>{ children }</Text>
		</TouchableOpacity>
	);
};

const styles = {
	textStyle: {
		color: '#000',
		fontSize: 18,
		fontWeight: '600',
		padding: 5
	},
	buttonStyle: {
		flex: 1,
		alignSelf: 'stretch',
		borderRadius: 5
	}
};

export default MenuItem;