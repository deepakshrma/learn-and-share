import React, { Component } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

import {
  Dimensions,
  StyleSheet,
  ScrollView,
  View,
  Image,
  Text,
} from 'react-native';
import { Button } from 'react-native-elements';
import MenuItem from './MenuItem';

const window = Dimensions.get('window');
const uri = 'https://pickaface.net/gallery/avatar/Opi51c74d0125fd4.png';

const styles = StyleSheet.create({
  menu: {
    flex: 1,
    width: window.width,
    height: window.height,
    backgroundColor: 'gray',
    padding: 20,
  },
  avatarContainer: {
    marginBottom: 20,
    marginTop: 20,
  },
  avatar: {
    width: 48,
    height: 48,
    borderRadius: 24,
    flex: 1,
  },
  name: {
    position: 'absolute',
    left: 70,
    top: 20,
  },
  item: {
    fontSize: 14,
    fontWeight: '300',
    paddingTop: 5,
    borderColor: '#fff'
  },
});
export default class Menu extends Component {
  state = { menus: [] };
  componentWillMount() {
        axios.get('https://stark-stream-10851.herokuapp.com/menus')
            .then((response) => this.setState({ menus: response.data || [] }));
    }
  activeFeatures(onItemSelected){
    return this.state.menus.filter(x => x.active ).map( (menu) => {
      return (
        <MenuItem key={menu.path} onPress={ () => this.props.onItemSelected(menu.path)}>
            { menu.title }
        </MenuItem>
      );
    })
  }
  render() {
    return (
    <ScrollView scrollsToTop={false} style={styles.menu}>
      <View style={styles.avatarContainer}>
        <Image
          style={styles.avatar}
          source={{ uri }}
        />
        <Text style={styles.name}>Deepak S</Text>
      </View>
      {
        this.activeFeatures()
      }
    </ScrollView>
  );
  }
}
Menu.propTypes = {
  onItemSelected: PropTypes.func.isRequired,
};