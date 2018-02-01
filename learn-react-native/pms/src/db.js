const low = require('lowdb');
//const FileSync = require('lowdb/adapters/FileSync');

//const adapter = new FileSync('db.json');
// var adapter = new LocalStorage('db')
const Memory = require('lowdb/adapters/Memory')
const db = low(new Memory());
db.defaults({ users: {} })
  .write();
export default db;