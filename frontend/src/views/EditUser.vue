<template>
  <div class="card">
    <div class="card-header">
      <h1>Edit User</h1>
    </div>
    <div class="card-body">
      <form @submit.prevent="updateUser">
        <div class="form-group">
          <label for="firstName">First Name</label>
          <input id="firstName" type="text" class="form-control" v-model="currentUser.ime" placeholder="First Name"/>
        </div>
        <div class="form-group">
          <label for="lastName">Last Name</label>
          <input id="lastName" type="text" class="form-control" v-model="currentUser.prezime" placeholder="Last Name"/>
        </div>
        <div class="form-group">
          <label for="birthDate">Birth Date</label>
          <input id="birthDate" type="date" class="form-control" v-model="formattedDatum" placeholder="Birth Date"/>        
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input id="email" type="email" class="form-control" v-model="currentUser.username" placeholder="Email"/>
        </div>
        <button type="submit" class="btn btn-primary">Save Changes</button>
      </form>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import {mapState,mapActions} from "vuex";
export default {
  data() {
    return {
      user: {
        ime: '',
        prezime: '',
        datum: '',
        username: '',
        
      },

    };
  },
  computed: {
    ...mapState([
      'token', 'currentUser'
    ]),
    formattedDatum: {
      get() {
        if (this.currentUser.datum) {
          const date = new Date(this.currentUser.datum);
          return `${date.getFullYear()}-${('0' + (date.getMonth() + 1)).slice(-2)}-${('0' + date.getDate()).slice(-2)}`;
        }
        return null;
      },
      set(newValue) {
        this.currentUser.datum = new Date(newValue).toISOString();
      }
    },
    token() {
      return this.$store.state.token;
    },
  //   formattedDatum() {
  //   if (this.currentUser.datum) {
  //     const date = new Date(this.currentUser.datum);
  //     const year = date.getFullYear();
  //     const month = ('0' + (date.getMonth() + 1)).slice(-2); // add leading zero
  //     const day = ('0' + date.getDate()).slice(-2); // add leading zero
  //     return `${year}-${month}-${day}`;
  //   }
  //   return null;
  // },


  },

  // created() {
  //   this.fetchUserData();
  // },
  methods: {
    
    ...mapActions([
      'registerManager'
    ]),
    updateUser() {
      console.log("updateUser",this.currentUser);
      this.currentUser.salaId = null;
      fetch("http://localhost:8080/user/updateUser",{
        method: 'POST',
        headers:{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.token
        },
        body: JSON.stringify(this.currentUser)
      }).then(response => {
        if (response.ok) {
          router.push({ name: 'home' });
          this.currentUser = response.json();
        } else {
          throw new Error('Something went wrong');
        }
      }).then(data => {
        console.log(data);
      }).catch(error => {
        console.log(error);
      });
    }
  },

};
</script>