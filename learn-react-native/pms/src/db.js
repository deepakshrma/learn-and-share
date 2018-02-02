const low = require('lowdb');
//const FileSync = require('lowdb/adapters/FileSync');

//const adapter = new FileSync('db.json');
// var adapter = new LocalStorage('db')
const Memory = require('lowdb/adapters/Memory')
const db = low(new Memory());
db.defaults({ users: {
  1: {
    employees: {
      1: {
        name: 'deepak',
        phone: '111-212-1221',
        shift: 'Monday'
      },
      2: {
        name: 'deepak 2',
        phone: '111-333-1221',
        shift: 'Thursday'
      }
    }
  }
} })
  .write();
export default db;