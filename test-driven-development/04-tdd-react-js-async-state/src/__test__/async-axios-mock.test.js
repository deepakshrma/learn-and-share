import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
Enzyme.configure({ adapter: new Adapter() });

import * as AsyncServices from '../services/AsyncService';
describe('when the button is clicked', () => {
    const spy = jest.spyOn(AsyncServices, 'asyncAxiosData');
    const mockData = { bpi: { USD: { rate_float: 5 } } };
    beforeEach(() => {
        const mock = new MockAdapter(axios);
        mock
            .onGet("https://api.coindesk.com/v1/bpi/currentprice.json")
            .reply(200, mockData);
    })
    it('calls the `asyncAxiosData` function', () => {
        AsyncServices.asyncAxiosData();
        expect(spy).toHaveBeenCalled();
    });
    it('sets the `data.bpi.USD.rate_float` to the bitcoin exchange rate that we get from the GET request', async () => {
        const response = await AsyncServices.asyncAxiosData();
        expect(response.data.bpi.USD.rate_float).toEqual(mockData.bpi.USD.rate_float);
    });
});