import React from 'react';
import { Image, Text, View } from 'react-native';
import Card from './Card';
import CardSection from './CardSection';

export default AlbumDetail = ({ album }) => {
	const { title, artist, thumbnail_image} = album;
	const { thumbnailStyle, headerContentStyle } = styles;
	return (
		<Card>
			<CardSection>
				<View>
					<Image
						style={ thumbnailStyle }
						source={ {uri: thumbnail_image }}
					/>
				</View>
				<View style={ headerContentStyle }>
					<Text>{ title }</Text>
					<Text>{ artist }</Text>
				</View>
			</CardSection>
		</Card>
	);
};
const styles = {
	headerContentStyle: {
		flexDirection: 'column',
		justifyContent: 'space-around'
	},
	thumbnailStyle: {
		width: 50,
		height: 50
	}
};