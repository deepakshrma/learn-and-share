import React from 'react';
import { View, Text } from 'react-native';
import Card from './Card';

export default AlbumDetail = (props) => (
	<Card>
		<Text>{ props.album.title }</Text>
	</Card>
);