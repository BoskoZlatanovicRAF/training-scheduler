<template>
  <div id="app">
    <nav class="navbar">
      <router-link class="nav-link home-link" to="/">Home</router-link>
      <div class="dropdown" @mouseenter="showDropdown = true" @mouseleave="showDropdown = false">
        <button class="sign-in-button">Hello, sign in</button>
        <div class="dropdown-content" v-if="showDropdown">
          <router-link class="dropdown-item" v-if="!token" to="/login">Log In</router-link>
          <router-link class="dropdown-item" v-if="!token" to="/managerRegister">Manager Register</router-link>
          <router-link class="dropdown-item" v-if="!token" to="/userRegister">User Register</router-link>
          <router-link class="dropdown-item" v-if="token" to="/login" @click="logout">Log Out</router-link>
        </div>
      </div>
    </nav>
    <router-view/>
  </div>
</template>
<script>
import {mapState, mapMutations } from 'vuex';
export default {
    name: 'App',
    data() {
      return {
        showDropdown: false
      }
    },
    components: {
    },
    computed: {
      ...mapState([
        'token'
      ])
    },
    methods: {
      ...mapMutations([
        'removeToken',
        'setToken'
      ]),
      logout() {
        this.removeToken();
      }
    },
    token() {
      return this.$store.state.token;
    },
    
  }


</script>

<style>
#app {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #2c3e50;
  text-align: center;
}

.navbar {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 0;
  background-color: #333;
}

.nav-link {
  margin: 0 10px;
  color: #fff;
  text-decoration: none;
}

.home-link {
  font-size: 1.2em; /* Larger font size for visibility */
  font-weight: bold; /* Bold font for prominence */
  color:#ff991f !important;
}

.sign-in-button {
  background-color: #ffa41c;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 3px;
  cursor: pointer;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
  left: 50%; /* Align the left edge of the dropdown content with the right edge of the dropdown button */
  transform: translateX(-50%);
}

.dropdown-content .dropdown-item {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content .dropdown-item:hover {
  background-color: #f1f1f1;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown:hover .sign-in-button {
  background-color: #ff991f;
}
</style>