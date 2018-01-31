import firebase from 'firebase';
import { EMPLOYEE_UPDATE, EMPLOYEE_CREATE } from "./types";
import { Actions } from 'react-native-router-flux';
import { Alert } from 'react-native';

export const employeeUpdate = ({ prop, value }) => {
    return {
        type: EMPLOYEE_UPDATE,
        payload: { prop, value }
    }
}
export const employeeCreate = ({ name, phone, shift }) => {
    const { currentUser } = firebase.auth();
    return () => {
        // Actions.login()
        Alert.alert(
            [name, phone, shift].join('  '),
            currentUser.uid,
            [
              {text: 'Ask me later', onPress: () => console.log('Ask me later pressed')},
              {text: 'Cancel', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
              {text: 'OK', onPress: () => console.log('OK Pressed')},
            ],
            { cancelable: false }
          )
        firebase.database().ref(`/users/${currentUser.uid}/employees`)
            .push({ name, phone, shift })
            .then(() =>  Actions.employeeList())
            .catch((error) => {
                Actions.employeeList()
            });
    }
}