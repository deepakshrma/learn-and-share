import React from 'react';
import './Person.css';

const person = (props) => {
    console.log('>>>>>>prop updated...');
    return (
        <div className="Person">
            <p>I am {props.name} and i am {props.age} year old.</p>
            <input type="text" onChange={props.changed} value={props.name}/>
            <p onClick={props.click} >{props.children}</p>
        </div>
    );
}
export { person as Person };