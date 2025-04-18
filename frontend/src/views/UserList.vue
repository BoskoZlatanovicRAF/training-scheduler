<script>
import {mapActions, mapState} from "vuex";
import {computed} from "vue";

export default {
  name: "UserList",
  data() {
    return {
      fields: [
        { key: "username", label: "Email" },
        { key: "ime", label: "Ime" },
        { key: "prezime", label: "Prezime" },
        { key: "datum", label: "Datum rodjenja" },
        { key: "tipKorisnika", label: "Tip korisnika" },
        { key: "status", label: "Status" },
        { key: "actions", label: "Actions" },
      ],

    };
  },
  computed:{
    ...mapState([
      'users','token'
    ]),
  },
  methods: {
    ...mapActions([
      'fetchUsers'
    ]),
    toggleUserStatus(user) {
      console.log(user);
      if (user.status === 'ONLINE') {
        this.deactivateUser(user);
      } else {
        this.activateUser(user);
      }
    },
    async deactivateUser(user) {
      console.log(user);
      fetch("http://localhost:8080/user/deactivate",{
        method: 'POST',
        headers:{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.token
        },
        body: JSON.stringify(user)
      }).then(response => {
        if (response.ok) {
          this.fetchUsers();
        } else {
          throw new Error('Something went wrong');
        }
      }).then(data => {
        console.log(data);
      }).catch(error => {
        console.log(error);
      });
    },
    activateUser(user) {
      fetch("http://localhost:8080/user/activateAdmin",{
        method: 'POST',
        headers:{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.token
        },
        body: JSON.stringify(user)
      }).then(response => {
        if (response.ok) {
          this.fetchUsers();
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

  async mounted() {
    await this.fetchUsers();
  },
  watch: {
    users(newUsers) {
      this.users = newUsers
    },
  },
}
</script>
<template>
  <div class="container mt-4">
    <b-table striped hover :items="this.users" :fields="fields">
      <template #cell(actions)="data">
        <b-button size="sm" :variant="data.item.status === 'ONLINE' ? 'danger' : 'success'" @click="toggleUserStatus(data.item)">
          {{ data.item.status === 'ONLINE' ? 'Deactivate' : 'Activate' }}
        </b-button>
      </template>
    </b-table>
  </div>
</template>


<style scoped>

</style>