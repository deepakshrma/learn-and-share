import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Button from './components/Button';
import Currency from './components/Currency';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        <Button text="Click Here"></Button>
        <Button></Button>
        <Button text="fetch data" onClick={
          this.fetchData
        }></Button>
      <Currency rate="0"></Currency>
      </div >
    );
  }
  fetchData() {
    console.log("test")
  }
}

export default App;
