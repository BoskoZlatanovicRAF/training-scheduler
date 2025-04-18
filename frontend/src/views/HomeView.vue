<template>
  <div class="home">
    <button class="btn" v-if="token" @click="editUser">Edit User</button>
    <button class="btn" v-if="token && currentUser.tipKorisnika === 'Standard'" @click="zakaziTermin()">Zakazi Termin</button>
    <button class="btn" v-if="token && currentUser.tipKorisnika === 'Manager'" @click="dodajTermin()">Dodaj Termin</button>
    <button class="btn" v-if="token && currentUser.tipKorisnika === 'Manager'" @click="listaTreninga()">Lista Treninga za Salu</button>
    <button class="btn" v-if="token && currentUser.tipKorisnika === 'Standard'" @click="racuni()">Lista Racuni</button>
    <button class="btn" v-if="token && (currentUser.tipKorisnika === 'Manager' || currentUser.tipKorisnika === 'Standard')" @click="notifikacije()">Notifikacije</button>
    <button class="btn" v-if="token && currentUser.tipKorisnika === 'Admin'" @click="notifikacije()">Sve Notifikacije</button>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import {mapActions, mapState} from "vuex";
export default {
  name: 'HomeView',
  components: {
    HelloWorld
  },
  computed: {
    ...mapState([
      'currentUser'
    ]),
    token() {
      return this.$store.getters.setToken;
    }
  },
 
  methods: {
    editUser() {
      this.$router.push({ name: 'editUser'});
    },
    dodajTermin() {
      this.$router.push({ name: 'dodajTermin'});
    },
    zakaziTermin() {
      this.$router.push({ name: 'zakaziTermin'});
    },
    listaTreninga() {
      this.$router.push({ name: 'listaTreninga'});
    },
    racuni() {
      this.$router.push({ name: 'racuni'});
    },
    notifikacije(){
      this.$router.push({ name: 'notifikacije'});
    },
  }

}

</script>

<style scoped>
.btn {
  color: #fff;
  background-color: #007bff;
  border: none;
  padding: 10px 20px;
  margin: 10px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn:hover {
  background-color: #0056b3;
}
</style>
