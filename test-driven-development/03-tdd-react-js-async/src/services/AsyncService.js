import axios from 'axios';
async function asyncAxiosData() {
    console.log('get data');
    const result = await axios.get(
        "https://api.coindesk.com/v1/bpi/currentprice.json"
    );
    return result;
}

const asyncData = () => {
    return Promise.resolve("async data")
}

export {
    asyncData,
    asyncAxiosData
}