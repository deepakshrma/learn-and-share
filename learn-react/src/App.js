import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { Person } from './Person';

class App extends Component {
  state = {
    showPersons: true,
    persons: [
      { id: '123341', name: 'Deepak', age: '28' },
      { id: '123421', name: 'Raj', age: '30' },
      { id: '122341', name: 'Sanjay', age: '34' },
    ]
  }
  switchNameHandler = (newname) => {
    // this.state.persons[0].name = 'changed name';
    this.setState({
      persons: [
        { name: newname, age: '28' },
        { name: 'Raj', age: '30' },
        { name: 'Sanjay', age: '24' }
      ]
    })
  }
  togglePersonsHandler = () => {
    this.setState({
      showPersons: !this.state.showPersons
    });
  }
  nameChangedhandler = (event, id) => {
    const personIndex = this.state.persons.findIndex(p => p.id === id);
    const person = {
      ...this.state.persons[personIndex]
    };
    person.name = event.target.value;
    const persons = [...this.state.persons];
    persons[personIndex] = person;
    this.setState({
      persons: persons
    });
  }
  deletePersonHandler(personIndex) {
    // const persons = this.state.persons.splice(personIndex, 1);
    const persons = this.state.persons.slice();
    persons.splice(personIndex, 1);
    this.setState({ persons: persons });
  }
  renderPersons() {
    if (!this.state.showPersons) {
      return null;
    }
    return this.state.persons.map((person, index) => {
      return (<Person
        key={person.id}
        name={person.name}
        age={person.age}
        changed={(event) => this.nameChangedhandler(event, person.id)}
        click={() => this.deletePersonHandler(index)}  //click={() => this.switchNameHandler(person.name)}
      >Missing data</Person>
      );
    })
    /* 
    return (
      <div >
        <Person name={this.state.persons[0].name} age={this.state.persons[0].age} />
        <Person name={this.state.persons[1].name} age={this.state.persons[1].age} changed={this.nameChangedhandler.bind(this)} />
        
      </div>
    );
    */
  }
  render() {
    const style = {
      backgroundColor: 'white',
      font: 'inherit',
      border: '1px solid blue',
      padding: '8px'
    };
    return (
      <div className="App">
        <button
          style={style}
          onClick={this.switchNameHandler.bind(this, 'new Deepak')}>Switch name</button>
        <button
          style={style}
          onClick={this.togglePersonsHandler}>Show/Hide</button>
        {
          this.renderPersons()
        }
        <h1>Hi, Hello React App</h1>
        <p>This is really working...</p>
      </div>
    );
  }
}

export default App;
