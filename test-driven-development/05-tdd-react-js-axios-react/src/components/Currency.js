import React from "react";
export default class Currency extends React.Component {
  render() {
    return <h1>{this.props.rate}</h1>;
  }
}
