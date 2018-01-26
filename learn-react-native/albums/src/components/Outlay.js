import React from 'react';
import {
  Dimensions,
  StyleSheet,
  ScrollView,
  View,
  Image,
  Text,
} from 'react-native';
import AlbumList from './AlbumList';
import SongsList from './SongsList';
import LoginForm from './LoginForm';

export default function Outlay({ route }) {
    switch(route){
        case "songs":
            return <SongsList />;
        case "songs2": 
            return <SongsList />;
        default: 
            return <AlbumList />;
    }
}