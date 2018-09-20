import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
Enzyme.configure({ adapter: new Adapter() });

import App from "../App";

