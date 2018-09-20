import React from 'react';
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
Enzyme.configure({ adapter: new Adapter() });

import App from "../App";

describe('App', () => {
    describe('when the button is clicked', () => {
      const spy = jest.spyOn(App.prototype, 'fetchData');
      const app = shallow(<App />);
      const mockData = { bpi: { USD: { rate_float: 5 } } };
       beforeEach(()=>{
        const mock = new MockAdapter(axios);
        mock
         .onGet("https://api.coindesk.com/v1/bpi/currentprice.json")
         .reply(200, mockData);
          app.find('.btn').simulate('click');
      })
      it('calls the `fetchData` function', () => {
        app.find('.btn').simulate('click');
        expect(spy).toHaveBeenCalled();
      });
      it('sets the `state.rate` to the bitcoin exchange rate that we    get from the GET request', () => {
        expect(app.state().rate).not.toEqual(0);
        expect(app.state().rate).toEqual(mockData.bpi.USD.rate_float);
     });
    });
  });