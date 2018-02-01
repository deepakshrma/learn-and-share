import React from 'react';
import { Scene, Router, Actions } from 'react-native-router-flux';
import LoginForm from './components/LoginForm';
import EmployeeList from './components/EmployeeList';
import EmployeeCreate from './components/EmployeeCreate';

const Routercomponent = () => {
    return (
        <Router>
            <Scene key="root" hideNavBar>
                <Scene key="auth" initial>
                    <Scene 
                        key="login" 
                        component={ LoginForm } 
                        title="Please Login" 
                    />
                </Scene>
                <Scene key="main">
                    <Scene 
                        rightTitle="Add Me"
                        onRight={ () => Actions.employeeCreate() }
                        key="employeeList" 
                        component={ EmployeeList } 
                        title="Employees"
                        initial
                    />
                    <Scene 
                        key="employeeCreate" 
                        component={ EmployeeCreate } 
                        title="Create Employee"
                    />
                </Scene >
           </Scene>
        </Router>
    );
}
export default Routercomponent;