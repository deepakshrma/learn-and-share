import React from 'react';
import { Image, Text, View } from 'react-native';
import Card from './Card';
import CardSection from './CardSection';
import Button from './Button';

export default AlbumDetail = ({ album }) => {
	const { title, artist, thumbnail_image, image } = album;
	const {
		thumbnailStyle,
		headerContentStyle,
		thumbnailContainerStyle,
		headerTextStyle,
		imageStyle
	} = styles;
	return (
		<Card>
			<CardSection>
				<View style={ thumbnailContainerStyle }>
					<Image
						style={ thumbnailStyle }
						source={ {uri: thumbnail_image }}
					/>
				</View>
				<View style={ headerContentStyle }>
					<Text style={ headerTextStyle }>{ title }</Text>
					<Text>{ artist }</Text>
				</View>
			</CardSection>

			<CardSection>
				<Image
					style={ imageStyle }
					source={ { uri: image }}
				/>
			</CardSection>

			<CardSection>
				<Button
					value={'Buy Song'}
					onPress={ () => console.log(title) }
				/>
			</CardSection>
		</Card>
	);
};
const styles = {
	headerContentStyle: {
		flexDirection: 'column',
		justifyContent: 'space-around'
	},
	headerTextStyle: {
		fontSize: 18
	},
	thumbnailStyle: {
		width: 50,
		height: 50
	},
	thumbnailContainerStyle: {
		justifyContent: 'center',
		alignItems: 'center',
		marginLeft: 10,
		marginRight: 10
	},
	imageStyle: {
		height: 300,
		flex: 1,
		width: null
	}
};