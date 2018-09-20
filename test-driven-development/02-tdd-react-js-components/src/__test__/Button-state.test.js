import React from 'react';
import Button from '../components/Button';
import renderer from 'react-test-renderer';
import {shallow} from 'enzyme';
import './enzyme-setup'

test('Button change text on data pass', () => {
  // Render a checkbox with label in the document
  const buttonWithoutText = shallow(<Button/>);
  expect(buttonWithoutText.text()).toEqual('Submit');
  const buttonWithText = shallow(<Button text="Hover Me"/>);
  expect(buttonWithText.text()).toEqual('Hover Me');
  expect(buttonWithText.text()).not.toEqual('Submit');
});