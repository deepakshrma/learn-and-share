import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Alert } from 'react-native';
import _ from 'lodash';
import { Card, CardSection, Button } from './common';

import { employeeUpdate } from '../actions';
import EmployeeForm from './EmployeeForm';

class EmployeeEdit extends Component {
    componentWillMount() {
        _.each(this.props.employee, (val, prop) => {
            this.props.employeeUpdate({ prop, val })
        });
    }
    onButtonPress() {
        const { name, phone, shift } = this.props;
        this.props.employeeCreate({ name, phone, shift: shift || 'Monday' })
    }
    render() {
        return (
            <Card>
                <EmployeeForm {...this.props} />
                <CardSection>
                    <Button onPress={this.onButtonPress.bind(this)}>Save</Button>
                </CardSection>
            </Card>
        );
    }
}
const mapStateToProp = state => {
    const { name, phone, shift } = state.employeeForm
    Alert.alert(
                'Error!!!! ',
                JSON.stringify(state.employeeForm),
                [{text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'}],
                { cancelable: false }
            );
    return { name, phone, shift }
}
export default connect(mapStateToProp, { employeeUpdate })(EmployeeEdit);