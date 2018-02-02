import React, { Component } from 'react';
import { View, Text, ListView } from 'react-native';
import { connect } from 'react-redux';
import _ from 'lodash';
import { employeeFetch } from '../actions';
import ListItem from '../components/ListItem'

class EmployeeList extends Component {
    componentWillMount() {
        this.props.employeeFetch();
        this.createDataSource(this.props);
    }
    componentWillReceiveProps(newProps) {
        this.createDataSource(newProps);
    }
    createDataSource({ employees }) {
        const ds = new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2
        });
        this.dataSource = ds.cloneWithRows(employees)
    }
    renderRow(row) {
        return (<ListItem employee={row} />);
    }
    render() {
        return (
            <ListView
                enableEmptySections
                dataSource={this.dataSource}
                renderRow={this.renderRow}
            />
        );
    }
}
const mapStateToProp = (state) => {
    const employees = _.map(state.employees, (val, uid) => {
        return {
            ...val,
            uid
        }
    })
    return {
        employees
    }
}
export default connect(mapStateToProp, { employeeFetch })(EmployeeList);