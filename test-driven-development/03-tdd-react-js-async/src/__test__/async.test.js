import { asyncData } from "../services/AsyncService";
test("the data is peanut butter", done => {
    expect.assertions(1);
    asyncData().then(data => {
        expect(data).toBe("async data");
        done();
    });
});
test("the data is peanut butter", async () => {
    expect.assertions(1);
    const data = await asyncData();
    expect(data).toBe("async data");
});