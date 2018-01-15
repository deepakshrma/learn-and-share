import React, { Component } from 'react';
import { Text, View } from 'react-native';
import axios from 'axios';

export default class AlbumList extends Component {
    componentWillMount() {
        console.log('componentWillMount in album list');
        axios.get('https://rallycoding.herokuapp.com/api/music_albums')
            .then((data) => {
                console.log('>>>>>>', data);
            });
    }
    render() {
        return (
            <Text>Album List</Text>
        );
    }
}