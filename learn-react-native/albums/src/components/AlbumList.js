import React, { Component } from 'react';
import { Text, ScrollView } from 'react-native';
import axios from 'axios';
import AlbumDetail from './AlbumDetail';

export default class AlbumList extends Component {
    state = { albums: [] };
    componentWillMount() {
        console.log('componentWillMount in album list');
        axios.get('https://stark-stream-10851.herokuapp.com/music_albums')
            .then((response) => this.setState({ albums: response.data || [] }));
    }
    renderAlbums() {
        return this.state.albums.map( album => <AlbumDetail key={ album.title } album={ album }/>)
    }
    render() {
        return (
            <ScrollView>{ this.renderAlbums() }</ScrollView>
        );
    }
}