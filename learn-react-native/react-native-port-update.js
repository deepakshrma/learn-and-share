const replace = require('replace-in-file');
const options = {
	files: [
		'./albums/node_modules/react-native/**/*.m',
		'./albums/node_modules/react-native/**/*.mm',
		'./albums/node_modules/react-native/**/*.pbxproj',
		'./albums/node_modules/react-native/**/*.java',
		'./albums/node_modules/react-native/**/*.js'
	],
	from: /([@|:| ])8081/g,
	to: '$18082',
};

replace(options)
	.then(changes => {
		console.log('Modified files:', changes.join(', '));
	})
	.catch(error => {
		console.error('Error occurred:', error);
	});