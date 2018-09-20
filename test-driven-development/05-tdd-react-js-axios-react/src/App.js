import React, { Component } from "react";
import axios from "axios";
import logo from "./logo.svg";
import "./App.css";
import Button from "./components/Button";
import Currency from "./components/Currency";

class App extends Component {
  constructor() {
    super();
    this.state = {
      rate: "0"
    };
    this.fetchData = this.fetchData.bind(this);
  }
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
        <Button text="Click Here" />
        <Button />
        <button className="btn" onClick={this.fetchData}>
          Fetch
        </button>
        <Currency rate={this.state.rate} />
      </div>
    );
  }
  async fetchData() {
    console.log("get data");
    const result = await axios.get(
      "https://api.coindesk.com/v1/bpi/currentprice.json"
    );
    this.setState({ rate: result.data.bpi.USD.rate_float });
  }
}

export default App;
