import { asyncData, asyncAxiosData } from "../services/AsyncService";
test("the data is async data", done => {
    expect.assertions(1);
    asyncData().then(data => {
        expect(data).toBe("async data");
        done();
    });
});
test("the data is async data by await", async () => {
    expect.assertions(1);
    const data = await asyncData();
    expect(data).toBe("async data");
});
test("the data from axios to be defined", async () => {
    expect.assertions(2);
    const response = await asyncAxiosData();
    expect(response.status).toBe(200);
    expect(response.data.chartName).toBe('Bitcoin');
});