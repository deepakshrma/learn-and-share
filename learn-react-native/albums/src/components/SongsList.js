import React, { Component } from 'react';
import { Text, ScrollView } from 'react-native';
import axios from 'axios';
import { List, ListItem, Avatar } from 'react-native-elements'

export default class SongsList extends Component {
    state = { albums: [] };
    componentWillMount() {
        console.log('componentWillMount in album list');
        axios.get('https://stark-stream-10851.herokuapp.com/music_albums')
            .then((response) => this.setState({ albums: response.data || [] }));
    }
    renderRow (rowData, sectionID) {
        const { title, artist, thumbnail_image, image, url } = rowData;
	    return (
            <ListItem
            avatar={<Avatar
                        rounded
                        source={{uri: thumbnail_image}}
                        title={title}
                    />}
            key={title}
            title={title}
            subtitle={artist}
            />
        )
    }
    renderAlbums() {
        return (
            <List>
                {
                    this.state.albums.map((l, i) => (
                        this.renderRow(l)
                    ))
                }
            </List>
        );
    }
    render() {
        return (
            <ScrollView>{ this.renderAlbums() }</ScrollView>
        );
    }
}