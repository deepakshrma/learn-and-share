import firebase from 'firebase';
import { EMPLOYEE_UPDATE, EMPLOYEE_CREATE } from "./types";
import { Actions } from 'react-native-router-flux';
import { Alert } from 'react-native';
import db from '../db';
const pushEmployee = (id, employee) => {
    return new Promise((resolve, reject) => {
        try {
            if (!db.get(`users.${id}`).value()) {
                db.set(`users.${id}`, { employees: {} }).write()
            }
            const employeeId = `${id}${+new Date()}`;
            const updatedEmployee = { [employeeId]: employee };
            db.set(`users.${id}.employees`, updatedEmployee).write();
            resolve(updatedEmployee);
        } catch (e) {
            reject(e)
        }
    })

}
export const employeeUpdate = ({ prop, value }) => {
    return {
        type: EMPLOYEE_UPDATE,
        payload: { prop, value }
    }
}
export const employeeCreate = ({ name, phone, shift }) => {
    const { currentUser } = firebase.auth();
    return (dispatch) => {
        pushEmployee(currentUser ? currentUser.uid : + new Date(), { name, phone, shift })
            .then((emp) => {
                dispatch({ type: EMPLOYEE_CREATE });
                Actions.main({ type: 'reset' });
            });
    };
}
//
            // Alert.alert(
            //     'Employee Details',
            //     JSON.stringify(emp),
            //     [
            //       {text: 'Ask me later', onPress: () => console.log('Ask me later pressed')},
            //       {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
            //       {text: 'OK', onPress: () => console.log('OK Pressed')},
            //     ],
            //     { cancelable: false }
            //   )