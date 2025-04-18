import { createStore } from 'vuex'
import VuexPersist from "vuex-persist";


const vuexPersist = new VuexPersist({
  key: 'my-app',
  storage: window.localStorage
});
export default createStore({
  state: {
    token:'',
    sale:[],
    users:[],
    currentUser: {},
    treninge:[],
    events:[],
    treningByNaziv:{}
  },
  getters: {
    setToken: state => state.token,
    setSale: state => state.sale,
    setUsers: state => state.users,
    setTreninge: state => state.treninge,
    setEvents: state => state.events,
    setTreningByNaziv: state => state.treningByNaziv
  },
  mutations: {
    setToken(state, token){
      state.token = token;
    },
    removeToken(state){
      state.token = '';
    },
    setSale(state, sale){
      state.sale = sale;
    },
    setUsers(state, users){
      state.users = users;
    },
    setUser(state, user){
      state.currentUser = user;
    },
    setTreninge(state, treninge){
      state.treninge = treninge;
    },
    setEvents(state, events){
      state.events = events;
    },
    setTreningByNaziv(state, trening){
      state.treningByNaziv = trening;
    },
    removeToken(state){
      state.token = '';
    }
  },
  actions: {
    login({commit}, user) {
      fetch('http://localhost:8080/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user)
  }).then(response => {
    if (response.ok) {
      return response.json();
    }
    throw new Error('Login failed');
  }).then(data => {
    commit('setToken', data.token);
    return fetch('http://localhost:8080/user/getKorisnik', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${data.token}`
      }
    });
  }).then(response => {
    if (response.ok) {
      return response.json();
    }
    throw new Error('Failed to fetch user');
  }).then(user => {
    commit('setUser', user);
  }).catch(error => {
    console.error(error);
  });
    },
    registerUser({commit}, user) {
      fetch('http://localhost:8080/user/registerKorisnik', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      }).then(response => {
        if (response.ok) {
          return response.json();
        }
      });
    },
    registerManager({commit}, user) {
      fetch('http://localhost:8080/user/registerManager', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      }).then(response => {
        if (response.ok) {
          return response.json();
        }
      });
    },
    async fetchSale({commit}) {
      return new Promise((resolve, reject) => {
        fetch('http://localhost:8086/api/sala/')
            .then(response => {
              if (response.ok) {
                response.json().then(data => {
                  commit('setSale', data);
                  resolve(data); // Resolve the promise after committing the data
                });
              } else {
                reject(new Error('Failed to fetch data'));
              }
            })
            .catch(error => {
              reject(error); // Reject the promise in case of any errors
            });
      });
    },
    async fetchUsers({commit}){
        fetch('http://localhost:8080/user/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + this.state.token
            }
        }).then(response => {
            if (response.ok) {
            response.json().then(data => {
                commit('setUsers', data);
            });
            }
        });
    },
    async fetchTreninge({commit}){
      fetch('http://localhost:8086/api/trening/',
      {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + this.state.token
        }
      })
          .then(response => {
            if (response.ok) {
              response.json().then(data => {
                commit('setTreninge', data);
              });
            }
          });
    
    },
    async fetchEvents({commit}){
       fetch('http://localhost:8086/api/termini/current',
       {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
       }) 
       .then(response => {
        if (response.ok) {
          response.json().then(data => {
            commit('setEvents', data);
          });
        }
      });
    },
    async getTreningbyNaziv({commit}, naziv){
      fetch(`http://localhost:8086/api/termini/getTrening?naziv=${naziv}`,
      {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
       }) 
       .then(response => {
        if (response.ok) {
          response.json().then(data => {
            commit('setTreningByNaziv', data);
          });
        }
      });
    },
  },
  modules: {
  },

  plugins: [vuexPersist.plugin]
})
