import React, { Component } from 'react';
import {
    Text,
    TouchableWithoutFeedback,
    View,
    LayoutAnimation
} from 'react-native';
import { connect } from 'react-redux';
import { CardSection } from './common/index';
import * as actions from './../actions';
import { selectLibrary } from './../actions';

class ListItem extends Component {
    componentWillUpdate() {
        LayoutAnimation.easeInEaseOut();
    }
    renderDesc() {
        const { library, expended } = this.props;
        if (expended) {
            return (
                <CardSection>
                    <Text style={{ flex: 1 }}> {library.description} </Text>
                </CardSection>
            );
        }
    }
    render() {
        const { titleStyle } = styles;
        const { id, title } = this.props.library;
        return (
            <TouchableWithoutFeedback
                onPress={() => this.props.selectLibrary(id)}
            >
                <View>
                    <CardSection>
                        <Text style={titleStyle}>
                            {title}
                        </Text>
                    </CardSection>
                    {this.renderDesc()}
                </View>

            </TouchableWithoutFeedback>
        );
    }
}
const styles = {
    titleStyle: {
        fontSize: 18,
        paddingLeft: 15
    }
}
const mapStateToProps = (state, ownProps) => {
    const { library } = ownProps;
    return { expended: library.id === state.selectedLibraryId };
}
export default connect(mapStateToProps, actions)(ListItem);